package uk.gov.hmcts.reform.docgen.util
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment._

object S2sHelperTest {
  val authenticator: GoogleAuthenticator = new GoogleAuthenticator()

  val requestJwt = exec(
    http("S2S token")
      .post(s2sURL + "/lease")
      .formParam("microservice", "em_gw")
      .formParam("oneTimePassword", "${otp}")
      .check(bodyString.saveAs("jwt"))
  )

  val checkJwt = exec(
    http("Check token")
      .get("/details")
      .header("Authorization", "Bearer ${jwt}")
      .check(substring("em_gw"))
  )

  val otpFeeder = Iterator.continually(Map("otp" -> authenticator.getTotpPassword(s2sSecret)))
  exec { session => println("S2S Session" +session); session }
  exec { session => println("S2S Token::--\n>" + session("jwt").as[String]); session}


}
