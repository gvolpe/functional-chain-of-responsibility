package com.gvolpe.chain.rules

import scalaz._, Scalaz._

object NumberRules {

  case class DemoState(number: Int)

  case object GreaterThanFiveRule extends Rule[String, DemoState] {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number > 5) "Greater than five".left
      else state.right
  }

  case object LessThanFiveRule extends Rule[String, DemoState] {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number < 5) "Less than five".left
      else state.right
  }

  case object EqualsFiveRule extends Rule[String, DemoState] {
    override def handle(state: DemoState): String \/ DemoState =
      if (state.number == 5) "Equals five".left
      else state.right
  }

}
