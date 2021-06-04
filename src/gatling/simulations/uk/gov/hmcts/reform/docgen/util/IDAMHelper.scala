package uk.gov.hmcts.reform.docgen.util
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment._

object IDAMHelper {

  val getIdamAuthCode =
    exec(http("TX010_EM_Bundle_IdamAuthCode")
      .post(idamURL + "/oauth2/authorize/?response_type=code&client_id=" + idamClient + "&redirect_uri=" + idamRedirectURL + "&scope=openid profile roles")
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth("${username}","${password}")
      .header("Content-Length", "0")
      .check(status.is(200))
      .check(jsonPath("$..code").optional.saveAs("serviceauthcode")))
      .pause(2)//original value is 50

      .doIf(session => session.contains("serviceauthcode")) {
        exec(http("TX020_EM_Bundle_Oauth2Token")
          .post(idamURL + "/oauth2/token?grant_type=authorization_code&code=" + "${serviceauthcode}" + "&client_id=" + idamClient + "&redirect_uri=" + idamRedirectURL + "&client_secret=" + idamSecret)
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Content-Length", "0")
          .check(jsonPath("$..access_token").optional.saveAs("accessToken"))
          .check(status.is(200)))
          .pause(5)// original value is 50
      }

  /*val getIdamAuthCode=
    exec(http("TX010_EM_Bundle_IdamAuthCode")
      .post(Env.getIdamUrl+"/oauth2/authorize")
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth(USERNAME,PASSWORD)
      .header("Content-Length", "0")
      .check(status.is(200))
      .check(jsonPath("$..access_token").optional.saveAs("accessToken")))
      .pause(20)//original value is 50
   */
}
