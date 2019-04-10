package uk.gov.hmcts.reform.docgen.util


import io.gatling.core.Predef._
import io.gatling.http.Predef._


object  S2SCode {

  val S2SAuthToken =
  exec(http("S2S Auth Token")
  .post(Env.getS2sUrl+"/lease")
  .header("Content-Type", "application/json")
  .body(StringBody(
  """{
    "microservice": ${Env.getS2sMicroservice},
    "oneTimePassword": "${otp}"
  }"""
  ))
  .check(jsonPath("$..").saveAs("s2sToken")))

    .exec {
      session =>
        //println("this is a userid ....." + session("generatedemail").as[String])
        println("this is access token....." + session("s2sToken").as[String])

        session
    }

}
