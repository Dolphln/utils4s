package cn.thinkjoy.utils4s.scala

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * Created by jacksu on 15/11/28.
 */

/**
 * 通过被推选的政客给他的投票者一个减税的承诺的例子说明
 */

case class TaxCut(reduction: Int){
  println("reducing start now")
  Thread.sleep(200)
  println("reducing stop now")
}

object Government {
  //Promise 的完成和对返回的 Future 的处理发生在不同的线程
  def redeemCampaignPledge(): Future[TaxCut] = {
    val p = Promise[TaxCut]()
    Future {
      println("Starting the new legislative period.")
      p.success(TaxCut(20))
      Thread.sleep(200)
      println("We reduced the taxes! You must reelect us!!!!1111")
    }
    p.future
  }
}
object FutureAndPromise {

  def main(args: Array[String]) {
    //实现承诺
    val taxCutF: Future[TaxCut] = Government.redeemCampaignPledge()
    println("Now that they're elected, let's see if they remember their promises...")
    taxCutF.onComplete {
      case Success(TaxCut(reduction)) =>
        println(s"A miracle! They really cut our taxes by $reduction percentage points!")
      case Failure(ex) =>
        println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
    }
    Thread.sleep(1000)
  }
}
