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
package org.ensime.maven.plugins.ensime;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.execution.MavenSession;

import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

@Mojo(name = "scalariformOnly", requiresProject = true)
final public class ScalariformMojo extends AbstractMojo {

  @Parameter(defaultValue="false")
  private boolean alignParameters;
  @Parameter(defaultValue="false")
  private boolean alignSingleLineCaseStatements;
  @Parameter(defaultValue="40")
  private int alignSingleLineCaseStatements_maxArrowIndent;
  @Parameter(defaultValue="false")
  private boolean compactControlReadability;
  @Parameter(defaultValue="false")
  private boolean compactStringConcatenation;
  @Parameter(defaultValue="true")
  private boolean doubleIndentClassDeclaration;
  @Parameter(defaultValue="true")
  private boolean formatXml;
  @Parameter(defaultValue="false")
  private boolean indentLocalDefs;
  @Parameter(defaultValue="true")
  private boolean indentPackageBlocks;
  @Parameter(defaultValue="2")
  private int indentSpaces;
  @Parameter(defaultValue="false")
  private boolean indentWithTabs;
  @Parameter(defaultValue="false")
  private boolean multilineScaladocCommentsStartOnFirstLine;
  @Parameter(defaultValue="false")
  private boolean placeScaladocAsterisksBeneathSecondAsterisk;
  @Parameter(defaultValue="false")
  private boolean preserveDanglingCloseParenthesis;
  @Parameter(defaultValue="false")
  private boolean preserveSpaceBeforeArguments;
  @Parameter(defaultValue="false")
  private boolean rewriteArrowSymbols;
  @Parameter(defaultValue="false")
  private boolean spaceBeforeColon;
  @Parameter(defaultValue="false")
  private boolean spaceInsideBrackets;
  @Parameter(defaultValue="false")
  private boolean spaceInsideParentheses;
  @Parameter(defaultValue="true")
  private boolean spacesWithinPatternBinders;

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject mavenProject;

  @Parameter(defaultValue = "${session}", readonly = true)
  private MavenSession mavenSession;

  @Component
  private BuildPluginManager pluginManager;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    executeMojo(
      plugin(
          groupId("org.scalariform"),
          artifactId("scalariform-maven-plugin"),
          version("0.1.4")
      ),
      goal("format"),
      configuration(
        element(name("alignParameters"), "" + alignParameters),
        element(name("alignSingleLineCaseStatements"),
          "" + alignSingleLineCaseStatements),
        element(name("alignSingleLineCaseStatements_maxArrowIndent"),
          "" + alignSingleLineCaseStatements_maxArrowIndent),
        element(name("compactControlReadability"),
          "" + compactControlReadability),
        element(name("compactStringConcatenation"),
          "" + compactStringConcatenation),
        element(name("doubleIndentClassDeclaration"),
          "" + doubleIndentClassDeclaration),
        element(name("formatXml"), "" + formatXml),
        element(name("indentLocalDefs"), "" + indentLocalDefs),
        element(name("indentPackageBlocks"), "" + indentPackageBlocks),
        element(name("indentSpaces"), "" + indentSpaces),
        element(name("indentWithTabs"), "" + indentWithTabs),
        element(name("multilineScaladocCommentsStartOnFirstLine"),
          "" + multilineScaladocCommentsStartOnFirstLine),
        element(name("placeScaladocAsterisksBeneathSecondAsterisk"),
          "" + placeScaladocAsterisksBeneathSecondAsterisk),
        element(name("preserveDanglingCloseParenthesis"),
          "" + preserveDanglingCloseParenthesis),
        element(name("preserveSpaceBeforeArguments"),
          "" + preserveSpaceBeforeArguments),
        element(name("rewriteArrowSymbols"), "" + rewriteArrowSymbols),
        element(name("spaceBeforeColon"), "" + spaceBeforeColon),
        element(name("spaceInsideBrackets"), "" + spaceInsideBrackets),
        element(name("spaceInsideParentheses"), "" + spaceInsideParentheses),
        element(name("spacesWithinPatternBinders"), "" + spacesWithinPatternBinders)
      ),
      executionEnvironment(
          mavenProject,
          mavenSession,
          pluginManager
      )
    );
  }
}
