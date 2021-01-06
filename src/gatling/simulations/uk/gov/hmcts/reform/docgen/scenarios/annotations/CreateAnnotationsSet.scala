package uk.gov.hmcts.reform.docgen.scenarios.annotations

import io.gatling.core.Predef._
import io.gatling.http.Predef._



object CreateAnnotationsSet {
 // val testUtil = new TestUtil()


  //val documentId = UUID.randomUUID.toString
 // val annotationSetId = UUID.randomUUID.toString
  val annosets_200 = csv("read_anno_set_200MB.csv").circular
  val annosets_500 = csv("read_anno_set_500MB.csv").circular
  val annosets_1000 = csv("read_anno_set_1000MB.csv").circular
  val annosets_small = csv("read_annosets_smalldocs_new.csv").circular
  val annosets_200_new = jsonFile("create_anno_set_200MB_new.json").circular
  val annosets_500_new = jsonFile("create_anno_set_500MB_new.json").circular
  val annosets_1000_new = jsonFile("create_anno_set_1000MB_new.json").circular
  val annosets_small_new = jsonFile("create_annoset_smalldocs_new.json").circular
  val dataFeeder_large= csv("feeder_large_reader.csv").circular
  val dataFeeder_small= csv("feeder_small_reader.csv").circular
  val dataFeeder_large_create= csv("feeder_large_create.csv").circular
  val dataFeeder_small_create= csv("feeder_small_create.csv").circular
  val dataFeeder_large_status=csv("LargeDocs.csv").circular
  val dataFeeder_small_update=csv("feeder_small_update.csv").circular
  //val dataFeeder_small_status=csv("SmallDocs.csv").circular



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



