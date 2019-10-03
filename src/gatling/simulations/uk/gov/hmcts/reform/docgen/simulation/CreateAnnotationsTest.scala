package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.feed.CreateResourceAccess
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.DeleteAnnotations.deleteAnnotationHttp
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateAnnotationsTest extends Simulation {
	val intArray = Array(3,5,10)
	val dataFeeder= csv("feeder_large_reader.csv").circular

	val httpProtocol = http
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.annotationURL)

	val createAnnotationSetScn = scenario("create  Annotation set")
  	.feed(CreateResourceAccess.feed).feed(dataFeeder)
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		//.exec(getAnnotationSetsHttp)
	//	.exec(createAnnotationSetHttp)

		.exec(createAnnotationHttp_Large_Files)
		//.exec(getAnnotationsHttp)

	val createAnnotationsScn_Large_ExistingAnnos = scenario("Create Annotations for large Docs")
  			.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
.randomSwitch(
  	34d ->	exec(annotationSet_200MB),
  	33d->exec(annotationSet_500MB),
	  33d ->	exec(annotationSet_1000MB)
	)
	val createAnnotationsScn_Large_NewAnnos = scenario("Create Annotations for large Docs for new annos")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.randomSwitch(
			34d ->	exec(annotationSet_200MB_newannoset),
			33d->exec(annotationSet_500MB_newannosets),
			33d ->	exec(annotationSet_1000MB_newannosets)
		)

	val createAnnotationsScn_Small_ExistingAnnoSets = scenario("Create Annotations For Small Docs")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(annotationSets)
		//.exec(createAnnotationSetHttp)
		.pause(5)
		.repeat(20) {
			exec(createAnnotationHttp_Small_Files).pause(5)
		}

	val createAnnotationsScn_Small_NewAnnoSets = scenario("Create Annotations For Small Docs for new Annosets")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(annotationSets_newannoset)
		//.exec(createAnnotationSetHttp)
		.pause(5)
		.repeat(20) {
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

		createAnnotationsScn_Large_ExistingAnnos.inject(
			rampUsers(48) during (55 minutes)),

		createAnnotationsScn_Large_NewAnnos.inject(
			rampUsers(48) during (55 minutes)),

		createAnnotationsScn_Small_ExistingAnnoSets.inject(
			nothingFor(60 seconds),
			rampUsers(320) during (55 minutes)),

		createAnnotationsScn_Small_NewAnnoSets.inject(
			nothingFor(60 seconds),
			rampUsers(320) during (55 minutes)),

		getAnnotationbyIdScn_Large.inject(
			nothingFor(120 seconds),
			rampUsers(150) during (58 minutes)),

		getAnnotationbyIdScn_Small.inject(
			nothingFor(120 seconds),
			rampUsers(330) during (58 minutes))
	)
		.protocols(httpProtocol)
		.maxDuration(65 minutes)



}