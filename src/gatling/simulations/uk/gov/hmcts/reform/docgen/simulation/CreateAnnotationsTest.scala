package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.DeleteAnnotations.deleteAnnotationHttp
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class CreateAnnotationsTest extends Simulation {
	val dataFeeder= csv("feeder_large_reader.csv").circular

	val httpProtocol = http
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
		.baseUrl(Environment.annotationURL)


	//create annos and annosets
	val createAnnotations_Large_Scn = scenario("Create Annotations for large Docs ")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.randomSwitch(
			34d ->	exec(CreateAnnotationsSet.annotationSet_create_200MB),
			33d->exec(CreateAnnotationsSet.annotationSet_create_500MB),
			33d ->	exec(CreateAnnotationsSet.annotationSet_create_1000MB)
		)

	val create_Annotations_Small_Scn = scenario("Create Annotations For Small Docs")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(CreateAnnotationsSet.annotationSet_create_small)
		//.exec(createAnnotationSetHttp)

	val read_Annotations_Large_Scn = scenario("Read Annotations for large Docs")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.randomSwitch(
			34d ->	exec(CreateAnnotationsSet.annotationSet_existing_200MB),
			33d->exec(CreateAnnotationsSet.annotationSet_existing_500MB),
			33d ->	exec(CreateAnnotationsSet.annotationSet_existing_1000MB)
		)

	val read_Annotations_Small_Scn = scenario("Read Annotations For Small Docs")
		.exec(IDAMHelper.getIdamAuthCode)
		.exec( S2SHelper.getOTP)
		.exec(S2SHelper.S2SAuthToken)
		.exec(CreateAnnotationsSet.annotationSet_existing_small)



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

		create_Annotations_Small_Scn.inject(
			rampUsers(640) during (55 minutes)),

		createAnnotations_Large_Scn.inject(
			rampUsers(90) during (45 minutes)),

		read_Annotations_Large_Scn.inject(
			nothingFor(60 seconds),
			rampUsers(150) during (59 minutes)),

		read_Annotations_Small_Scn.inject(
			nothingFor(60 seconds),
			rampUsers(330) during (59 minutes)),

	)
		.protocols(httpProtocol)
		.maxDuration(65 minutes)



}