package simulations.uk.gov.hmcts.reform.docgen.scenarios
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios.getTemplate
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

object postGeneratePDF {

  val bodyString= "{\"formPayload\":{\"rowNum\":\"0\",\"displayComments\":\"false\",\"displaycode\":\"0\",\"countyCourt\":{\"name\":\"Derby\",\"address\":\"The Court House, 59 Fore Street, Edmonton, Derby\"},\"referenceNumber\":\"908MC017\",\"claimant\":{\"partyName\":\"Homs States Ltd\"},\"defendant\":{\"partyName\":\"May Wall\"},\"preferredCourt\":\"Nottingham County Court\",\"preferredCourtAddress\":\"60 Canal St, Nottingham NG1 7EJ\",\"judicial\":{\"lastName\":\"Lem\"},\"order\":{\"date\":\"2019-02-07\"},\"directionsStatement1isIncluded\":\"true\",\"directionsStatement2isIncluded\":\"true\",\"directionsStatement3isIncluded\":\"true\",\"directionsStatement4isIncluded\":\"true\",\"directionsAdditionalStatement1\":\"The defendant may resolve this dispute without a hearing\",\"orderDirections1isIncluded\":\"true\",\"orderDirections1\":{\"date\":\"2019-02-11\"},\"orderDirections2isIncluded\":\"true\",\"orderDirections2\":{\"date\":\"2019-02-18\"},\"orderDirections3isIncluded\":\"true\",\"orderDirections3\":{\"date\":\"2019-02-25\"},\"orderDirections4isIncluded\":\"true\",\"orderDirections4\":{\"date\":\"2019-03-04\"},\"orderDirections5\":{\"order\":\"The parties must consider settling this litigation by any means of Alternative Dispute Resolution including Mediation\",\"date\":\"2019-03-11\"},\"orderDirections6\":{\"order\":\"The parties must file with the court and exchange skeleton arguments at least three days before the trial,       preferably by email if that is possible\",\"date\":\"2019-03-18\"},\"orderDirections7\":{\"order\":\"Oral evidence will not be permitted at the hearing from a witness whose statement has not been served       in accordance with this order or has been served late, except with permission from the Court\",\"date\":\"2019-03-22\"},\"hearingisRequired\":\"true\",\"hearing\":{\"date\":\"2019-04-15\"},\"hearingDurationSelection\":1,\"hearingTimeSelection\":1,\"hearingStatement1\":\"Some additional step to bring on the day\"},\"outputType\":\"PDF\",\"templateId\":\"Q1YtQ01DLUdPUi1FTkctMDAwNC1VSS1UZXN0LmRvY3g=\"}"
  val thinktime = Environment.thinkTime

  val postUserHttp = http("TX040_EM_DA_PDF_Generation")
    .post("/api/template-renditions")
    .header("Authorization", "Bearer ${accessToken}")
    .header("ServiceAuthorization", "Bearer ${s2sToken}")
    .body(StringBody(bodyString))
    .header("Content-Type", "application/json")
    .check(status is 200)

  val postUser = scenario("PDF Generation")
    .exec((getTemplate.getRequest)).pause(5)
    .repeat(4) {
      pause(thinktime)
      //exec(IDAMHelper.getIdamAuthCode)
      //exec(S2SHelper.S2SAuthToken)
      exec(postUserHttp)
    }
    .pause(2950)

}
