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

import java.io.File
import java.util.{ List => JList }
import java.util.{ Set => JSet }

import scala.collection.JavaConversions._

import scalax.io.JavaConverters._

import org.apache.maven.artifact.Artifact
import org.apache.maven.model.Resource
import org.apache.maven.project.MavenProject

import st.happy_camper.maven.plugins.ensime.model.Project
import st.happy_camper.maven.plugins.ensime.model.SubProject
import st.happy_camper.maven.plugins.ensime.sexpr.SExpr
import st.happy_camper.maven.plugins.ensime.sexpr.SExprEmitter

/**
 * Represents an ENSIME configuration file generator.
 * @author ueshin
 */
class ConfigGenerator(
    val project: MavenProject,
    val formatterPreferences: File) {

  def generate(out: File): Unit = {

    def collectModules(project: MavenProject) = {
      // TODO how to collect modules?
      List(project)
    }

    implicit val MavenProjectAsSubProject = new As[MavenProject, SubProject] {

      def as(project: MavenProject): SubProject = {
        val (runtimeDeps, compileDeps, testDeps) =
          (project.getArtifacts.asInstanceOf[JSet[Artifact]] :\ (List.empty[String], List.empty[String], List.empty[String])) {
            case (artifact, (runtimeDeps, compileDeps, testDeps)) =>
              val path = artifact.getFile.getAbsolutePath
              artifact.getScope match {
                case Artifact.SCOPE_PROVIDED =>
                  (runtimeDeps, path :: compileDeps, path :: testDeps)
                case Artifact.SCOPE_RUNTIME =>
                  (path :: runtimeDeps, compileDeps, path :: testDeps)
                case Artifact.SCOPE_TEST =>
                  (runtimeDeps, compileDeps, path :: testDeps)
                case _ =>
                  (path :: runtimeDeps, path :: compileDeps, path :: testDeps)
              }
          }

        SubProject(
          project.getArtifactId,
          project.getVersion,
          runtimeDeps :+ project.getBuild.getOutputDirectory,
          compileDeps,
          testDeps :+ project.getBuild.getOutputDirectory :+ project.getBuild.getTestOutputDirectory,
          project.getCompileSourceRoots.asInstanceOf[JList[String]]
            ++: project.getTestCompileSourceRoots.asInstanceOf[JList[String]]
            ++: project.getResources.asInstanceOf[JList[Resource]].map(_.getDirectory)
            ++: project.getTestResources.asInstanceOf[JList[Resource]].map(_.getDirectory) ++: Nil,
          project.getBuild.getOutputDirectory,
          project.getBuild.getTestOutputDirectory,
          List.empty[String],
          Map.empty)
      }
    }

    val emitter = new SExprEmitter(Project(collectModules(project).map(_.as[SubProject])).as[SExpr])
    emitter.emit(out.asOutput)
  }
}
