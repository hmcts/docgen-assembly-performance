package uk.gov.hmcts.reform.docgen.simulation
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef.{Proxy, http}
import simulations.uk.gov.hmcts.reform.docgen.scenarios.postGeneratePDF
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios.getTemplate
import scala.concurrent.duration._

class PDFGeneration extends Simulation {
  val ThinkTime = Environment.thinkTime

  val httpConf = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val templateDefinition = scenario("Template Definition").exec(getTemplate.getRequest)

  val pdfGeneration = scenario("PDF Generation").exec(postGeneratePDF.postUser)

  setUp(
    templateDefinition.inject(rampUsers(2) during (1 minutes)),
    pdfGeneration.inject(rampUsers(2) during (1 minutes))
  ).protocols(httpConf)
}