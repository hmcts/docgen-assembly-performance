package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object CreateBundle {
  val caseFeeder_15MB=csv("case_data_15MB.csv").circular
  val caseFeeder_75MB=csv("case_data_75MB.csv").circular
  val caseFeeder_300MB=csv("case_data_300MB.csv").circular


  val postCreateBundleReq_15MB = feed(caseFeeder_15MB).exec(http("TX040_EM_Bundle_Bundling_15MB")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch-ccd-bundles.json")).asJson
    .check(status is 200)
    /*.check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL"))*/
  )


  val postCreateBundleReq_75MB = feed(caseFeeder_75MB).exec(http("TX040_EM_Bundle_Bundling_75MB")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("create_bundle.json")).asJson
    .check(status is 200)
   /* .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL"))*/
  )

  val postCreateBundleReq_300MB = feed(caseFeeder_300MB).exec(http("TX040_EM_Bundle_Bundling_300MB")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch-ccd-bundles.json")).asJson
    .check(status is 200)
    /*.check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL"))*/
  )

  val singleBundle =
    exec(http("TX040_EM_Bundle_SingleBundling")
      .post("/api/new-bundle")
      .header("Authorization", "Bearer ${accessToken}")
      .header("ServiceAuthorization", "Bearer ${s2sToken}")
      .header("Content-Type", "application/json")
      .body(ElFileBody("single-new-bundle.json")).asJson
      .check(status is 200)
    )

  val multiBundle =
    exec(http("TX050_EM_Bundle_MultiBundling")
      .post("/api/new-bundle")
      .header("Authorization", "Bearer ${accessToken}")
      .header("ServiceAuthorization", "Bearer ${s2sToken}")
      .header("Content-Type", "application/json")
      .body(ElFileBody("multi-new-bundle.json")).asJson
      .check(status is 200)
    )

}
