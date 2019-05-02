package uk.gov.hmcts.reform.docgen.util
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object  S2SHelper {
  val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))

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
      .check(bodyString.saveAs("s2sToken")))
      //.check(jsonPath("$.s2sToken").optional.saveAs("s2sToken")))
      .exec {
      session =>
        println("S2S token::-->" + session("s2sToken").as[String])
        println("")
        session


    }

}
