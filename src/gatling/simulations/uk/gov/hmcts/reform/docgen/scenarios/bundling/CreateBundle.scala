package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object CreateBundle {


  val postCreateBundleReq = http("New Bundle")
    .post("/api/new-bundle")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .header("Content-Type", "application/json")
    .body(ElFileBody("bundling-latest.json")).asJson
    .check(status is 200)
    .check(bodyString.saveAs("responseBody"))

  val postUser = scenario("New Bundle")
    .exec(postCreateBundleReq)
    .exec { session => println(session("responseBody").as[String]); session}
}
