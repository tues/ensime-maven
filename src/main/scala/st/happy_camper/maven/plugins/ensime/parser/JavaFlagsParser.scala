package st.happy_camper.maven.plugins.ensime.parser

import util.parsing.combinator.RegexParsers

/**
 * Parser which generates a list of strings from a string in form of
 * "-arg1 -arg2 -arg3"
 */
object JavaFlagsParser extends RegexParsers {
  def arg: Parser[String] = "-.[^\\s]+".r
  def separator: Parser[String] = "[\\s]".r

  def args = arg.* ~ (separator ~ arg).*

  def apply(input: String) = parseAll(args, input) match {
    case Success(result, _) => result._1
    case NoSuccess(_, _) => throw new IllegalArgumentException("Could not parse java flags")
  }
}
