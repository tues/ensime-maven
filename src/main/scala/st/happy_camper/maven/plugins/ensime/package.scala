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
package st.happy_camper.maven.plugins

/**
 * An ensime package object.
 * @author ueshin
 */
package object ensime {

  /**
   * Represents a converter from some type to another type.
   * @author ueshin
   */
  trait As[A, B] {

    /**
     * Treats some type value as another type value.
     * @param a the value to be converted
     * @return the converted value
     */
    def as(a: A): B
  }

  /**
   * Represents a converter from type A to another type.
   * @author ueshin
   */
  class AAsB[A](a: A) {

    type AAs[B] = ({ type l[A] = As[A, B] })#l[A]

    /**
     * Treats type A value as another type value.
     * @return the converted value
     */
    def as[B: AAs] = implicitly[AAs[B]].as(a)
  }

  /**
   * Returns AAsB instance.
   * @param a the value
   * @return the instance
   */
  implicit def toAAsB[A](a: A) = new AAsB(a)
}
