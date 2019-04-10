package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios.getFormDefinition
import uk.gov.hmcts.reform.docgen.util.S2SCode
import uk.gov.hmcts.reform.docgen.util.uk.gov.hmcts.reform.docgen.util.IDAMCode

import scala.concurrent.duration._

class TemplateDefinition extends Simulation{

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

 /* val docAssemblyScenarios = List (
    getFormDefinition.getAuthCode.inject(
      rampUsers(1) during(1 seconds)
    )
  )*/

  val getAuthCode = scenario("AuthCode and S2S")
    .exec(IDAMCode.getIdamAuthCode)
    .exec(S2SCode.S2SAuthToken)

  /*setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )*/
  setUp(getAuthCode.inject(rampUsers(1) during (5 seconds))
  ).protocols(httpConf)

}