  //folllowing are for create
  val annotationSet_create_small = feed(annosets_small_new)
      .exec(http("AN01_010_SmallPages_CreateAnnotationSet")
        .post("/api/annotation-sets")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(10)

        .exec(http("AN01_020_SmallPages_GetAnnotationSet")
          .get("/api/annotation-sets/${annotationSetId}")
          // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
          .header("Authorization", "Bearer ${accessToken}")
          .header("ServiceAuthorization", "Bearer ${s2sToken}")
          .header("Content-Type", "application/json")
          .check(status.in(200)))

        .repeat(5) {
          feed(dataFeeder_small_create).exec(http("AN01_030_SmallPages_CreateAnnotations")
            .post("/api/annotations")
            .header("Authorization", "Bearer ${accessToken}")
            .header("ServiceAuthorization", "Bearer ${s2sToken}")
            .header("Content-Type", "application/json")
            //  .body(StringBody(annotationsStru))
            .body(ElFileBody("annotations.json")).asJson
            .check(status is 201)).pause(15)

            .exec(http("AN01_040_SmallPages_GetAnnotations1")
              .get("/api/annotations/${annotationId}")
              // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
              .header("Authorization", "Bearer ${accessToken}")
              .header("ServiceAuthorization", "Bearer ${s2sToken}")
              .header("Content-Type", "application/json")
              .check(status.in(200)))

            .feed(dataFeeder_small_update)
            .exec(http("AN01_050_SmallPages_UpdateAnnotations")
            .put("/api/annotations")
            .header("Authorization", "Bearer ${accessToken}")
            .header("ServiceAuthorization", "Bearer ${s2sToken}")
            .header("Content-Type", "application/json")
            //  .body(StringBody(annotationsStru))
            .body(ElFileBody("annotations.json")).asJson
            .check(status is 200)).pause(15)

            .exec(http("AN01_060_SmallPages_GetAnnotations2")
              .get("/api/annotations/${annotationId}")
              // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
              .header("Authorization", "Bearer ${accessToken}")
              .header("ServiceAuthorization", "Bearer ${s2sToken}")
              .header("Content-Type", "application/json")
              .check(jsonPath("$..page").is("1"))
              .check(status.in(200)))
    }

  //######################### for checking purpose-delete once check is complete ##########################
 /* val annotationSet_Status_200 = feed(dataFeeder_small_status).exec(http("AN01_010_200Pages_GetAnnoSet")
    .get("/api/annotation-sets/filter?documentId=${documentId_small}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
      .doIfOrElse(session => session("httpStatus").as[String].matches("404"))
  {
    exec {
    session =>
      val fw = new BufferedWriter(new FileWriter("documentIds_create_small.csv", true))
      try {
        fw.write(session("documentId_small").as[String]+"\r\n")
      }
      finally fw.close()
      session
  }
  }
  {
    exec {
    session =>
      val fw = new BufferedWriter(new FileWriter("documentIds_read_small.csv", true))
      try {
        fw.write(session("documentId_small").as[String]+"\r\n")
      }
      finally fw.close()
      session
  }
  }*/
  //########################## end of checking ##################################

  val annotationSet_create_200MB = feed(annosets_200_new).exec(http("AN01_010_200Pages_GetAnnoSet")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIf(session => session("httpStatus").as[String].matches("404"))
    {
      exec(http("AN01_020_200Pages_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(50)

        .repeat(1000) {
          feed(dataFeeder_large_create).exec(http("AN01_030_200Pages_CreateAnnotation")
            .post("/api/annotations")
            .header("Authorization", "Bearer ${accessToken}")
            .header("ServiceAuthorization", "Bearer ${s2sToken}")
            .header("Content-Type", "application/json")
            //  .body(StringBody(annotationsStru))
            .body(ElFileBody("annotations.json")).asJson
            .check(status is 201))
        }

    }


  val annotationSet_create_500MB = feed(annosets_500_new).exec(http("AN1_010_500Pages_GetAnnoSet")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIf(session => session("httpStatus").as[String].matches("404"))
    {
      exec(http("AN01_020_500Pages_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(50)

        .repeat(2500) {
          feed(dataFeeder_large_create).exec(http("AN01_030_500Pages_CreateAnnotation")
            .post("/api/annotations")
            .header("Authorization", "Bearer ${accessToken}")
            .header("ServiceAuthorization", "Bearer ${s2sToken}")
            .header("Content-Type", "application/json")
            //  .body(StringBody(annotationsStru))
            .body(ElFileBody("annotations.json")).asJson
            .check(status is 201))
        }

    }
  val annotationSet_create_1000MB = feed(annosets_1000_new).exec(http("AN01_010_1000Pages_GetAnnoSet")
    .get("/api/annotation-sets/filter?documentId=${documentId}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIf(session => session("httpStatus").as[String].matches("404"))
    {
      exec(http("AN01_020_1000Pages_CreateAnnotationSet")
        .post("/api/annotation-sets/")
        .header("Authorization", "Bearer ${accessToken}")
        .header("ServiceAuthorization", "Bearer ${s2sToken}")
        .header("Content-Type", "application/json")
        // .body(StringBody(annotationsStructure)).asJson
        .body(ElFileBody("createannotationset.json")).asJson
        .check(status is 201))
        .pause(50)
        .repeat(5000) {
          feed(dataFeeder_large_create).exec(http("AN01_030_1000Pages_CreateAnnotation")
            .post("/api/annotations")
            .header("Authorization", "Bearer ${accessToken}")
            .header("ServiceAuthorization", "Bearer ${s2sToken}")
            .header("Content-Type", "application/json")
            //  .body(StringBody(annotationsStru))
            .body(ElFileBody("annotations.json")).asJson
            .check(status is 201))
        }

    }


  // following is for read annosets and annos

  val annotationSet_existing_200MB = feed(annosets_200).exec(http("AN02_010_200Pages_GetAnnoSet")
    .get("/annotation-sets/${documentId_200}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200))
    .check(status.saveAs("httpStatus")))
    .doIf( session => session("httpStatus").as[String].matches("200")) {

        //feed(feeder_json)
          feed(dataFeeder_large).exec(http("AN02_020_200Pages_GetAnnotations")
          .get("/api/annotations/${annotationId}")
          .header("Authorization", "Bearer ${accessToken}")
          .header("ServiceAuthorization", "Bearer ${s2sToken}")
          .check(status is 200)).pause(50)

    }

  val annotationSet_existing_500MB = feed(annosets_500).exec(http("AN02_010_500Pages_GetAnnoSet")
    .get("/annotation-sets/${documentId_500}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200))
    .check(status.saveAs("httpStatus")))
    .doIf( session => session("httpStatus").as[String].matches("200")) {

        //feed(feeder_json)
        feed(dataFeeder_large).exec(http("AN02_020_500Pages_GetAnnotations")
          .get("/api/annotations/${annotationId}")
          .header("Authorization", "Bearer ${accessToken}")
          .header("ServiceAuthorization", "Bearer ${s2sToken}")
          .check(status is 200)).pause(50)

    }

  val annotationSet_existing_1000MB = feed(annosets_1000).exec(http("AN02_010_1000Pages_GetAnnoSet")
    .get("/annotation-sets/${documentId_1000}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200))
    .check(status.saveAs("httpStatus")))
    .doIf( session => session("httpStatus").as[String].matches("200")) {

        //feed(feeder_json)
        feed(dataFeeder_large).exec(http("AN02_020_1000Pages_GetAnnotations")
          .get("/api/annotations/${annotationId}")
          .header("Authorization", "Bearer ${accessToken}")
          .header("ServiceAuthorization", "Bearer ${s2sToken}")
          .check(status is 200)).pause(50)
      }


  val annotationSet_existing_small = feed(annosets_small).exec(http("AN02_010_SmallPages_GetAnnoSet")
    .get("annotation-sets/${documentId_small}")
    // /api/annotation-sets/filter?documentId=assets/non-dm.jpg
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .check(status.in(200,404))
    .check(status.saveAs("httpStatus")))
    .doIf( session => session("httpStatus").as[String].matches("200")) {

        //feed(feeder_json)
        feed(dataFeeder_small).exec(http("AN02_020_SmallPages_GetAnnotations")
          .get("/annotation-sets/${annotationSetId}/annotations/${annotationId}")
          .header("Authorization", "Bearer ${accessToken}")
          .header("ServiceAuthorization", "Bearer ${s2sToken}")
          .check(status is 200)).pause(50)
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
