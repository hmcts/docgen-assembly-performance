package uk.gov.hmcts.reform.docgen.util
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment

object  S2SHelper {
  val thinktime = Environment.thinkTime

  val getOTP =
  exec(
    session => {
      val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))
      session.set("OTP", otp)

    })

  val otpp="${OTP}"

  val S2SAuthToken =

    exec(http("TX030_EM_DA_S2S Token")
      .post(Env.getS2sUrl+"/lease")
      .header("Content-Type", "application/json")
      .body(StringBody(
        s"""{
       "microservice": "${Env.getS2sMicroservice}",
        "oneTimePassword": "${otpp}"
        }"""
      )).asJson
      .check(bodyString.saveAs("s2sToken"))
      .check(bodyString.saveAs("responseBody")))
     /* .exec { session => println("S2S Session" +session); session }
      .exec { session => println("S2S Responce Body::--\n>" + session("responseBody").as[String]); session}
      .exec { session => println("S2S Token::--\n>" + session("s2sToken").as[String]); session}*/
      //.exec { session => println("OTP ::--\n>" + session("otp").as[String]); session}
}