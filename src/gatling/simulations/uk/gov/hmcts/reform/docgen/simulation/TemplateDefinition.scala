package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios.getTemplate

import scala.concurrent.duration._

class TemplateDefinition extends Simulation{

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val docAssemblyScenarios = List (
    getTemplate.getRequest.inject(
      rampUsers(100) during(1 minutes)
    )
  )

  setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )

}