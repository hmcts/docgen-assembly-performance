package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object CreateBundle {
  val caseFeeder_30MB=csv("case_data_30MB.csv").circular
  val caseFeeder_100MB=csv("case_data_100MB.csv").circular
  val caseFeeder_300MB=csv("case_data_300MB.csv").circular


  val postCreateBundleReq_30MB = feed(caseFeeder_30MB).exec(http("TX040_EM_Bundle_Bundling_30MB")
    .post("/api/new-bundle")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("bundling-latest-original.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..id").saveAs("bundleId")))


  val postCreateBundleReq_100MB = feed(caseFeeder_100MB).exec(http("TX040_EM_Bundle_Bundling_100MB")
    .post("/api/new-bundle")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("bundling-latest-original.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..id").saveAs("bundleId")))

  val postCreateBundleReq_300MB = feed(caseFeeder_300MB).exec(http("TX040_EM_Bundle_Bundling_300MB")
    .post("/api/new-bundle")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("bundling-latest-original.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))
    .check(jsonPath("$..caseBundles").saveAs("caseBundles"))
    .check(jsonPath("$..id").saveAs("bundleId")))


}
