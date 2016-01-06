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

import java.util.Properties

import org.ensime.maven.plugins.ensime.sexpr.SExpr
import org.ensime.maven.plugins.ensime.sexpr.SKeyword
import org.ensime.maven.plugins.ensime.sexpr.SMap

/**
 * Represents formatter preferences.
 * @author ueshin
 */
case class FormatterPreferences(

    // Indentation & Alignment
    indentSpaces: Option[Int] = None,
    indentWithTabs: Option[Boolean] = None,
    alignParameters: Option[Boolean] = None,
    doubleIndentClassDeclaration: Option[Boolean] = None,
    alignSingleLineCaseStatements: Option[Boolean] = None,
    alignSingleLineCaseStatements_maxArrowIndent: Option[Int] = None,
    indentPackageBlocks: Option[Boolean] = None,
    indentLocalDefs: Option[Boolean] = None,

    // Spaces
    spaceBeforeColon: Option[Boolean] = None,
    compactStringConcatenation: Option[Boolean] = None,
    spaceInsideBrackets: Option[Boolean] = None,
    spaceInsideParentheses: Option[Boolean] = None,
    preserveSpaceBeforeArguments: Option[Boolean] = None,
    spacesWithinPatternBinders: Option[Boolean] = None,

    // Scaladoc
    multilineScaladocCommentsStartOnFirstLine: Option[Boolean] = None,
    placeScaladocAsterisksBeneathSecondAsterisk: Option[Boolean] = None,

    // Miscellaneous
    formatXml: Option[Boolean] = None,
    rewriteArrowSymbols: Option[Boolean] = None,
    preserveDanglingCloseParenthesis: Option[Boolean] = None,
    compactControlReadability: Option[Boolean] = None) {
}

/**
 * A companion object for {@link FormatterPreferences}.
 * @author ueshin
 */
object FormatterPreferences {

  /**
   * Creates a new instance from Properties.
   * @param properties the properties
   * @return the new instance
   */
  def apply(properties: Properties) = {
    new FormatterPreferences(

      // Indentation & Alignment
      indentSpaces = Option(properties.getProperty("indentSpaces")).map(_.toInt),
      indentWithTabs = Option(properties.getProperty("indentWithTabs")).map(_.toBoolean),
      alignParameters = Option(properties.getProperty("alignParameters")).map(_.toBoolean),
      doubleIndentClassDeclaration = Option(properties.getProperty("doubleIndentClassDeclaration")).map(_.toBoolean),
      alignSingleLineCaseStatements = Option(properties.getProperty("alignSingleLineCaseStatements")).map(_.toBoolean),
      alignSingleLineCaseStatements_maxArrowIndent = Option(properties.getProperty("alignSingleLineCaseStatements.maxArrowIndent")).map(_.toInt),
      indentPackageBlocks = Option(properties.getProperty("indentPackageBlocks")).map(_.toBoolean),
      indentLocalDefs = Option(properties.getProperty("indentLocalDefs")).map(_.toBoolean),

      // Spaces
      spaceBeforeColon = Option(properties.getProperty("spaceBeforeColon")).map(_.toBoolean),
      compactStringConcatenation = Option(properties.getProperty("compactStringConcatenation")).map(_.toBoolean),
      spaceInsideBrackets = Option(properties.getProperty("spaceInsideBrackets")).map(_.toBoolean),
      spaceInsideParentheses = Option(properties.getProperty("spaceInsideParentheses")).map(_.toBoolean),
      preserveSpaceBeforeArguments = Option(properties.getProperty("preserveSpaceBeforeArguments")).map(_.toBoolean),
      spacesWithinPatternBinders = Option(properties.getProperty("spacesWithinPatternBinders")).map(_.toBoolean),

      // Scaladoc
      multilineScaladocCommentsStartOnFirstLine = Option(properties.getProperty("multilineScaladocCommentsStartOnFirstLine")).map(_.toBoolean),
      placeScaladocAsterisksBeneathSecondAsterisk = Option(properties.getProperty("placeScaladocAsterisksBeneathSecondAsterisk")).map(_.toBoolean),

      // Miscellaneous
      formatXml = Option(properties.getProperty("formatXml")).map(_.toBoolean),
      rewriteArrowSymbols = Option(properties.getProperty("rewriteArrowSymbols")).map(_.toBoolean),
      preserveDanglingCloseParenthesis = Option(properties.getProperty("preserveDanglingCloseParenthesis")).map(_.toBoolean),
      compactControlReadability = Option(properties.getProperty("compactControlReadability")).map(_.toBoolean))
  }

