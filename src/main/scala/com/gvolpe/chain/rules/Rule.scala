package com.gvolpe.chain.rules

import scalaz._, Scalaz._
import scala.annotation.tailrec

trait Rule[A, B] {
  def handle(request: B): A \/ B
}

object RulesHandlerOps {

  implicit class RuleListHandler[A, B](list: List[Rule[A, B]]) {
    def applyOnlyOneTailRec(request: B): A \/ B = {
      @tailrec
      def loop(req: B, rules: List[Rule[A, B]]): A \/ B = {
        if (rules.isEmpty) req.right
        else rules.head.handle(req) match {
          case -\/(value)   => value.left
          case \/-(handler) => loop(req, rules.tail)
        }
      }
      loop(request, list)
    }

    def applyOnlyOneTraverse(request: B): List[B] \/ A = {
      list traverseU (_.handle(request)) swap
    }
  }

}
