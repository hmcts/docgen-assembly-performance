package uk.gov.hmcts.reform.docgen.util
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment

object IDAMHelper {

  private val USERNAME = "testytesttest@test.net"
  private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"
// private val USERNAME = "emshowcase@hmcts.net"
 // private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"
  // below are for aat
/*private val USERNAME = "bundle-tester--518511189@gmail.com"
  private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"*/


  val thinktime = Environment.thinkTime


  val getIdamAuthCode =
    exec(http("TX010_EM_Bundle_IdamAuthCode")
      .post(Env.getIdamUrl+"/oauth2/authorize/?response_type=code&client_id="+ Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect)
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth(USERNAME,PASSWORD)
      .header("Content-Length", "0")
      .check(status.is(200))
      .check(jsonPath("$..code").optional.saveAs("serviceauthcode")))
      .pause(50)

      .doIf(session => session.contains("serviceauthcode")) {
        exec(http("TX020_EM_Bundle_Oauth2Token")
          .post(Env.getIdamUrl + "/oauth2/token?grant_type=authorization_code&code=" + "${serviceauthcode}" + "&client_id="+Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect + "&client_secret=" + Env.getOAuthSecret)
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Content-Length", "0")
          .check(jsonPath("$..access_token").optional.saveAs("accessToken"))
          .check(status.is(200)))
          .pause(50)
      }
}