  /**
   * Treats FormatterPreferences as SExpr.
   */
  implicit object FormatterPreferencesAsSExpr extends As[FormatterPreferences, SExpr] {

    def as(fp: FormatterPreferences): SExpr = {
      SMap(
        // Indentation & Alignment
        fp.indentSpaces.map(i => ("indentSpaces".as[SKeyword], i.as[SExpr])).toList :::
          fp.indentWithTabs.map(b => ("indentWithTabs".as[SKeyword], b.as[SExpr])).toList :::
          fp.alignParameters.map(b => ("alignParameters".as[SKeyword], b.as[SExpr])).toList :::
          fp.doubleIndentClassDeclaration.map(b => ("doubleIndentClassDeclaration".as[SKeyword], b.as[SExpr])).toList :::
          fp.alignSingleLineCaseStatements.map(b => ("alignSingleLineCaseStatements".as[SKeyword], b.as[SExpr])).toList :::
          fp.alignSingleLineCaseStatements_maxArrowIndent.map(i => ("alignSingleLineCaseStatements.maxArrowIndent".as[SKeyword], i.as[SExpr])).toList :::
          fp.indentPackageBlocks.map(b => ("indentPackageBlocks".as[SKeyword], b.as[SExpr])).toList :::
          fp.indentLocalDefs.map(b => ("indentLocalDefs".as[SKeyword], b.as[SExpr])).toList :::

          // Spaces
          fp.spaceBeforeColon.map(b => ("spaceBeforeColon".as[SKeyword], b.as[SExpr])).toList :::
          fp.compactStringConcatenation.map(b => ("compactStringConcatenation".as[SKeyword], b.as[SExpr])).toList :::
          fp.spaceInsideBrackets.map(b => ("spaceInsideBrackets".as[SKeyword], b.as[SExpr])).toList :::
          fp.spaceInsideParentheses.map(b => ("spaceInsideParentheses".as[SKeyword], b.as[SExpr])).toList :::
          fp.preserveSpaceBeforeArguments.map(b => ("preserveSpaceBeforeArguments".as[SKeyword], b.as[SExpr])).toList :::
          fp.spacesWithinPatternBinders.map(b => ("spacesWithinPatternBinders".as[SKeyword], b.as[SExpr])).toList :::

          // Scaladoc
          fp.multilineScaladocCommentsStartOnFirstLine.map(b => ("multilineScaladocCommentsStartOnFirstLine".as[SKeyword], b.as[SExpr])).toList :::
          fp.placeScaladocAsterisksBeneathSecondAsterisk.map(b => ("placeScaladocAsterisksBeneathSecondAsterisk".as[SKeyword], b.as[SExpr])).toList :::

          // Miscellaneous
          fp.formatXml.map(b => ("formatXml".as[SKeyword], b.as[SExpr])).toList :::
          fp.rewriteArrowSymbols.map(b => ("rewriteArrowSymbols".as[SKeyword], b.as[SExpr])).toList :::
          fp.preserveDanglingCloseParenthesis.map(b => ("preserveDanglingCloseParenthesis".as[SKeyword], b.as[SExpr])).toList :::
          fp.compactControlReadability.map(b => ("compactControlReadability".as[SKeyword], b.as[SExpr])).toList)
    }
  }
}
