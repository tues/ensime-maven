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
package st.happy_camper.maven.plugins.ensime
package model

import st.happy_camper.maven.plugins.ensime.sexpr.SExpr
import st.happy_camper.maven.plugins.ensime.sexpr.SKeyword
import st.happy_camper.maven.plugins.ensime.sexpr.SList
import st.happy_camper.maven.plugins.ensime.sexpr.SMap
import st.happy_camper.maven.plugins.ensime.sexpr.SNil
import st.happy_camper.maven.plugins.ensime.sexpr.SString
import st.happy_camper.maven.plugins.ensime.sexpr.STrue

/**
 * Represents sub-project of ENSIME project.
 * @author ueshin
 */
case class SubProject(
  name: String,
  version: String,
  runtimeDeps: List[String],
  compileDeps: List[String],
  testDeps: List[String],
  sourceRoots: List[String],
  target: String,
  testTarget: String,
  dependsOnModule: List[String],
  formattingPreferences: Map[String, Either[String, Boolean]])

/**
 * A companion object for {@link SubProject}.
 * @author ueshin
 */
object SubProject {

  /**
   * Treats SubProject as SExpr.
   */
  implicit object SubProjectAsSExpr extends As[SubProject, SExpr] {

    override def as(subproject: SubProject) =
      SMap(Seq(
        (SKeyword("name") -> SString(subproject.name)),
        (SKeyword("module-name") -> SString(subproject.name)),
        (SKeyword("version") -> SString(subproject.version)),
        (SKeyword("runtime-deps") -> SList(subproject.runtimeDeps.map(SString(_)))),
        (SKeyword("compile-deps") -> SList(subproject.compileDeps.map(SString(_)))),
        (SKeyword("test-deps") -> SList(subproject.testDeps.map(SString(_)))),
        (SKeyword("source-roots") -> SList(subproject.sourceRoots.map(SString(_)))),
        (SKeyword("target") -> SString(subproject.target)),
        (SKeyword("test-target") -> SString(subproject.testTarget)),
        (SKeyword("depends-on-modules") -> SList(subproject.dependsOnModule.map(SString(_)))),
        (SKeyword("formatting-prefs") -> SMap(
          subproject.formattingPreferences.map {
            case (key, Left(value)) =>
              SKeyword(key) -> SString(value)
            case (key, Right(true)) =>
              SKeyword(key) -> STrue
            case (key, Right(false)) =>
              SKeyword(key) -> SNil
          }.toSeq))))
  }
}
