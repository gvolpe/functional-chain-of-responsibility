package com.gvolpe.chain.rules

import com.gvolpe.chain.rules.NumberRules.DemoState

object PFRules {

  type PFRule = PartialFunction[DemoState, String]

  val GreaterThanFiveRule: PFRule = {
    case DemoState(number) if number > 5 => "Greater than five"
  }

  val LessThanFiveRule: PFRule = {
    case DemoState(number) if number < 5 => "Less than five"
  }

  val EqualsFiveRule: PFRule = {
    case DemoState(number) if number == 5 => "Equals five"
  }

  val NumberRules = GreaterThanFiveRule orElse LessThanFiveRule orElse EqualsFiveRule

  private val PFRuleList = GreaterThanFiveRule :: LessThanFiveRule :: EqualsFiveRule :: Nil
  val PFNumberRules = PFRuleList reduceLeft (_ orElse _)

}
