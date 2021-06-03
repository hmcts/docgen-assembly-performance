package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment._

object UploadDoc{

  val postDocument =
    exec(
      http("Upload document ${upload_type}")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "300_pdf.pdf")
            .contentType("application/pdf")
            .fileName("300_pdf.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl")))
}