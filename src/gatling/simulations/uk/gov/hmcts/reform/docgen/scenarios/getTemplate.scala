package uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.{Env, IDAMHelper, S2SHelper}

object getTemplate {

 val env = new Env()
 val getUserHttp= http("Template Definition")
   .get(s"/api/form-definitions/${env.getTemplateID}")
   .header("Authorization", "Bearer ${accessToken}")
   .header("ServiceAuthorization", "Bearer ${s2sToken}")
   .header("Content-Type", "application/json")
   .check(status is 200)

 val getRequest = scenario("Template Definition")
   .exec(IDAMHelper.getIdamAuthCode)
   .exec(S2SHelper.S2SAuthToken)
   .exec(getUserHttp)

}
