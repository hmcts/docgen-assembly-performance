package uk.gov.hmcts.reform.docgen.scenarios.doc_assembly

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment._
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

object getTemplate {

  val getUserHttp = exec(http("TX040_EM_DA_Template_Definition")
    .get(s"/api/form-definitions/${templateID}")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    // .header("ServiceAuthorization", "Bearer ${jwt}")
    .header("Content-Type", "application/json")
    .check(status is 200))


  val getRequest = scenario("Template Definition")
    .exec(IDAMHelper.getIdamAuthCode)
    .exec(S2SHelper.S2SAuthToken)


}
