package uk.gov.hmcts.reform.docgen.scenarios.bundling
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.docgen.util.Env

object UploadDoc{

  val postDocument = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("Upload document ${upload_type}")
        .post(Env.getDocStoreUrl+"/documents")
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