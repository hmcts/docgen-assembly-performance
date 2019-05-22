package uk.gov.hmcts.reform.docgen.util

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment


object  OTP {
  //def otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))
  var otpgen = ""
  def getotp() :String = {
    otpgen = (String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret)))
    println("sasasasasasasasa otp ererererererererer"+otpgen)
    otpgen


  }



  //println( "One Time Password::-->\n" + otp)
  val otpFeeder = Iterator.continually(Map( "OTPDD" -> ({
    getotp()
  })))
}