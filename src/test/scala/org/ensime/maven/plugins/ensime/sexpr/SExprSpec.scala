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
package sexpr

import java.io.ByteArrayOutputStream

import scalax.io.JavaConverters._

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification.Scope

/**
 * A spec for {@link SExpr}.
 * @author ueshin
 */
@RunWith(classOf[JUnitRunner])
object SExprSpecTest extends SExprSpec

class SExprSpec extends Specification {

  "SExprAsString" should {

    "treat S-String as String" in {
      val sstring = SString("string")
      sstring.as[String] must equalTo("\"string\"")
    }

    "treat S-True as String" in {
      val strue = STrue
      strue.as[String] must equalTo("t")
    }

    "treat S-Nil as Strin" in {
      val snil = SNil
      snil.as[String] must equalTo("nil")
    }

    "treat S-Keyword as String" in {
      val skeyword = SKeyword("keyword")
      skeyword.as[String] must equalTo(":keyword")
    }

    "treat S-List as String" in {
      val slist = SList(Seq(SString("string"), STrue, SMap(Seq(
        (SKeyword("key"), SString("value")))), SNil))
      slist.as[String] must equalTo("""("string"
                                      | t
                                      | (:key
                                      |    "value")
                                      | nil)""".stripMargin)
    }

    "treat empty S-List as String" in {
      val slist = SList(Seq.empty)
      slist.as[String] must equalTo("()")
    }

    "treat S-Map as String" in {
      val smap = SMap(Seq(
        (SKeyword("key1"), SList(Seq(SString("string"), STrue))),
        (SKeyword("key2"), SList(Seq(SString("string"), SNil)))))
      smap.as[String] must equalTo("""(:key1
                                     |   ("string"
                                     |    t)
                                     | :key2
                                     |   ("string"
                                     |    nil))""".stripMargin)
    }

    "treat empty S-Map as String" in {
      val smap = SMap(Seq())
      smap.as[String] must equalTo("()")
    }
  }
}
