package com.gvolpe.chain

object PFRulesCombinations extends App {

  type PFRuleOne    = PartialFunction[String, Option[Int]]
  type PFRuleOTwo   = PartialFunction[Int, Option[Boolean]]
  type PFRuleOThree = PartialFunction[Boolean, Option[Long]]

  val RuleOne: PFRuleOne = {
    case "start" => Some(1)
  }
  val DefaultRuleOne: PFRuleOne = {
    case _ => None
  }
  val RuleTwo, RuleThree = RuleOne

  val RuleFour: PFRuleOTwo = {
    case 1 => Some(true)
  }
  val DefaultRuleTwo: PFRuleOTwo = {
    case _ => None
  }
  val RuleFive = RuleFour

  val RuleSix: PFRuleOThree = {
    case true => Some(2L)
  }
  val DefaultRuleThree: PFRuleOThree = {
    case _ => None
  }
  val RuleSeven, RuleEight = RuleSix

  val rulesOne    = List(RuleOne, RuleTwo, RuleThree, DefaultRuleOne)     reduceLeft (_ orElse _)
  val rulesTwo    = List(RuleFour, RuleFive, DefaultRuleTwo)              reduceLeft (_ orElse _)
  val rulesThree  = List(RuleSix, RuleSeven, RuleEight, DefaultRuleThree) reduceLeft (_ orElse _)

  val result: Option[Long] = for {
    one   <- rulesOne("start")
    two   <- rulesTwo(one)
    three <- rulesThree(two)
  } yield three

  assert(result.contains(2))

  val result2: Option[Long] = for {
    one   <- rulesOne("default case")
    two   <- rulesTwo(one)
    three <- rulesThree(two)
  } yield three

  assert(result2.isEmpty)

}
