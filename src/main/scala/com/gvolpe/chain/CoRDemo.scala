package com.gvolpe.chain

import rules._
import NumberRules._
import RulesHandlerOps._

object CoRDemo extends App {

  val notEqualRules: List[Rule[DemoState, String]]  = GreaterThanFiveRule :: LessThanFiveRule :: EqualsFiveRule :: Nil
  val equalRules: List[Rule[DemoState, String]]     = EqualsFiveRule :: Nil
  val emptyRules: List[Rule[DemoState, String]]     = List.empty[Rule[DemoState, String]]

  for {
    x <-  notEqualRules handle DemoState(15)
    y <-  emptyRules    handle DemoState(15)
  } yield {
    println(s"$x | $y") // This line won't be reached
  }

  for {
    x <- notEqualRules  handle DemoState(15)
    y <- equalRules     handle DemoState(5)
  } yield {
    println(s"$x | $y")
  }

}
