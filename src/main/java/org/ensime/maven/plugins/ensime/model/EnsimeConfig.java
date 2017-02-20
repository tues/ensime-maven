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
import java.util.List;
import java.util.Set;
import java.util.Map;

final public class EnsimeConfig {
  private final File root;
  private final File cacheDir;
  private final Set<File> scalaCompilerJars;
  private final Set<File> ensimeServerJars;
  private final String name;
  private final String scalaVersion;
  private final List<String> scalacOptions; // 1.0
  private final Map<String, EnsimeModule> modules; // 1.0
  private final File javaHome;
  private final List<String> javaFlags;
  private final List<String> javacOptions; // 1.0
  private final Set<File> javaSrc;
  private final List<EnsimeProject> projects;

  public EnsimeConfig(final File root,
    final File cacheDir,
    final Set<File> scalaCompilerJars,
    final Set<File> ensimeServerJars,
    final String name,
    final String scalaVersion,
    final List<String> scalacOptions,
    final Map<String, EnsimeModule> modules,
    final File javaHome,
    final List<String> javaFlags,
    final List<String> javacOptions,
    final Set<File> javaSrc,
    final List<EnsimeProject> projects) {

    this.root              = root;
    this.cacheDir          = cacheDir;
    this.scalaCompilerJars = scalaCompilerJars;
    this.ensimeServerJars  = ensimeServerJars;
    this.name              = name;
    this.scalaVersion      = scalaVersion;
    this.scalacOptions     = scalacOptions;
    this.modules           = modules;
    this.javaHome          = javaHome;
    this.javaFlags         = javaFlags;
    this.javacOptions      = javacOptions;
    this.javaSrc           = javaSrc;
    this.projects          = projects;
  }


  public File getRoot() { return root; }
  public File getCacheDir() { return cacheDir; }
  public Set<File> getScalaCompilerJars() { return scalaCompilerJars; }
  public Set<File> getEnsimeServerJars() { return ensimeServerJars; }
  public String getName() { return name; }
  public String getScalaVersion() { return scalaVersion; }
  public List<String> getScalacOptions() { return scalacOptions; }
  public Map<String, EnsimeModule> getModules() { return modules; }
  public File getJavaHome() { return javaHome; }
  public List<String> getJavaFlags() { return javaFlags; }
  public List<String> getJavacOptions() { return javacOptions; }
  public Set<File> getJavaSrc() { return javaSrc; }
  public List<EnsimeProject> getProjects() { return projects; }
}
