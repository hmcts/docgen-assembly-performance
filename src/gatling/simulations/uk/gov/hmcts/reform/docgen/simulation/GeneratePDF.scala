package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.scenarios.postGeneratePDF
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios.getFormDefination

import scala.concurrent.duration._


class GeneratePDF extends Simulation {

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val docGenScenarios = List (

    getFormDefination.getRequest.inject(
      rampUsers(1) during(1 seconds)
    ),

    postGeneratePDF.postUser.inject(
      rampUsers(1) during(1 seconds)
    )
  )


  setUp(docGenScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
