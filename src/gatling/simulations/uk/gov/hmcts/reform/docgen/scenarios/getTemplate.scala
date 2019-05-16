package uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.util.{Env, IDAMHelper, S2SHelper, S2sHelperTest}

object getTemplate {

 val thinkTime = Environment.thinkTime
 val env = new Env()
 val getUserHttp= exec(http("TX050_EM_DA_Template_Definition")
   .get(s"/api/form-definitions/${env.getTemplateID}")
   .header("Authorization", "Bearer ${accessToken}")
   //.header("ServiceAuthorization", "Bearer ${s2sToken}")
   .header("ServiceAuthorization", "Bearer ${jwt}")
   .header("Content-Type", "application/json")
   .check(status is 200))

 val getRequest = scenario("Template Definition")
   .exec(IDAMHelper.getIdamAuthCode)
   //.exec(S2SHelper.S2SAuthToken)
   .feed(S2sHelperTest.otpFeeder)
   .exec(S2sHelperTest.requestJwt)
 .exec(S2sHelperTest.checkJwt)
   .exec(getUserHttp)

}
