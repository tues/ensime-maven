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
import java.util.StringJoiner;

final public class EnsimeProjectId {
  private final String project;
  private final String config;

  public EnsimeProjectId(final String project, final String config) {
    this.project = project;
    this.config  = config;
  }

  public String getProject() { return project; }
  public String getConfig() { return config; }


  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", "EnsimeProjectId(", ")");
    joiner.add(project);
    joiner.add(config);

    return joiner.toString();
  }
}

