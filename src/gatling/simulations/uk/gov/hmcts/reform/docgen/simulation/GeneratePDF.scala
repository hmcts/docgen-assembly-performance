package uk.gov.hmcts.reform.docgen.simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.scenarios.postGeneratePDF.postUserHttp
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios.getTemplate.getUserHttp
import uk.gov.hmcts.reform.docgen.util.{Env, IDAMHelper, OTP, S2SHelper}

import scala.concurrent.duration._


class GeneratePDF extends Simulation {

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)


  val postUser = scenario("PDF Generation")
    .exec(IDAMHelper.getIdamAuthCode)
   .exec( S2SHelper.getOTP)
  .exec(S2SHelper.S2SAuthToken)
    .exec(getUserHttp)
    .pause(30)
   .repeat(4) {
     exec(postUserHttp)
   }

  setUp(postUser.inject(
rampUsers(1) during(1 minutes)
))
    .protocols(httpConf)
    .maxDuration(5 minutes)

}
