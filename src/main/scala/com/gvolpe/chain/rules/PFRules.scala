package com.gvolpe.chain.rules

import com.gvolpe.chain.rules.NumberRules.DemoState

object PFRules {

  type PFRule = PartialFunction[DemoState, Option[String]]

  val GreaterThanFiveRule = numberRule(_ > 5, "Greater than five")
  val LessThanFiveRule    = numberRule(_ < 5, "Less than five")
  val EqualsFiveRule      = numberRule(_ == 5, "Equals five")

  val LongRule: PFRule = {
    def condition(n : Int) = {
      (n > 500 && n % 3 == 0 && n % 5 == 0) || (n > 100 && n % 2 == 0)
    }
    numberRule(condition, "Long condition result")
  }

  private def numberRule(f: Int => Boolean, result: String): PFRule = {
    case DemoState(n) if f(n) => Option(result)
  }

  val NumberRules = GreaterThanFiveRule orElse LessThanFiveRule orElse EqualsFiveRule

  val PFNumberRules = List(
    LongRule,
    GreaterThanFiveRule,
    LessThanFiveRule,
    EqualsFiveRule
  ) reduceLeft (_ orElse _)

}
