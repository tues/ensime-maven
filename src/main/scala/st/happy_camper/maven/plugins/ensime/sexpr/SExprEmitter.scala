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
    case SString(value)    => out.write("\"" + value + "\"")
    case STrue             => out.write("t")
    case SNil              => out.write("nil")

    case SKeyword(keyword) => out.write(":" + keyword)

    case SList(list) =>
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

    case SMap(map) =>
      out.write("(")
      map.headOption.foreach {
        case (key, value) =>
          emit(key, indent + 1)
          out.write("\n")
          out.write(" " * (indent + 3))
          emit(value, indent + 3)
          map.tail.foreach {
            case (key, value) =>
              out.write("\n")
              out.write(" " * (indent + 1))
              emit(key, indent + 1)
              out.write("\n")
              out.write(" " * (indent + 3))
              emit(value, indent + 3)
          }
      }
      out.write(")")
  }
}
