package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._

class BurnAnnotationsTest extends Simulation {

  /*val httpProtocol = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))

  val burnAnnotationsScn = List (
    BurnAnnotations.burnAnnotationWithDocument.inject(rampUsers(100) during(60 minutes))
  )

  setUp(burnAnnotationsScn)
    .protocols(httpProtocol)
    .maxDuration(60 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )*/
}