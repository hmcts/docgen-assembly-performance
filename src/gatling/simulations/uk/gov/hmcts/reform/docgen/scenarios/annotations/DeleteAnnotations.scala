package uk.gov.hmcts.reform.docgen.scenarios.annotations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object DeleteAnnotations {

  val annotationId = UUID.randomUUID.toString

  val deleteAnnotationHttp = http("Delete annotation")
    .delete("/api/annotations" + annotationId)
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .check(status is 200)

  val deleteAnnotation = scenario("delete an annotation")
    .exec(deleteAnnotationHttp)
}
