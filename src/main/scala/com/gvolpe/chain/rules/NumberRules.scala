package com.gvolpe.chain.rules

import scalaz._, Scalaz._

object NumberRules {

  case class DemoState(number: Int)

  type FiveRule = Rule[String, DemoState]

  case object GreaterThanFiveRule extends FiveRule {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number > 5) "Greater than five".left
      else state.right
  }

  case object LessThanFiveRule extends FiveRule {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number < 5) "Less than five".left
      else state.right
  }

  case object EqualsFiveRule extends FiveRule {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number == 5) "Equals five".left
      else state.right
  }

}
