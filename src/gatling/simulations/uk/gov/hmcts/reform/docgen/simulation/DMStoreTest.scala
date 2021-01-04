package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.Environment
import uk.gov.hmcts.reform.docgen.scenarios._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet
import uk.gov.hmcts.reform.docgen.scenarios.dm_store.DMStore
import uk.gov.hmcts.reform.docgen.scenarios.doc_assembly.getTemplate
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class DMStoreTest extends Simulation {


  val httpProtocol = http
    //.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.dmStoreURL)


  //create annos and annosets
  val DMStore_Scn = scenario("DM Store For IAC")
    //.exec(IDAMHelper.getIdamAuthCode)
    .exec(S2SHelper.getOTP)
    .exec(S2SHelper.S2SAuthToken)
    .randomSwitch(
      17d -> exec(DMStore.postDMStore_5MB).exec(DMStore.getDMStore_5MB).exec(DMStore.getBinaryDMStore_5MB),
      17d-> exec(DMStore.postDMStore_10MB).exec(DMStore.getDMStore_10MB).exec(DMStore.getBinaryDMStore_10MB),
      17d -> exec(DMStore.postDMStore_25MB).exec(DMStore.getDMStore_25MB).exec(DMStore.getBinaryDMStore_25MB),
      17d -> exec(DMStore.postDMStore_50MB).exec(DMStore.getDMStore_50MB).exec(DMStore.getBinaryDMStore_50MB),
      16d-> exec(DMStore.postDMStore_100MB).exec(DMStore.getDMStore_100MB).exec(DMStore.getBinaryDMStore_100MB),
      16d -> exec(DMStore.postDMStore_200MB).exec(DMStore.getDMStore_200MB).exec(DMStore.getBinaryDMStore_200MB)
    )
        .pause(10)

  //.exec(CreateBundle.postCreateBundleReq)
  //		.pause(30)
  //  	.exec(StitchBundle.postStitchBundle)



  setUp(
    DMStore_Scn.inject(rampUsers(10) during (300)).protocols(httpProtocol))
  //.maxDuration(90 minutes)


}