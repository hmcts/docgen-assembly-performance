package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.scenarios.PostGeneratePDFDocument
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}

import scala.concurrent.duration._


class DocAssembly extends Simulation {
  //  val bodyString = "{\"formPayload\":{},\"outputType\": \"PDF\",\"templateId\": \"RkwtRlJNLUFQUC1FTkctMDAwMDIuZG9jeA==\"}"
  //
  //  val httpProtocol = http
  //    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
  //    .baseUrl("https://dg-docassembly-aat.service.core-compute-aat.internal/api/template-renditions")
  //    .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzbW0wOGhpb21ycDgzNnRyb2VicXB0NWJiNiIsInN1YiI6IjEyMzE0MSIsImlhdCI6MTU1MzU5MzE5NSwiZXhwIjoxNTUzNjIxOTk1LCJkYXRhIjoiY2FzZXdvcmtlci1kaXZvcmNlLWNvdXJ0YWRtaW4sY2FzZXdvcmtlci10ZXN0LGNhc2V3b3JrZXItcmVmZXJlbmNlLWRhdGEsY2FzZXdvcmtlci1zc2NzLWNhbGxhZ2VudCxjYXNld29ya2VyLGNhc2V3b3JrZXItcHJvYmF0ZS1pc3N1ZXIsY2FzZXdvcmtlci1wcm9iYXRlLWV4YW1pbmVyLHBheW1lbnRzLGNhc2V3b3JrZXItZGl2b3JjZS1maW5hbmNpYWxyZW1lZHktY291cnRhZG1pbixjYXNld29ya2VyLWRpdm9yY2UtZmluYW5jaWFscmVtZWR5LXNvbGljaXRvcixjYXNld29ya2VyLXByb2JhdGUtYXV0aG9yaXNlcixjYXNld29ya2VyLXNzY3MsY2FzZXdvcmtlci1kaXZvcmNlLWZpbmFuY2lhbHJlbWVkeS1qdWRpY2lhcnksY2FzZXdvcmtlci1kaXZvcmNlLWZpbmFuY2lhbHJlbWVkeSxjYXNld29ya2VyLXNzY3MtanVkZ2UsY2FzZXdvcmtlci1zc2NzLXBhbmVsbWVtYmVyLGNhc2V3b3JrZXItZGl2b3JjZS1qdWRnZSxqdWktanVkZ2UsanVpLXBhbmVsbWVtYmVyLGNhc2V3b3JrZXItZGl2b3JjZSxjYXNld29ya2VyLWRpdm9yY2UtY291cnRhZG1pbi1sb2ExLGNhc2V3b3JrZXItdGVzdC1sb2ExLGNhc2V3b3JrZXItcmVmZXJlbmNlLWRhdGEtbG9hMSxjYXNld29ya2VyLXNzY3MtY2FsbGFnZW50LWxvYTEsY2FzZXdvcmtlci1sb2ExLGNhc2V3b3JrZXItcHJvYmF0ZS1pc3N1ZXItbG9hMSxjYXNld29ya2VyLXByb2JhdGUtZXhhbWluZXItbG9hMSxwYXltZW50cy1sb2ExLGNhc2V3b3JrZXItZGl2b3JjZS1maW5hbmNpYWxyZW1lZHktY291cnRhZG1pbi1sb2ExLGNhc2V3b3JrZXItZGl2b3JjZS1maW5hbmNpYWxyZW1lZHktc29saWNpdG9yLWxvYTEsY2FzZXdvcmtlci1wcm9iYXRlLWF1dGhvcmlzZXItbG9hMSxjYXNld29ya2VyLXNzY3MtbG9hMSxjYXNld29ya2VyLWRpdm9yY2UtZmluYW5jaWFscmVtZWR5LWp1ZGljaWFyeS1sb2ExLGNhc2V3b3JrZXItZGl2b3JjZS1maW5hbmNpYWxyZW1lZHktbG9hMSxjYXNld29ya2VyLXNzY3MtanVkZ2UtbG9hMSxjYXNld29ya2VyLXNzY3MtcGFuZWxtZW1iZXItbG9hMSxjYXNld29ya2VyLWRpdm9yY2UtanVkZ2UtbG9hMSxqdWktanVkZ2UtbG9hMSxqdWktcGFuZWxtZW1iZXItbG9hMSxjYXNld29ya2VyLWRpdm9yY2UtbG9hMSIsInR5cGUiOiJBQ0NFU1MiLCJpZCI6IjEyMzE0MSIsImZvcmVuYW1lIjoidGVzdEBURVNULkNPTSIsInN1cm5hbWUiOiJ0ZXN0QFRFU1QuQ09NIiwiZGVmYXVsdC1zZXJ2aWNlIjoiQ0NEIiwibG9hIjoxLCJkZWZhdWx0LXVybCI6Imh0dHBzOi8vd3d3LmNjZC5kZW1vLnBsYXRmb3JtLmhtY3RzLm5ldCIsImdyb3VwIjoiY2FzZXdvcmtlciJ9.03bQ9HBVSTyhtaV2jsIWenebPBg8IVHo6HBmqsP5bA0")
  //    .header("ServiceAuthorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWlfd2ViYXBwIiwiZXhwIjoxNTUzNjA3NTg4fQ.yLNPkPZYqvSzcbc4louYkqwHYmLlW10ZGMtJMB3s-rM21ywgoCDOdNc7JIslSfu8f6FgcDoLIjAc7Ymljy5XKA")
  //    //.body()
  //    .header("Content-Type", "application/json")
  //
  //
  //  val scn = scenario("Document Generation Performance")
  //    .exec(http("PDF Generation")
  //    .post("/"))
  //    .pause(5)
  //
  //  setUp(
  //    scn.inject(atOnceUsers(1))
  //  ).protocols(httpProtocol)

  val httpConf = http.baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val docAssemblyScenarios = List (
    PostGeneratePDFDocument.postUser.inject(
      atOnceUsers(1),
      rampUsersPerSec(1) to 100 during(300 seconds)
    )
  )


  setUp(docAssemblyScenarios)
    .protocols(httpConf)
    .maxDuration(1 minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime.toInt)
    )
}
