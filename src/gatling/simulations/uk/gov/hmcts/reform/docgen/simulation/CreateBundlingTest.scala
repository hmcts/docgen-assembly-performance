package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios.bundling.CreateBundle
import uk.gov.hmcts.reform.docgen.scenarios.stitching.StitchBundle
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateBundlingTest extends Simulation {

	val caseFeeder=csv("case_data.csv").circular

	val httpProtocol = http
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.bundlingURL)


	//create annos and annosets
	val createBundling_Scn = scenario("Create Bundling For SSCS ")
  	.feed(caseFeeder)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec(S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
  	.exec(CreateBundle.postCreateBundleReq)
//		.pause(30)
//  	.exec(StitchBundle.postStitchBundle)
		/*.randomSwitch(
			34d ->	exec(CreateAnnotationsSet.annotationSet_create_200MB),
			33d->exec(CreateAnnotationsSet.annotationSet_create_500MB),
			33d ->	exec(CreateAnnotationsSet.annotationSet_create_1000MB)
		)*/


	setUp(

		createBundling_Scn.inject(
			rampUsers(10) during (2 minutes))
	)
		.protocols(httpProtocol)
		.maxDuration(5 minutes)


}