package uk.gov.hmcts.reform.docgen.scenarios.annotations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotations.dataFeeder_large_create



object CreateAnnotationsSet {
 // val testUtil = new TestUtil()


  //val documentId = UUID.randomUUID.toString
 // val annotationSetId = UUID.randomUUID.toString
  val annosets_200 = jsonFile("create_anno_set_200MB.json").circular
  val annosets_500 = jsonFile("create_anno_set_500MB.json").circular
  val annosets_1000 = jsonFile("create_anno_set_1000MB.json").circular
  val annosets_small = jsonFile("read_new_annosets_smalldocs.json").circular
  val annosets_200_new = jsonFile("create_anno_set_200MB_new.json").circular
  val annosets_500_new = jsonFile("create_anno_set_500MB_new.json").circular
  val annosets_1000_new = jsonFile("create_anno_set_1000MB_new.json").circular
  val annosets_small_new = jsonFile("create_new_annosets_smalldocs.json").circular


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

  val annotationSets = feed(annosets_small).exec(http("AN04_010_SmallDocs_GetStatus")
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
      exec(http("AN04_020_SmallDocs_GetAnnotaionSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN04_030_SmallDocs_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)

    }

  val annotationSets_newannoset = feed(annosets_small_new).exec(http("AN09_010_SmallDocs_GetStatus_NewAnnoSet")
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
      exec(http("AN09_020_SmallDocs_GetAnnotaionSet_NewAnnoSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN09_030_SmallDocs_CreateAnnotationSet_NewAnnoSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)

    }

  val annotationSet_200MB = feed(annosets_200).exec(http("AN01_010_200Pages_GetStatus")
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
      exec(http("AN01_020_200Pages_GetAnnotaionSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))

    }
    {
      exec(http("AN01_030_200Pages_CreateAnnotationSet")
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
      feed(dataFeeder_large_create).exec(http("AN01_040_200Pages_CreateAnnotation")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }


  val annotationSet_500MB = feed(annosets_500).exec(http("AN02_010_500Pages_GetStatus")
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
      exec(http("AN02_020_500Pages_GetAnnotaionSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
       // .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN02_030_500Pages_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)
    }

    .repeat(2000) {
      feed(dataFeeder_large_create).exec(http("AN02_040_500Pages_CreateAnnotation")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }

  val annotationSet_1000MB = feed(annosets_1000).exec(http("AN03_010_1000Pages_GetStatus")
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
      exec(http("AN03_020_1000Pages_GetAnnotaionSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN01_030_1000Pages_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)
    }
    .repeat(3000) {
      feed(dataFeeder_large_create).exec(http("AN03_040_1000Pages_CreateAnnotation")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }
  val annotationSet_200MB_newannoset = feed(annosets_200_new).exec(http("AN05_010_200Pages_GetStatus_NewAnnoSet")
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
      exec(http("AN05_020_200Pages_GetAnnotaionSet_NewAnnoset")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))

    }
    {
      exec(http("AN05_030_200Pages_CreateAnnotationSet_NewAnnoSet")
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
      feed(dataFeeder_large_create).exec(http("AN05_040_200Pages_CreateAnnotation_NewSet")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }


  val annotationSet_500MB_newannosets = feed(annosets_500_new).exec(http("AN06_010_500Pages_GetStatus_NewAnnoSet")
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
      exec(http("AN06_020_500Pages_GetAnnotaionSet_NewAnnoSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
      // .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN06_030_500Pages_CreateAnnotationSet_NewAnnoSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)
    }

    .repeat(2000) {
      feed(dataFeeder_large_create).exec(http("AN06_040_500Pages_CreateAnnotation_NewAnnoSet")
        .post("/api/annotations")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        //  .body(StringBody(annotationsStru))
        .body(ElFileBody("annotations.json")).asJson
        .check(status is 201))
    }

  val annotationSet_1000MB_newannosets = feed(annosets_1000_new).exec(http("AN07_010_1000Pages_GetStatus_NewAnnoSet")
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
      exec(http("AN07_020_1000Pages_GetAnnotaionSet_NewAnnoSet")
        .get("/api/annotation-sets/filter?documentId=${documentId}")
        // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        .check(jsonPath("$..annotationSetId").optional.saveAs("annotationSetId")))
        .exec { session => println("hey this is it"+session("annotationSetId").as[String]); session}
    }
    {
      exec(http("AN07_030_1000Pages_CreateAnnotationSet_NewAnnoSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)
    }
    .repeat(3000) {
      feed(dataFeeder_large_create).exec(http("AN07_040_1000Pages_CreateAnnotation_NewAnnoSet")
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
