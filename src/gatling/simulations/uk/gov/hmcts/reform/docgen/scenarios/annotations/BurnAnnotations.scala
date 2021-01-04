package uk.gov.hmcts.reform.docgen.scenarios.annotations

object BurnAnnotations {

 /*// val testUtil = new TestUtil()
  //val annotationsUtil = new AnnotationsUtil()
 // val documentId = annotationsUtil.uploadDocument(testConfigOnePagePDF.pdfName, testUtil);
  //val thinkTime =  Environment.thinkTime

  val createAnnotationSetHttp = exec(http("Post Create annotation sets")
    .post(Environment.baseURL + "/api/annotation-sets")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .header("Content-Type", "application/json")
    .body(StringBody(annotationsUtil.createAnnotationSet(documentId).toString()))
    .check(status is 201)).pause(thinkTime)


  val createAnnotationHttp = http("Generate annotations")
    .post(Environment.baseURL + "/api/annotations")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .header("Content-Type", "application/json")
    .body(StringBody(annotationsUtil.createAnnotations(annotationsUtil.getAnnotationSetId).toString))

  val documentJsonObject = new JSONObject()
  documentJsonObject.put("inputDocumentId", documentId)

  val burnDocumentHttp = exec(http("TX01_EM_ANNT_Burn_Annotations")
    .post(Environment.NPA_URL + "/api/document-tasks")
    .header("Authorization", testUtil.getIdamAuth())
    .header("ServiceAuthorization", testUtil.getS2sAuth())
    .header("Content-Type", "application/json")
    .body(StringBody(documentJsonObject.toString()))
    .check(status is 201)).pause(thinkTime)

  val burnAnnotationWithDocument = scenario("Burn annotation with document")
    .exec(documentId)
    .exec(createAnnotationSetHttp)
    .exec(createAnnotationHttp)
    .exec(burnDocumentHttp)
}

object testConfigOnePagePDF {
  var pdfName = "pdf-test-file.pdf"
  var pages = 10
  var totalAnnotations = 100*/
}
