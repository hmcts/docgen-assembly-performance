package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.S2SHelper
import uk.gov.hmcts.reform.docgen.scenarios.bundling.UploadDoc
import scala.concurrent.duration._

class UploadDocTest extends Simulation {


  val httpProtocol = http
    		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))


  //Upload document
  val uploadDoc_Scn = scenario("Uploading Document for Bundling ")
    .exec(S2SHelper.getOTP)
    .exec(S2SHelper.S2SAuthToken)
    .exec(UploadDoc.postDocument)


  setUp(

    uploadDoc_Scn.inject(
      rampUsers(1) during (1 minutes))
  )
    .protocols(httpProtocol)
    //.maxDuration(90 minutes)


}