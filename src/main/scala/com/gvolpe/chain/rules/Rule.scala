package com.gvolpe.chain.rules

import scalaz._, Scalaz._
import scala.annotation.tailrec

trait Rule[A, B] {
  def handle(request: A): A \/ B
}

object RulesHandlerOps {

  implicit class RuleListHandler[A, B](list: List[Rule[A, B]]) {
    def handle(request: A): A \/ B = {
      @tailrec
      def loop(req: A, rules: List[Rule[A, B]]): A \/ B = {
        if (rules.isEmpty) req.left
        else rules.head.handle(req) match {
          case \/-(value)   => value.right
          case -\/(handler) => loop(req, rules.tail)
        }
      }
      loop(request, list)
    }
  }

}
