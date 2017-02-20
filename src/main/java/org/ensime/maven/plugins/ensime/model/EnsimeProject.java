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

import java.util.Set;
import java.util.List;
import java.io.File;

final public class EnsimeProject {
  private final EnsimeProjectId id;
  private final List<EnsimeProjectId> dependsOn;
  private final Set<File> sources;
  private final Set<File> targets;
  private final List<String> scalacOptions;
  private final List<String> javacOptions;
  private final Set<File> libraryJars;
  private final Set<File> librarySources;
  private final Set<File> libraryDocs;

  public EnsimeProject(final EnsimeProjectId id,
      final List<EnsimeProjectId> dependsOn,
      final Set<File> sources,
      final Set<File> targets,
      final List<String> scalacOptions,
      final List<String> javacOptions,
      final Set<File> libraryJars,
      final Set<File> librarySources,
      final Set<File> libraryDocs) {
    this.id             = id;
    this.dependsOn      = dependsOn;
    this.sources        = sources;
    this.targets        = targets;
    this.scalacOptions  = scalacOptions;
    this.javacOptions   = javacOptions;
    this.libraryJars    = libraryJars;
    this.librarySources = librarySources;
    this.libraryDocs    = libraryDocs;
  }

  public EnsimeProjectId getId() { return id; }
  public List<EnsimeProjectId> getDependsOn() { return dependsOn; }
  public Set<File> getSources() { return sources; }
  public Set<File> getTargets() { return targets; }
  public List<String> getScalacOptions() { return scalacOptions; }
  public List<String> getJavacOptions() { return javacOptions; }
  public Set<File> getLibraryJars() { return libraryJars; }
  public Set<File> getLibrarySources() { return librarySources; }
  public Set<File> getLibraryDocs() { return libraryDocs; }
}

