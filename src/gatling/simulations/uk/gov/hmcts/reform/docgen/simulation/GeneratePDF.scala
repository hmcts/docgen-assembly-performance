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

  val docAssemblyScenarios = List (
    postGeneratePDF.postUser.inject(
      //nothingFor(1 seconds),
      rampUsers(1) during(10 seconds)
      //constantUsersPerSec(25) during (10 seconds)
    )
  )


  setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(2 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
