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

import scalax.io.Codec
import scalax.io.JavaConverters._

/**
 * Represents S-Expression used by ENSIME configuration file.
 * @author ueshin
 */
sealed trait SExpr
case class SString(value: String) extends SExpr
case class SInt(value: Int) extends SExpr
case object STrue extends SExpr
case object SNil extends SExpr
case class SKeyword(keyword: String) extends SExpr
case class SList(list: Seq[SExpr]) extends SExpr
case class SMap(map: Seq[(SKeyword, SExpr)]) extends SExpr

/**
 * A companion object for {@link SExpr}.
 * @author ueshin
 */
object SExpr {

  /**
   * Treats SExpr as String.
   */
  implicit object SExprAsString extends As[SExpr, String] {

    override def as(sexpr: SExpr) = {
      val bytes = new ByteArrayOutputStream
      val codec = Codec.default
      val emitter = new SExprEmitter(sexpr)
      emitter.emit(bytes.asOutput)(codec)
      new String(bytes.toByteArray, codec.charSet)
    }
  }

  /**
   * Treats Boolean as SExpr.
   */
  implicit object BooleanAsSExpr extends As[Boolean, SExpr] {

    override def as(b: Boolean) = {
      if (b) STrue else SNil
    }
  }

  /**
   * Treats Int as SExpr.
   */
  implicit object IntAsSExpr extends As[Int, SExpr] {

    override def as(i: Int) = {
      SInt(i)
    }
  }

  /**
   * Treats String as SExpr.
   */
  implicit object StringAsSExpr extends As[String, SExpr] {

    override def as(s: String) = {
      SString(s)
    }
  }

  /**
   * Treats String as SKeyword.
   */
  implicit object StringAsSKeyword extends As[String, SKeyword] {

    override def as(str: String) = {
      SKeyword(str)
    }
  }
}
