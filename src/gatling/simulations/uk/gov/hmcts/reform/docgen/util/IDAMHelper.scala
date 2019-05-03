package uk.gov.hmcts.reform.docgen.util
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object IDAMHelper {

  private val USERNAME = "testytesttest@test.net"
  private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"
  val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))


  val getIdamAuthCode =
    exec(http("IDAM Token")
      .post(Env.getIdamUrl+"/oauth2/authorize/?response_type=code&client_id="+ Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect)
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth(USERNAME,PASSWORD)
      .header("Content-Length", "0")
      .check(status.is(200))
      .check(jsonPath("$..code").optional.saveAs("serviceauthcode")))
      .pause(3)

      .doIf(session => session.contains("serviceauthcode")) {
        exec(http("Oauth2Token")
          .post(Env.getIdamUrl + "/oauth2/token?grant_type=authorization_code&code=" + "${serviceauthcode}" + "&client_id="+Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect + "&client_secret=" + Env.getOAuthSecret)
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Content-Length", "0")
          .check(jsonPath("$..access_token").optional.saveAs("accessToken"))
          .check(status.is(200)))
          .pause(5)
      }

      .exec {
        session =>
          //println("this is access token....." + session("accessToken").as[String])
          session
      }

}
