package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.TestUtil

object postGeneratePDF {

  val testUtil = new TestUtil();
  val bodyString = "{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": \"RkwtRlJNLUFQUC1FTkctMDAwMDIuZG9jeA==\"}"

  val postUserHttp = http("PDF Generation")
    .post("/api/template-renditions")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .body(StringBody(bodyString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postUser = scenario("PDF Generation")
    .exec(postUserHttp)
}
