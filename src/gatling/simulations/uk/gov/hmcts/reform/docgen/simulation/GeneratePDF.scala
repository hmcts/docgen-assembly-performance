package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.scenarios.postGeneratePDF
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}

import scala.concurrent.duration._


class GeneratePDF extends Simulation {

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val docGenScenarios = List (

    postGeneratePDF.postUser.inject(
      rampUsers(10) during(2 minutes)
    )
  )

  setUp(docGenScenarios)
    .protocols(httpConf)
    .maxDuration(2 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
