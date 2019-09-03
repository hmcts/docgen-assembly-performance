package uk.gov.hmcts.reform.docgen.scenarios.annotations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._


object CreateAnnotationsSet {
 // val testUtil = new TestUtil()


  //val documentId = UUID.randomUUID.toString
  val annotationSetId = UUID.randomUUID.toString
  val feeder_json = jsonFile("documentIds.json").circular

  //println("document id "+documentId)
  println("annotationset  id "+annotationSetId)

  //val annotationsStructure = "{\"id\":\"92704e1b-9eea-4fb2-876e-5b5d960c9203\",\"documentId\":\"598f3514-b0d2-46ed-9c92-f345d3e2e817\"}"

  val createAnnotationSetHttp = feed(feeder_json).exec(http("Create annotation sets")
    .post("/api/annotation-sets/")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
   // .body(StringBody(annotationsStructure)).asJson
    .body(ElFileBody("createannotationset.json")).asJson
    .check(status is 201))

  val deleteAnnotationSetHttp = http("Delete annotation set")
    .delete("/api/annotation-sets/" + annotationSetId)
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)

  val getAnnotationSetsHttp = exec(http("get annotation sets")
    .get("/api/annotation-sets/")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    // .body(StringBody(annotationsStructure)).asJson
    //.body(ElFileBody("updateAnnotationset.json")).asJson
    .check(status is 200))

  val updateAnnotationSetsHttp = exec(http("get annotation sets")
    .post("/api/annotation-sets")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    // .body(StringBody(annotationsStructure)).asJson
    .body(ElFileBody("updateAnnotationset.json")).asJson
    .check(status is 201))




}
