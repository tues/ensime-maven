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
package org.ensime.maven.plugins.ensime.model;

import java.io.File;
import java.util.Set;
import java.util.function.Function;
import static java.util.stream.Collectors.toSet;

public class EnsimeModule {
  private final String name;
  private final Set<File> mainRoots;
  private final Set<File> testRoots;
  private final Set<File> targets;
  private final Set<File> testTargets;
  private final Set<String> dependsOnNames;
  private final Set<File> compileJars;
  private final Set<File> runtimeJars;
  private final Set<File> testJars;
  private final Set<File> sourceJars;
  private final Set<File> docJars;

  public EnsimeModule(final String name,
      final Set<File> mainRoots,
      final Set<File> testRoots,
      final Set<File> targets,
      final Set<File> testTargets,
      final Set<String> dependsOnNames,
      final Set<File> compileJars,
      final Set<File> runtimeJars,
      final Set<File> testJars,
      final Set<File> sourceJars,
      final Set<File> docJars) {
    this.name           = name;
    this.mainRoots      = mainRoots;
    this.testRoots      = testRoots;
    this.targets        = targets;
    this.testTargets    = testTargets;
    this.dependsOnNames = dependsOnNames;
    this.compileJars    = compileJars;
    this.runtimeJars    = runtimeJars;
    this.testJars       = testJars;
    this.sourceJars     = sourceJars;
    this.docJars        = docJars;
  }

  public String getName() { return name; }
  public Set<File> getMainRoots() { return mainRoots; }
  public Set<File> getTestRoots() { return testRoots; }
  public Set<File> getTargets() { return targets; }
  public Set<File> getTestTargets() { return testTargets; }
  public Set<String> getDependsOnNames() { return dependsOnNames; }
  public Set<File> getCompileJars() { return compileJars; }
  public Set<File> getRuntimeJars() { return runtimeJars; }
  public Set<File> getTestJars() { return testJars; }
  public Set<File> getSourceJars() { return sourceJars; }
  public Set<File> getDocJars() { return docJars; }

  public Set<EnsimeModule> dependencies(Function<String, EnsimeModule> lookup) {
    return dependsOnNames.stream().map(lookup).collect(toSet());
  }
}
