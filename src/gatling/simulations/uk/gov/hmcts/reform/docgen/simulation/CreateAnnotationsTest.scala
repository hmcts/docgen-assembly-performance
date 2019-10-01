package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.feed.{CreateResourceAccess, RandomNumberGeneration}
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations.{getAnnotationsById_Small,getAnnotationsById_Large,createAnnotationHttp_Large_Files, createAnnotationHttp_Small_Files, getAnnotationsHttp}
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet.{annotationSets,deleteAnnotationSetHttp, getAnnoByDocId, annotationSet_200MB,annotationSet_500MB,annotationSet_1000MB}
import uk.gov.hmcts.reform.docgen.scenarios.annotations.DeleteAnnotations.deleteAnnotationHttp
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateAnnotationsTest extends Simulation {
	val intArray = Array(3,5,10)
	val dataFeeder= csv("feeder_large.csv").circular

	val httpProtocol = http
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.annotationURL)

	val createAnnotationSetScn = scenario("create  nnotation set")
  	.feed(CreateResourceAccess.feed).feed(dataFeeder)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(getAnnotationSetsHttp)
	//	.exec(createAnnotationSetHttp)

		.exec(createAnnotationHttp_Large_Files)
		//.exec(getAnnotationsHttp)

	val createAnnotationsScn_Large = scenario("Create Annotations for large Docs").feed(RandomNumberGeneration.numberfeed)
  			.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
.randomSwitch(
  	34d ->	exec(annotationSet_200MB),
  	33d->exec(annotationSet_500MB),
	  33d ->	exec(annotationSet_1000MB)
	)
	  	/*//.exec(createAnnotationSetHttp)
	  	.pause(5)
	    	.repeat(3) {
			exec(createAnnotationHttp_Large_Files).pause(5)*/
		//}
	val createAnnotationsScn_Small = scenario("Create Annotations For Small Docs").feed(RandomNumberGeneration.numberfeed)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(annotationSets)
		//.exec(createAnnotationSetHttp)
		.pause(5)
		.repeat(10) {
			exec(createAnnotationHttp_Small_Files).pause(5)
		}


	val getAnnotationSetScn = scenario("get  annotation set by document id")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(getAnnoByDocId)
		//.exec(getAnnotationSetsHttp)

	val getAnnotationbyIdScn_Small = scenario("Get Annotations Based On Annotations Id for small")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(createAnnotationSetHttp)
		.exec(getAnnotationsById_Small)

	val getAnnotationbyIdScn_Large = scenario("Get Annotations Based On Annotations Id for large")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(createAnnotationSetHttp)
		.exec(getAnnotationsById_Large)

	val getAnnotationsScn = scenario("get  nnotations")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(createAnnotationSetHttp)
		.exec(getAnnotationsHttp)


	val deleteAnnotationSetScn = scenario("delete annotation set")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(deleteAnnotationSetHttp)

	val createAnnotationsScn = scenario("create annotations")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(createAnnotationHttp_Large_Files)

	val deleteAnnotationsScn = scenario("delete annotations")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(deleteAnnotationHttp)


	setUp(

		createAnnotationsScn_Large.inject(
			rampUsers(90) during (55 minutes)),

		createAnnotationsScn_Small.inject(
			nothingFor(60 seconds),
			rampUsers(630) during (55 minutes)),

		getAnnotationbyIdScn_Large.inject(
			nothingFor(180 seconds),
			rampUsers(150) during (55 minutes)),

		getAnnotationbyIdScn_Small.inject(
			nothingFor(180 seconds),
			rampUsers(330) during (55 minutes)),

	)
		.protocols(httpProtocol)
		.maxDuration(65 minutes)



}