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

import scalax.io.Codec
import scalax.io.Output

/**
 * Represents S-Expression emitter.
 * @author ueshin
 */
class SExprEmitter(val out: Output)(implicit codec: Codec = Codec.default) {

  /**
   * Emits S-Expression.
   */
  def emit(sexpr: SExpr, indent: Int = 0): Unit = sexpr match {
    case SString(value)    => emitSString(value)
    case STrue             => emitSTrue
    case SNil              => emitSNil
    case SKeyword(keyword) => emitSKeyword(keyword)
    case SList(list)       => emitSList(list, indent)
    case SMap(map)         => emitSMap(map, indent)
  }

  @inline
  private def emitSString(value: String) = out.write("\"" + value + "\"")

  @inline
  private def emitSTrue = out.write("t")

  @inline
  private def emitSNil = out.write("nil")

  @inline
  private def emitSKeyword(keyword: String) = out.write(":" + keyword)

  @inline
  private def emitSList(list: Seq[SExpr], indent: Int) = {
    out.write("(")
    list.headOption.foreach { head =>
      emit(head, indent + 1)
      list.tail.foreach { sexpr =>
        out.write("\n")
        out.write(" " * (indent + 1))
        emit(sexpr, indent + 1)
      }
    }
    out.write(")")
  }

  @inline
  private def emitSMap(map: Seq[(SKeyword, SExpr)], indent: Int) = {
    out.write("(")
    map.headOption.foreach {
      case (SKeyword(key), value) =>
        emitSKeyword(key)
        out.write("\n")
        out.write(" " * (indent + 3))
        emit(value, indent + 3)
        map.tail.foreach {
          case (SKeyword(key), value) =>
            out.write("\n")
            out.write(" " * (indent + 1))
            emitSKeyword(key)
            out.write("\n")
            out.write(" " * (indent + 3))
            emit(value, indent + 3)
        }
    }
    out.write(")")
  }
}
