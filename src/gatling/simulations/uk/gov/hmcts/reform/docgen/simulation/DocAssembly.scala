package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.scenarios.PostGeneratePDFDocument
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}

import scala.concurrent.duration._


class DocAssembly extends Simulation {


  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val docAssemblyScenarios = List (
    PostGeneratePDFDocument.postUser.inject(
      atOnceUsers(1),
      //rampUsersPerSec(1) to 100 during(300 seconds)
      constantUsersPerSec(2) during (50 seconds)
    )
  )


  setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
