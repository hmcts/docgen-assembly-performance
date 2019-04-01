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
      nothingFor(1 seconds),
      rampUsers(300) during(60 seconds),
      //constantUsersPerSec(3) during (60 seconds)
    )
  )


  setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
