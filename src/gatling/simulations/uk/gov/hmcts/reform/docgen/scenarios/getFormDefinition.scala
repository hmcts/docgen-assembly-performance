package uk.gov.hmcts.reform.docgen.scenarios
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.JSONObject
import uk.gov.hmcts.reform.docgen.util.uk.gov.hmcts.reform.docgen.util.IDAMCode
import uk.gov.hmcts.reform.docgen.util.{Env, IdamHelper, S2SCode, TestUtil}

object getFormDefinition {
  val testUtil = new TestUtil()

 /* println("IDAM Token-->:" + testUtil.getIdamAuth())
  println("S2S Token-->:" + testUtil.getS2sAuth())
*/
  private val USERNAME = "testytesttest@test.net"
  private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"
  val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))

  println("OTP-->:" + otp)



  /*val getUserHttp= http("Form Definition")
    .get("/api/form-definitions/${testUtil.getTemplateID()}")
    .header("Authorization", "${accessToken}")
    .header("ServiceAuthorization", "${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getRequest = scenario("Template Definition")
    .exec(getUserHttp)*/

  val getAuthCode = scenario("AuthCode")
    .exec(IDAMCode.getIdamAuthCode)
    .exec(S2SCode.S2SAuthToken)

}
