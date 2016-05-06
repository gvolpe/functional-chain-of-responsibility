package com.gvolpe.chain.rules

import scalaz._, Scalaz._

object NumberRules {

  case class DemoState(number: Int)

  case object GreaterThanFiveRule extends Rule[DemoState, String] {
    override def handle(state: DemoState): DemoState \/ String =
      if (state.number > 5) "Greater than five".right
      else state.left
  }

  case object LessThanFiveRule extends Rule[DemoState, String] {
    override def handle(state: DemoState): DemoState \/ String =
      if (state.number < 5) "Less than five".right
      else state.left
  }

  case object EqualsFiveRule extends Rule[DemoState, String] {
    override def handle(state: DemoState): DemoState \/ String =
      if (state.number == 5) "Equals five".right
      else state.left
  }

}
