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
package org.ensime.maven.plugins.ensime
package model

import org.ensime.maven.plugins.ensime.sexpr._
import org.ensime.maven.plugins.ensime.sexpr.SMap
import org.ensime.maven.plugins.ensime.sexpr.SList
import org.ensime.maven.plugins.ensime.sexpr.SKeyword

/**
 * Represents ENSIME project.
 * @author ueshin
 */
case class Project(
  name: String,
  rootDir: String,
  cacheDir: String,
  scalaVersion: String,
  javaHome: String,
  ensimeJavaFlags: List[String],
  subprojects: List[SubProject],
  formatterPreferences: FormatterPreferences)

/**
 * A companion object for {@link Project}.
 * @author ueshin
 */
object Project {

  /**
   * Treats Project as SExpr.
   */
  implicit object ProjectAsSExpr extends As[Project, SExpr] {

    override def as(project: Project) = {
      SMap(Seq(
        (SKeyword("root-dir") -> SString(project.rootDir)),
        (SKeyword("cache-dir") -> SString(project.cacheDir)),
        (SKeyword("name") -> SString(project.name)),
        (SKeyword("scala-version") -> SString(project.scalaVersion)),
        (SKeyword("java-home") -> SString(project.javaHome)),
        (SKeyword("java-flags") -> SList(project.ensimeJavaFlags.map(SString(_)))),
        (SKeyword("subprojects"), SList(project.subprojects.map { _.as[SExpr] })),
        (SKeyword("formatting-prefs"), project.formatterPreferences.as[SExpr])))
    }
  }
}
