package uk.gov.hmcts.reform.docgen.util
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment

object  S2SHelper {
  val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))
  val thinktime = Environment.thinkTime

  val S2SAuthToken =
    exec(http("S2S Token")
      .post(Env.getS2sUrl+"/lease")
      .header("Content-Type", "application/json")
      .body(StringBody(
        s"""{
       "microservice": "${Env.getS2sMicroservice}",
        "oneTimePassword": "${otp}"
        }"""
      )).asJson
      .check(bodyString.saveAs("s2sToken"))).pause(thinktime)
      .exec {
        session =>
          //println("S2S token::-->" + session("s2sToken").as[String])
          session
      }
}
