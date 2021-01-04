package uk.gov.hmcts.reform.docgen.scenarios.dm_store

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment._
import uk.gov.hmcts.reform.docgen.scenarios.bundling.Document

object DMStore {

  //Post a document

  val postDMStore_5MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_5MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "5MB.pdf")
            .contentType("application/pdf")
            .fileName("5MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl1")))

  val postDMStore_10MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_10MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "10MB.pdf")
            .contentType("application/pdf")
            .fileName("10MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl2")))

  val postDMStore_25MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_25MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "25MB.pdf")
            .contentType("application/pdf")
            .fileName("25MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl3")))

  val postDMStore_50MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_50MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "50MB.pdf")
            .contentType("application/pdf")
            .fileName("50MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl4")))

  val postDMStore_100MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_100MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "100MB.pdf")
            .contentType("application/pdf")
            .fileName("100MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl5")))

  val postDMStore_200MB = feed(Document.documentsFeeder).exec(session => {
    session.set("upload_type", "standard").set("feeder", Document.documentsFeeder)})
    .exec(
      http("TX040_EM_PostDMStore_200MB")
        .post("/documents")
        .headers(Map(
          "ServiceAuthorization" -> "Bearer ${s2sToken}",
          "user-id" -> "auto.test.cnp@gmail.com",
        ))
        .bodyPart(
          RawFileBodyPart("files", "200MB.pdf")
            .contentType("application/pdf")
            .fileName("200MB.pdf")).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status is 200, jsonPath("$._embedded.documents[0]._links.self.href").saveAs("fileUrl6")))


  //Retrieve a document by its ID

  val getDMStore_5MB = http("TX050_EM_GetDMStore_5MB")
    .get("${fileUrl1}")
  .headers(Map(
    "ServiceAuthorization" -> "Bearer ${s2sToken}",
    "user-id" -> "auto.test.cnp@gmail.com",
  ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getDMStore_10MB = http("TX050_EM_GetDMStore_10MB")
    .get("${fileUrl2}")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getDMStore_25MB = http("TX050_EM_GetDMStore_25MB")
    .get("${fileUrl3}")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getDMStore_50MB = http("TX050_EM_GetDMStore_50MB")
    .get("${fileUrl4}")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getDMStore_100MB = http("TX050_EM_GetDMStore_100MB")
    .get("${fileUrl5}")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getDMStore_200MB = http("TX050_EM_GetDMStore_200MB")
    .get("${fileUrl6}")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  //Retrieve the binary of a document by its ID

  val getBinaryDMStore_5MB = http("TX060_EM_GetBinaryDMStore_5MB")
    .get("${fileUrl1}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getBinaryDMStore_10MB = http("TX060_EM_GetBinaryDMStore_10MB")
    .get("${fileUrl2}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getBinaryDMStore_25MB = http("TX060_EM_GetBinaryDMStore_25MB")
    .get("${fileUrl3}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getBinaryDMStore_50MB = http("TX060_EM_GetBinaryDMStore_50MB")
    .get("${fileUrl4}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getBinaryDMStore_100MB = http("TX060_EM_GetBinaryDMStore_100MB")
    .get("${fileUrl5}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

  val getBinaryDMStore_200MB = http("TX060_EM_GetBinaryDMStore_200MB")
    .get("${fileUrl6}/binary")
    .headers(Map(
      "ServiceAuthorization" -> "Bearer ${s2sToken}",
      "user-id" -> "auto.test.cnp@gmail.com",
    ))
    //.header("Authorization", "Bearer ${accessToken}")
    //.header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200)

}
