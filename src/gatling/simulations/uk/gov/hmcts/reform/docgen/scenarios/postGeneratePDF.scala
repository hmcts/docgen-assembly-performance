package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

object postGeneratePDF {

  val bodyString = "{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": \"RkwtRlJNLUFQUC1FTkctMDAwMDIuZG9jeA==\"}"

  val postUserHttp = http("PDF Generation")
    .post("/api/template-renditions")
    .header("Authorization", "Bearer" + " ${accessToken}")
    .header("ServiceAuthorization", "Bearer" + "${s2sToken}")
    .body(StringBody(bodyString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postUser = scenario("PDF Generation")
    .exec(IDAMHelper.getIdamAuthCode)
    .exec(S2SHelper.S2SAuthToken)
    .exec(postUserHttp)
}
