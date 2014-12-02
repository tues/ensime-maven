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

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._

import st.happy_camper.maven.plugins.ensime.sexpr.SExpr

/**
 * A spec for {@link Project}.
 * @author ueshin
 */
@RunWith(classOf[JUnitRunner])
object ProjectSpecTest extends ProjectSpec

class ProjectSpec extends Specification {

  "Project" should {

    "be treated as SExpr" in {
      val project = Project("name","path", "cache", "version", List(SubProject(
        "name",
        "version",
        List("runtime-dep-1", "runtime-dep-2"),
        List("compile-dep-1", "compile-dep-2"),
        List("test-dep-1", "test-dpe-2"),
        List("source-root-1", "source-root-2"),
        "target",
        "test-target",
        List("depends-1", "depends-2"))),
        FormatterPreferences())

      val expected = """(:root-dir
                       |   "path"
                       | :cache-dir
                       |   "cache"
                       | :name
                       |   "name"
                       | :scala-version
                       |   "version"
                       | :subprojects
                       |   ((:name
                       |       "name"
                       |     :module-name
                       |       "name"
                       |     :runtime-deps
                       |       ("runtime-dep-1"
                       |        "runtime-dep-2")
                       |     :compile-deps
                       |       ("compile-dep-1"
                       |        "compile-dep-2")
                       |     :test-deps
                       |       ("test-dep-1"
                       |        "test-dpe-2")
                       |     :source-roots
                       |       ("source-root-1"
                       |        "source-root-2")
                       |     :target
                       |       "target"
                       |     :test-target
                       |       "test-target"
                       |     :depends-on-modules
                       |       ("depends-1"
                       |        "depends-2")))
                       | :formatting-prefs
                       |   ())""".stripMargin

        val output = project.as[SExpr].as[String]
        output must equalTo(expected)
    }
  }
}
