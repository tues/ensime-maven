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

import java.io.File
import scala.collection.JavaConverters._

object SExpFormatter {

  // normalise and ensure monkeys go first
  // (bit of a hack to do it here, maybe best when creating)
  private[ensime] def orderFiles(ss: Iterable[File]): List[File] = {
    val (monkeys, humans) = ss.toList.distinct.sortBy { f =>
      f.getName + f.getPath
    }.partition(_.getName.contains("monkey"))
    monkeys ::: humans
  }

  private def toSExp(s: String): String =
    "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\""

  private def toSExp(f: File): String = toSExp(f.getAbsolutePath)

  private def fsToSExp(ss: Iterable[File]): String =
    if (ss.isEmpty) "nil"
    else orderFiles(ss).map(toSExp).mkString("(", " ", ")")

  private def ssToSExp(ss: Iterable[String]): String =
    if (ss.isEmpty) "nil"
    else ss.toSeq.map(toSExp).mkString("(", " ", ")")

  private def msToSExp(ss: Iterable[EnsimeModule]): String =
    if (ss.isEmpty) "nil"
    else ss.toSeq.sortBy(_.getName).map(toSExp).mkString("(", " ", ")")

  private def psToSExp(ss: Iterable[EnsimeProject]): String =
    if (ss.isEmpty) "nil"
    else ss.toSeq.sortBy(_.getId.toString).map(toSExp).mkString("(", " ", ")")

  private def fToSExp(key: String, op: Option[File]): String =
    op.map { f => s":$key ${toSExp(f)}" }.getOrElse("")

  private def sToSExp(key: String, op: Option[String]): String =
    op.map { f => s":$key ${toSExp(f)}" }.getOrElse("")

  private def toSExp(b: Boolean): String = if (b) "t" else "nil"

  // a lot of legacy key names and conventions
  def toSExp(c: EnsimeConfig): String = s"""(
 :root-dir ${toSExp(c.getRoot)}
 :cache-dir ${toSExp(c.getCacheDir)}
 :scala-compiler-jars ${fsToSExp(c.getScalaCompilerJars.asScala)}
 :ensime-server-jars ${fsToSExp(c.getEnsimeServerJars.asScala)}
 :name "${c.getName}"
 :java-home ${toSExp(c.getJavaHome)}
 :java-flags ${ssToSExp(c.getJavaFlags.asScala)}
 :java-sources ${fsToSExp(c.getJavaSrc.asScala)}
 :java-compiler-args ${ssToSExp(c.getJavacOptions.asScala)}
 :reference-source-roots ${fsToSExp(c.getJavaSrc.asScala)}
 :scala-version ${toSExp(c.getScalaVersion)}
 :compiler-args ${ssToSExp(c.getScalacOptions.asScala)}
 :subprojects ${msToSExp(c.getModules.asScala.values)}
 :projects ${psToSExp(c.getProjects.asScala)}
)"""

  // a lot of legacy key names and conventions
  private def toSExp(m: EnsimeModule): String = {
    val roots = m.getMainRoots.asScala ++ m.getTestRoots.asScala
    s"""(
      :name ${toSExp(m.getName)}
      :source-roots ${fsToSExp(roots)}
      :targets ${fsToSExp(m.getTargets.asScala)}
      :test-targets ${fsToSExp(m.getTestTargets.asScala)}
      :depends-on-modules ${ssToSExp(m.getDependsOnNames.asScala.toList.sorted)}
      :compile-deps ${fsToSExp(m.getCompileJars.asScala)}
      :runtime-deps ${fsToSExp(m.getRuntimeJars.asScala)}
      :test-deps ${fsToSExp(m.getTestJars.asScala)}
      :doc-jars ${fsToSExp(m.getDocJars.asScala)}
      :reference-source-roots ${fsToSExp(m.getSourceJars.asScala)})"""
  }

  private def toSExp(p: EnsimeProject): String = s"""(
    :id ${toSExp(p.getId)}
    :depends ${idsToSExp(p.getDependsOn.asScala)}
    :sources ${fsToSExp(p.getSources.asScala)}
    :targets ${fsToSExp(p.getTargets.asScala)}
    :scalac-options ${ssToSExp(p.getScalacOptions.asScala)}
    :javac-options ${ssToSExp(p.getJavacOptions.asScala)}
    :library-jars ${fsToSExp(p.getLibraryJars.asScala)}
    :library-sources ${fsToSExp(p.getLibrarySources.asScala)}
    :library-docs ${fsToSExp(p.getLibraryDocs.asScala)})"""

  private def toSExp(id: EnsimeProjectId): String =
    s"""(:project ${toSExp(id.getProject)} :config ${toSExp(id.getConfig)})"""

  private def idsToSExp(ids: Iterable[EnsimeProjectId]): String =
    if (ids.isEmpty) "nil"
    else ids.toSeq.sortBy(_.toString).map(toSExp).mkString("(", " ", ")")

}
