package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios.bundling.CreateBundle
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateBundlingTest extends Simulation {

	val rampUpDurationMins = 2
	val rampDownDurationMins = 2
	val testDurationMins = 60
	val HourlyTarget1:Double = 69
	val RatePerSec1 = HourlyTarget1 / 3600
	val HourlyTarget2:Double = 139
	val RatePerSec2 = HourlyTarget2 / 3600
	val HourlyTarget3:Double = 12
	val RatePerSec3 = HourlyTarget3 / 3600

	val httpProtocol = http
		//.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.bundlingURL)

	// Create bundles
	val oldBundling_Scn = scenario("Create Bundling For IAC (Old API)")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec(S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
	  .exec(CreateBundle.oldCreateBundle)

	val singleBundling_Scn = scenario("Create Single Bundling For IAC")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec(S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(CreateBundle.singleBundle)

	val multiBundling_Scn = scenario("Create Multi Bundling For IAC")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec(S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
	  .exec(CreateBundle.multiBundle)

	setUp(
		oldBundling_Scn.inject(rampUsersPerSec(0.00) to (RatePerSec1) during (rampUpDurationMins minutes),
			constantUsersPerSec(RatePerSec1) during (testDurationMins minutes),
			rampUsersPerSec(RatePerSec1) to (0.00) during (rampDownDurationMins minutes)),

		singleBundling_Scn.inject(rampUsersPerSec(0.00) to (RatePerSec2) during (rampUpDurationMins minutes),
			constantUsersPerSec(RatePerSec2) during (testDurationMins minutes),
			rampUsersPerSec(RatePerSec2) to (0.00) during (rampDownDurationMins minutes)),

		multiBundling_Scn.inject(rampUsersPerSec(0.00) to (RatePerSec3) during (rampUpDurationMins minutes),
			constantUsersPerSec(RatePerSec3) during (testDurationMins minutes),
			rampUsersPerSec(RatePerSec3) to (0.00) during (rampDownDurationMins minutes)))

		.protocols(httpProtocol)

}