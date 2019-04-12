package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.{IDAMCode, S2SCode, TestUtil}

object postGeneratePDF {

  val testUtil = new TestUtil();
  val bodyString = "{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": \"RkwtRlJNLUFQUC1FTkctMDAwMDIuZG9jeA==\"}"

  val postUserHttp = http("PDF Generation")
    .post("/api/template-renditions")
    .header("Authorization", "Bearer" + " ${accessToken}")
    .header("ServiceAuthorization", "Bearer" + "${s2sToken}")
    .body(StringBody(bodyString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postUser = scenario("PDF Generation")
    .exec(IDAMCode.getIdamAuthCode)
    .exec(S2SCode.S2SAuthToken)
    .exec(postUserHttp)
}
