package uk.gov.hmcts.reform.docgen.scenarios.annotations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment


object CreateAnnotations {
  val thinkTime = Environment.thinkTime
  val dataFeeder_large= csv("feeder_large.csv").circular
  val dataFeeder_small= csv("feeder_small.csv").circular
  val dataFeeder_small_create= csv("feeder_small_create.csv").circular
  val dataFeeder_large_create= csv("feeder_large_create.csv").circular
  //val annotationsStru  = "{\"id\":\"${id}\",\"annotationSetId\":\"${annotationSetId}\",\"page\":\"${page}\",\"color\":\"FFFF00\",\"comments\":[{\"id\":\"${commentId}\",\"annotationId\":\"${id}\",\"createdBy\":null,\"createdByDetails\":null,\"createdDate\":\"2018-11-07T14:21:33.538Z\",\"lastModifiedBy\":null,\"lastModifiedByDetails\":null,\"lastModifiedDate\":null,\"content\":\"added comment for testsssssssss\"}],\"rectangles\":[{\"y\":${y},\"x\":${x},\"width\":32.540813,\"height\":10.860495,\"id\":\"${rectangleId}\"}],\"type\":\"highlight\"}"


  val createAnnotationHttp_Large_Files = feed(dataFeeder_large_create).exec(http("TX02_EM_ANNT_Create_Annotations")
    .post("/api/annotations")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
  //  .body(StringBody(annotationsStru))
    .body(ElFileBody("annotations.json")).asJson
    .check(status is 201)).pause(10)

  val createAnnotationHttp_Small_Files = feed(dataFeeder_small_create).exec(http("TX02_EM_ANNT_Create_Annotations_Small")
    .post("/api/annotations")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    //  .body(StringBody(annotationsStru))
    .body(ElFileBody("annotations.json")).asJson
    .check(status is 201)).pause(10)




  val updateAnnotationHttp = feed(dataFeeder_small).exec(http("TX02_EM_ANNT_Create_Annotations")
    .put("/api/annotations")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    //.body(StringBody(annotationsStru))
    .body(ElFileBody("annotations.json")).asJson
    .check(status is 200)).pause(10)



  val deleteAnnotationHttp = http("Delete annotation")
    .delete("/api/annotations/${annotationId}")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)


   val getAnnotationsHttp = http("Get annotations")
    .get("/api/annotations")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)

  val getAnnotationsById_Small = feed(dataFeeder_small).exec(http("Get annotations by annotation Id Small")
    .get("/api/annotations/${annotationId}" )
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)).pause(50)

  val getAnnotationsById_Large = feed(dataFeeder_large).exec(http("Get annotations by annotation Id Large")
    .get("/api/annotations/${annotationId}" )
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)).pause(50)




  /*val createAnnotations = scenario("create an annotation")
    .exec(CreateAnnotationsSet.createAnnotationSet)
    .repeat(100) {
      pause(10)
      feed(dataFeeder)
        .feed(Iterator.continually(Map("annotationSetId" -> CreateAnnotationsSet.annotationSetId)))
        .exec(createAnnotationHttp).pause(10)
    }*/
}
