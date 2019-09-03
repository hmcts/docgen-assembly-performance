package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.feed.CreateResourceAccess
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations.createAnnotationHttp
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations.getAnnotationsHttp
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet.{createAnnotationSetHttp, deleteAnnotationSetHttp,getAnnotationSetsHttp}
import uk.gov.hmcts.reform.docgen.scenarios.annotations.DeleteAnnotations.deleteAnnotationHttp
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateAnnotationsTest extends Simulation {
	val dataFeeder= csv("feeder.csv").circular

	val httpProtocol = http
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.annotationURL)

	val createAnnotationSetScn = scenario("create  nnotation set")
  	.feed(CreateResourceAccess.feed).feed(dataFeeder)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(createAnnotationSetHttp)
		.exec(createAnnotationHttp)
		.exec(getAnnotationsHttp)

	val getAnnotationSetScn = scenario("get  nnotation set")
		.feed(CreateResourceAccess.feed)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(createAnnotationSetHttp)
		.exec(getAnnotationSetsHttp)


	val deleteAnnotationSetScn = scenario("delete annotation set")
		.feed(CreateResourceAccess.feed)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(deleteAnnotationSetHttp)

	val createAnnotationsScn = scenario("create annotations")
		.feed(CreateResourceAccess.feed)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(createAnnotationHttp)

	val deleteAnnotationsScn = scenario("delete annotations")
		.feed(CreateResourceAccess.feed)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(deleteAnnotationHttp)


	setUp(createAnnotationSetScn.inject(
		constantUsersPerSec(1) during (1 seconds)
	))
		.protocols(httpProtocol)
		.maxDuration(2 minutes)



}