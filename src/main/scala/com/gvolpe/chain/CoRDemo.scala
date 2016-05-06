package com.gvolpe.chain

import rules._
import NumberRules._
import RulesHandlerOps._

object CoRDemo extends App {

  val request = DemoState(5)

  val demoRules: List[Rule[DemoState, String]] = GreaterThanFiveRule :: LessThanFiveRule :: EqualsFiveRule :: Nil

  demoRules handle request map (value => println(value))

}
