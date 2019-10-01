package uk.gov.hmcts.reform.docgen.scenarios.stitching

import io.gatling.core.Predef._
import io.gatling.http.Predef._
//import play.api.libs.json.{JsValue, Json}
//import simulations.uk.gov.hmcts.reform.docgen.bundling.util.{DMHelper, TestUtil}

object StitchBundle {




  val postStitchBundle = http("Stitch Bundle")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch_bundle.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("stitchresponseBody"))


  val postUser = scenario("Stitch Bundle")
    //.exec(dmHelper.uploadDocument("src/resources/annotationTemplate.pdf"))
    .exec(postStitchBundle)
    .exec { session => println(session("responseBody").as[String]); session}

}
