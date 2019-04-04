package uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.TestUtil

object getFormDefination {
  val testUtil = new TestUtil();

  println("IDAM Token-->:" + testUtil.getIdamAuth())
  println("S2S Token-->:" + testUtil.getS2sAuth())

  val getUserHttp= http("Form Defination Request")
    .get("/api/form-definitions/Q1YtQ01DLUdPUi1FTkctMDAwNC1VSS1UZXN0LmRvY3g=")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getRequest = scenario("Load Template Defination")
    .exec(getUserHttp)

}
