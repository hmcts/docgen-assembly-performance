package uk.gov.hmcts.reform.docgen.util

package uk.gov.hmcts.reform.docgen.util

import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.JSONObject

object IDAMCode {
  val testUtil = new TestUtil()

  /* println("IDAM Token-->:" + testUtil.getIdamAuth())
   println("S2S Token-->:" + testUtil.getS2sAuth())
 */
  private val USERNAME = "testytesttest@test.net"
  private val PASSWORD = "4590fgvhbfgbDdffm3lk4j"
  val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))

  println("OTP-->:" + otp)


  //floowing is the IdamAuthCode retrieval IdamAuthCode
  val getIdamAuthCode =
  /*feed(dataFeederSystemAuth).*/
    exec(http("SYS01_TX01_Oauth2Authorize")
      .post(Env.getIdamUrl+"/oauth2/authorize/?response_type=code&client_id="+ Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect)
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth("${sysauthemail}","${sysauthpassword}")//email -> cmc.ccd.perftest613a@notifications.service.gov.uk
      .header("Content-Length", "0")
      .check(status.is(200))
      .check(jsonPath("$..code").optional.saveAs("serviceauthcode")))

      .doIf(session => session.contains("serviceauthcode")) {
        exec(http("SYS01_TX02_Oauth2Token")
          .post(Env.getIdamUrl + "/oauth2/token?grant_type=authorization_code&code=" + "${serviceauthcode}" + "&client_id="+Env.getOAuthClient+"&redirect_uri=" + Env.getOAuthRedirect + "&client_secret=" + Env.getOAuthSecret)
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Content-Length", "0")
          .check(jsonPath("$..access_token").optional.saveAs("accessToken"))
          .check(status.is(200)))
          .pause(5)//change back to 90
      }

      .exec {
        session =>
          //println("this is a userid ....." + session("generatedemail").as[String])
          println("this is access token....." + session("accessToken").as[String])

          session
      }



}
