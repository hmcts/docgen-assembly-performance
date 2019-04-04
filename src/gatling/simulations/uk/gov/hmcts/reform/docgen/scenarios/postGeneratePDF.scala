package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.TestUtil

object postGeneratePDF {

  val testUtil = new TestUtil();
  val bodyString = s"{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": ${testUtil.getTemplateID()} }"

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
