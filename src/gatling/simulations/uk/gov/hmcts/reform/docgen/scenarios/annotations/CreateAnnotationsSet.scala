package uk.gov.hmcts.reform.docgen.scenarios.annotations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations.dataFeeder_large


object CreateAnnotationsSet {
 // val testUtil = new TestUtil()


  //val documentId = UUID.randomUUID.toString
 // val annotationSetId = UUID.randomUUID.toString
  val annosets_200 = jsonFile("create_anno_set_200MB.json").circular
  val annosets_500 = jsonFile("create_anno_set_500MB.json").circular
  val annosets_1000 = jsonFile("create_anno_set_1000MB.json").circular
  val annosets_small = jsonFile("documentIds.json").circular


  //println("document id "+documentId)
 // println("annotationset  id "+annotationSetId)
  //val documentId="884c63fe-d552-4b98-a090-16f0ba9268de"

  //val annotationsStructure = "{\"id\":\"92704e1b-9eea-4fb2-876e-5b5d960c9203\",\"documentId\":\"598f3514-b0d2-46ed-9c92-f345d3e2e817\"}"

  val createAnnotationSetHttp = exec(http("Create annotation sets")
    .post("/api/annotation-sets/")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
   // .body(StringBody(annotationsStructure)).asJson
    .body(ElFileBody("createannotationset.json")).asJson
    .check(status is 201))

  val deleteAnnotationSetHttp = http("Delete annotation set")
    .delete("/api/annotation-sets/${annotationSetId}" )
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)

  //if you have a document id
val getAnnoByDocId=
  feed(annosets_200).exec(http("get annotation sets updated")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(jsonPath("$..annotationSetId").saveAs("annotationSetId1")))
    .exec { session => println("thi is test "+session("annotationSetId1").as[String]); session}

  val annotationSets = feed(annosets_small).exec(http("TX040_EM_AnnoSet_Get_Status")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIfOrElse( session => session("httpStatus").as[String].matches("200"))
    {
      //feed(feeder_json)
      exec(http("TX050_EM_AnnoSet_Get")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("TX060_EM_AnnoSet_Create")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)

    }


  val annotationSet_200MB = feed(annosets_200).exec(http("TX070_EM_AnnoSet_Get_Status_200MB")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIfOrElse( session => session("httpStatus").as[String].matches("200"))
    {
      //feed(feeder_json)
      exec(http("TX080_EM_AnnoSet_Get_200MB")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))

    }
    {
      exec(http("TX090_EM_AnnoSet_Create_200MB")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)

    }

    .repeat(1000) {
      feed(dataFeeder_large).exec(http("TX02_EM_ANNT_Create_Annotations_Large")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }


  val annotationSet_500MB = feed(annosets_500).exec(http("TX0100_EM_AnnoSet_Get_Status_500MB")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIfOrElse( session => session("httpStatus").as[String].matches("200"))
    {
      //feed(feeder_json)
      exec(http("TX110_EM_AnnoSet_Get_500MB")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("TX120_EM_AnnoSet_Create_500MB")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)
    }

    .repeat(2500) {
      feed(dataFeeder_large).exec(http("TX02_EM_ANNT_Create_Annotations_Large")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }

  val annotationSet_1000MB = feed(annosets_200).exec(http("TX0130_EM_AnnoSet_Get_Status_1000MB")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIfOrElse( session => session("httpStatus").as[String].matches("200"))
    {
      //feed(feeder_json)
      exec(http("TX0140_EM_AnnoSet_Get_1000MB")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("TX0150_EM_AnnoSet_Create_1000MB")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(100)
    }
    .repeat(5000) {
      feed(dataFeeder_large).exec(http("TX02_EM_ANNT_Create_Annotations_Large")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }



  //if you have a document id
  val getAnnotationSetByAnnosetIdHttp =
    feed(annosets_200).exec(http("get annotation sets updated")
      .get("/api/annotation-sets/filter?documentId=${documentId}")
      // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
      .header("Authorization", "Bearer ${accessToken}")
      .header("ServiceAuthorization", "Bearer ${s2sToken}")
      .header("Content-Type", "application/json")
      .check(jsonPath("$..annotationSetId").saveAs("annotationSetId1")))
      .exec { session => println("thi is test "+session("annotationSetId1").as[String]); session}
      .exec(http("get annotation sets")
    .get("/api/annotation-sets/${annotationSetId1}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
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
