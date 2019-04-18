package uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.{Env, IDAMCode, S2SCode, TestUtil}

object getFormDefinition {

 val testUtil = new TestUtil()

 val getUserHttp= http("Form Definition")
   .get(s"/api/form-definitions/${testUtil.getTemplateID}")
   .header("Authorization", "Bearer" + " ${accessToken}")
   .header("ServiceAuthorization", "Bearer" + "${s2sToken}")
   .header("Content-Type", "application/json")
   .check(status is 200)

 val getRequest = scenario("Template Definition")
   .exec(IDAMCode.getIdamAuthCode)
   .exec(S2SCode.S2SAuthToken)
   .exec(getUserHttp)


}
