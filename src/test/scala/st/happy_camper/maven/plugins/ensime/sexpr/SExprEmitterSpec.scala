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
package sexpr

import java.io.ByteArrayOutputStream

import scalax.io.JavaConverters._

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification.Scope

/**
 * A spec for {@link SExprEmitter}.
 * @author ueshin
 */
@RunWith(classOf[JUnitRunner])
object SExprEmitterSpecTest extends SExprEmitterSpec

class SExprEmitterSpec extends Specification {

  "SExprEmitter" should {

    "emit S-String" in new Context {
      val sstring = SString("string")

      emitter.emit(sstring)

      out.toString must equalTo("\"string\"")
    }

    "emit S-True" in new Context {
      val strue = STrue

      emitter.emit(strue)

      out.toString must equalTo("t")
    }

    "emit S-Nil" in new Context {
      val snil = SNil

      emitter.emit(snil)

      out.toString must equalTo("nil")
    }

    "emit S-Keyword" in new Context {
      val skeyword = SKeyword("keyword")

      emitter.emit(skeyword)

      out.toString must equalTo(":keyword")
    }

    "emit S-List" in new Context {
      val slist = SList(Seq(SString("string"), STrue, SMap(Seq(
        (SKeyword("key"), SString("value")))), SNil))

      emitter.emit(slist)

      out.toString must equalTo("""("string"
                                  | t
                                  | (:key
                                  |    "value")
                                  | nil)""".stripMargin)
    }

    "emit empty S-List" in new Context {
      val slist = SList(Seq.empty)

      emitter.emit(slist)

      out.toString must equalTo("()")
    }

    "emit S-Map" in new Context {
      val smap = SMap(Seq(
        (SKeyword("key1"), SList(Seq(SString("string"), STrue))),
        (SKeyword("key2"), SList(Seq(SString("string"), SNil)))))

      emitter.emit(smap)

      out.toString must equalTo("""(:key1
                                  |   ("string"
                                  |    t)
                                  | :key2
                                  |   ("string"
                                  |    nil))""".stripMargin)
    }

    "emit empty S-Map" in new Context {
      val smap = SMap(Seq())

      emitter.emit(smap)

      out.toString must equalTo("()")
    }
  }

  trait Context extends Scope {

    val out = new ByteArrayOutputStream

    val emitter = new SExprEmitter(out.asOutput)
  }
}
