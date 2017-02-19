/*
 * Copyright 2012 Happy-Camper Street.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.ensime.maven.plugins.ensime.formatter;

import java.io.File;
import java.util.Optional;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.ensime.maven.plugins.ensime.model.*;

import static java.util.stream.Collectors.*;

public class SExpFormatter {

  // normalise and ensure monkeys go first
  // (bit of a hack to do it here, maybe best when creating)
  private static List<File> orderFiles(final Collection<File> ss) {
    final Comparator<File> comparator = (f1, f2) -> {
      String f1Name = f1.getName() + f1.getPath();
      String f2Name = f2.getName() + f2.getPath();
      return f1Name.compareTo(f2Name);
    };
    Map<Boolean, List<File>> grouped =
      ss.stream().distinct().sorted(comparator)
      .collect(partitioningBy(f -> f.getName().contains("monkey")));
    List<File> monkeys = grouped.get(true);
    List<File> humans  = grouped.get(false);
    monkeys.addAll(humans);
    return monkeys;
  }

  private static String toSExp(final String s) {
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  private static String toSExp(final File f){
    return toSExp(f.getAbsolutePath());
  }

  private static String fsToSExp(final Collection<File> ss) {
    if (ss.isEmpty())
      return "nil";
    else
      return orderFiles(ss).stream().map(s -> toSExp(s)).collect(joining(" ", "(", ")"));
  }

  private static String ssToSExp(final Collection<String> ss) {
    if (ss.isEmpty())
      return "nil";
    else
      return ss.stream().map(s -> toSExp(s)).collect(joining(" ", "(", ")"));
  }

  private static String msToSExp(final Collection<EnsimeModule> ss) {
    if (ss.isEmpty())
      return "nil";
    else {
      return ss.stream()
        .sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
        .map(s -> toSExp(s)).collect(joining(" ", "(", ")"));
    }
  }

  private static String psToSExp(final Collection<EnsimeProject> ss) {
    if (ss.isEmpty())
      return "nil";
    else {
      return ss.stream()
        .sorted((p1, p2) -> p1.getId().toString().compareTo(p2.getId().toString()))
        .map(s -> toSExp(s)).collect(joining(" ", "(", ")"));
    }
  }

  private static String fToSExp(final String key, final Optional<File> op) {
    return op.map(f -> ":" + key + " " + toSExp(f)).orElse("");
  }

  private static String sToSExp(final String key, final Optional<String> op) {
    return op.map(s -> ":" + key + " " + toSExp(s)).orElse("");
  }

  private static String toSExp(final Boolean b) {
    return b? "t" : "nil";
  }

  // a lot of legacy key names and conventions
  public static String toSExp(final EnsimeConfig c) {
    StringBuilder builder = new StringBuilder();

    builder.append("(:root-dir ");
    builder.append(toSExp(c.getRoot()));
    builder.append("\n");
    builder.append(":cache-dir ");
    builder.append(toSExp(c.getCacheDir()));
    builder.append("\n");
    builder.append(":scala-compiler-jars ");
    builder.append(fsToSExp(c.getScalaCompilerJars()));
    builder.append("\n");
    builder.append(":ensime-server-jars");
    builder.append(fsToSExp(c.getEnsimeServerJars()));
    builder.append("\n");
    builder.append(":name \"");
    builder.append(c.getName());
    builder.append("\"");
    builder.append("\n");
    builder.append(":java-home ");
    builder.append(toSExp(c.getJavaHome()));
    builder.append("\n");
    builder.append(":java-flags ");
    builder.append(ssToSExp(c.getJavaFlags()));
    builder.append("\n");
    builder.append(":java-sources ");
    builder.append(fsToSExp(c.getJavaSrc()));
    builder.append("\n");
    builder.append(":java-compiler-args ");
    builder.append(ssToSExp(c.getJavacOptions()));
    builder.append("\n");
    builder.append(":reference-source-roots ");
    builder.append(fsToSExp(c.getJavaSrc()));
    builder.append("\n");
    builder.append(":scala-version ");
    builder.append(toSExp(c.getScalaVersion()));
    builder.append("\n");
    builder.append(":compiler-args ");
    builder.append(ssToSExp(c.getScalacOptions()));
    builder.append("\n");
    builder.append(":subprojects ");
    builder.append(msToSExp(c.getModules().values()));
    builder.append("\n");
    builder.append(":projects ");
    builder.append(psToSExp(c.getProjects()));
    builder.append(")");

    return builder.toString();
  }

  // a lot of legacy key names and conventions
  private static String toSExp(final EnsimeModule m) {
    List<File> roots = new ArrayList<>();
    roots.addAll(m.getMainRoots());
    roots.addAll(m.getTestRoots());
    StringBuilder builder = new StringBuilder();

    builder.append("(:name ");
    builder.append(toSExp(m.getName()));
    builder.append("\n");
    builder.append(":source-roots ");
    builder.append(fsToSExp(roots));
    builder.append("\n");
    builder.append(":targets ");
    builder.append(fsToSExp(m.getTargets()));
    builder.append("\n");
    builder.append(":test-targets ");
    builder.append(fsToSExp(m.getTestTargets()));
    builder.append("\n");
    builder.append(":depends-on-modules ");
    builder.append(ssToSExp(m.getDependsOnNames().stream().sorted().collect(toList())));
    builder.append("\n");
    builder.append(":compile-deps ");
    builder.append(fsToSExp(m.getCompileJars()));
    builder.append("\n");
    builder.append(":runtime-deps ");
    builder.append(fsToSExp(m.getRuntimeJars()));
    builder.append("\n");
    builder.append(":test-deps ");
    builder.append(fsToSExp(m.getTestJars()));
    builder.append("\n");
    builder.append(":doc-jars ");
    builder.append(fsToSExp(m.getDocJars()));
    builder.append("\n");
    builder.append(":reference-source-roots ");
    builder.append(fsToSExp(m.getSourceJars()));
    builder.append(")");

    return builder.toString();
  }

  private static String toSExp(final EnsimeProject p) {
    StringBuilder builder = new StringBuilder();

    builder.append("(:id ");
    builder.append(toSExp(p.getId()));
    builder.append("\n");
    builder.append(":depends ");
    builder.append(idsToSExp(p.getDependsOn()));
    builder.append("\n");
    builder.append(":sources ");
    builder.append(fsToSExp(p.getSources()));
    builder.append("\n");
    builder.append(":targets ");
    builder.append(fsToSExp(p.getTargets()));
    builder.append("\n");
    builder.append(":scalac-options ");
    builder.append(ssToSExp(p.getScalacOptions()));
    builder.append("\n");
    builder.append(":javac-options ");
    builder.append(ssToSExp(p.getJavacOptions()));
    builder.append("\n");
    builder.append(":library-jars ");
    builder.append(fsToSExp(p.getLibraryJars()));
    builder.append("\n");
    builder.append(":library-sources ");
    builder.append(fsToSExp(p.getLibrarySources()));
    builder.append("\n");
    builder.append(":library-docs ");
    builder.append(fsToSExp(p.getLibraryDocs()));
    builder.append(")");

    return builder.toString();
  }

  private static String toSExp(final EnsimeProjectId id) {
    StringBuilder builder = new StringBuilder();
    builder.append("(:project ");
    builder.append(toSExp(id.getProject()));
    builder.append(" ");
    builder.append(":config ");
    builder.append(toSExp(id.getConfig()));
    builder.append(")");

    return builder.toString();
  }

  private static String idsToSExp(final Collection<EnsimeProjectId> ids) {
    if (ids.isEmpty())
      return "nil";
    else {
      return ids.stream()
        .sorted((f1, f2) -> f1.toString().compareTo(f2.toString()))
        .map(s -> toSExp(s)).collect(joining(" ", "(", ")"));
    }
  }

}
