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

/**
 * Represents ENSIME project.
 * @author ueshin
 */
case class Project(
  subprojects: List[SubProject])

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
      SMap(Seq((SKeyword("subprojects"), SList(
        project.subprojects.map { _.as[SExpr] }))))
    }
  }
}
