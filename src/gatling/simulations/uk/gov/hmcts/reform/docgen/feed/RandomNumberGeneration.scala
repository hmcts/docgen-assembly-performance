package uk.gov.hmcts.reform.docgen.feed

import java.util.Random

import io.gatling.core.feeder.Feeder

object RandomNumberGeneration {

  val intArray = Array(3,5,10,15)
  val idx = new Random().nextInt(intArray.length)
  val randomNumber = intArray(idx)
  def numberfeed: Feeder[String] = Iterator.continually {
        Map(
      "randomnumber" -> randomNumber.toString

    )
  }
}
