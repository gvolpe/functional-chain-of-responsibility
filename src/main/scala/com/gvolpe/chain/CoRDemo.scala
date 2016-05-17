package com.gvolpe.chain

import scalaz._, Scalaz._
import rules._
import NumberRules._
import RulesHandlerOps._

object CoRDemo extends App {

  val notEqualRules : List[Rule[String, DemoState]] = GreaterThanFiveRule :: LessThanFiveRule :: Nil
  val equalRules    : List[Rule[String, DemoState]] = EqualsFiveRule :: Nil
  val emptyRules    : List[Rule[String, DemoState]] = List.empty[Rule[String, DemoState]]

  println(">>> Functional version of the Chain of Responsibility pattern <<<")

  // Using rules defined as partial functions

  assert(PFRules.NumberRules(DemoState(51)) contains "Greater than five")
  assert(PFRules.NumberRules(DemoState(5))  contains "Equals five")
  assert(PFRules.NumberRules(DemoState(1))  contains "Less than five")

  // Using rules defined as a list of partial functions

  assert(PFRules.PFNumberRules(DemoState(41))   contains "Greater than five")
  assert(PFRules.PFNumberRules(DemoState(5))    contains "Equals five")
  assert(PFRules.PFNumberRules(DemoState(3))    contains "Less than five")
  assert(PFRules.PFNumberRules(DemoState(1500)) contains "Long condition result")

  // Using tail-recursive solution

  notEqualRules applyOnlyOneTailRec DemoState(14) leftMap (s => assert(s == "Greater than five"))
  emptyRules    applyOnlyOneTailRec DemoState(14) leftMap (s => sys.error("Assertion won't be reached"))

  // Using traverseU (map and then sequenceU)

  notEqualRules traverseU (_.handle(DemoState(2)))  leftMap (s => assert(s == "Less than five"))
  notEqualRules traverseU (_.handle(DemoState(18))) leftMap (s => assert(s == "Greater than five"))
  notEqualRules traverseU (_.handle(DemoState(5)))  leftMap (s => assert(s == "Won't reach this assertion. It'll return the right side."))

  equalRules    traverseU (_.handle(DemoState(5)))  leftMap (s => assert(s == "Equals five"))

  val state = DemoState(66)
  val x = notEqualRules map(_.handle(state)) sequenceU
  val y = notEqualRules traverseU (_.handle(state))

  assert(x == y)

  // Using traverseU fail-fast technique and swapping sides once is processed

  notEqualRules applyOnlyOneTraverse DemoState(15) map (s => assert(s == "Greater than five"))

  val z = for {
    x <- notEqualRules  applyOnlyOneTraverse DemoState(2)
    y <- equalRules     applyOnlyOneTraverse DemoState(5)
  } yield s"$x | $y"

  z map (s => assert(s == "Less than five | Equals five"))
}
