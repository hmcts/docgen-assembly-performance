package uk.gov.hmcts.reform.docgen.feed

import java.util.UUID

import io.gatling.core.feeder.Feeder

object CreateResourceAccess {

  private def randomUUID: String = UUID.randomUUID().toString

  def feed: Feeder[String] = Iterator.continually {
    Map(
      "annotationSetId" -> randomUUID

    )
  }
}
