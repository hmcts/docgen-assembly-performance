package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios.bundling.CreateBundle
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateBundlingTest extends Simulation {


	val httpProtocol = http
		//.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.bundlingURL)


	//create annos and annosets
	val createBundling_Scn = scenario("Create Bundling For IAC ")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec(S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.repeat(1) {
		exec(CreateBundle.postCreateBundleReq_75MB)
		.exec(CreateBundle.singleBundle)
		.exec(CreateBundle.multiBundle)
		}
		.pause(10)

	//.exec(CreateBundle.postCreateBundleReq)
//		.pause(30)
	//  	.exec(StitchBundle.postStitchBundle)



	setUp(

		createBundling_Scn.inject(
			rampUsers(10) during (300))
	)
		.protocols(httpProtocol)
		.assertions(global.successfulRequests.percent.is(100))

}