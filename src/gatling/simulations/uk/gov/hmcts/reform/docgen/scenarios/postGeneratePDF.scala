package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.TestUtil

object postGeneratePDF {

  val bodyString = "{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": \"RkwtRlJNLUFQUC1FTkctMDAwMDIuZG9jeA==\"}"
  val testUtil = new TestUtil();

  println("IDAM Token-->:" + testUtil.getIdamAuth())
  println("S2S Token-->:" + testUtil.getS2sAuth())

  val postUserHttp = http("Generate PDF")
    .post("/api/template-renditions")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .body(StringBody(bodyString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postUser = scenario("Generate PDF Document")
    .exec(postUserHttp)
}
