package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object CreateBundle {
  val caseFeeder_30Pages=csv("case_data_30Pages.csv").circular
  val caseFeeder_100Pages=csv("case_data_100pages.csv").circular
  val caseFeeder_300Pages=csv("case_data_300Pages.csv").circular


  val postCreateBundleReq_30Pages = feed(caseFeeder_30Pages).exec(http("TX040_EM_Bundle_Bundling_30Pages")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch-ccd-bundles.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL")))


  val postCreateBundleReq_100Pages = feed(caseFeeder_100Pages).exec(http("TX040_EM_Bundle_Bundling_100Pages")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch-ccd-bundles.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL")))

  val postCreateBundleReq_300Pages = feed(caseFeeder_300Pages).exec(http("TX040_EM_Bundle_Bundling_300Pages")
    .post("/api/stitch-ccd-bundles")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("stitch-ccd-bundles.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..stitchedDocument.document_url").saveAs("stitchedDocumentURL")))


}
