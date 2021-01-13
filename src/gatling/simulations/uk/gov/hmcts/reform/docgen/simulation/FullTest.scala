package uk.gov.hmcts.reform.docgen.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import simulations.uk.gov.hmcts.reform.docgen.util.{Environment, Headers}
import uk.gov.hmcts.reform.docgen.scenarios._
import uk.gov.hmcts.reform.docgen.scenarios.annotations.CreateAnnotationsSet
import uk.gov.hmcts.reform.docgen.scenarios.bundling.CreateBundle
import uk.gov.hmcts.reform.docgen.scenarios.dm_store.DMStore
import uk.gov.hmcts.reform.docgen.scenarios.doc_assembly.getTemplate
import uk.gov.hmcts.reform.docgen.scenarios.doc_assembly.postGeneratePDF.postUserHttp
import uk.gov.hmcts.reform.docgen.util.{IDAMHelper, S2SHelper}

import scala.concurrent.duration._

class FullTest extends Simulation {

  val dataFeeder= csv("feeder_large_reader.csv").circular

  val httpProtocolAnnotation = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.annotationURL)

  val httpProtocolBundling = http
    //.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.bundlingURL)

  val httpProtocolDMStore = http
    //.proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.dmStoreURL)

  val httpProtocolDocAssembly = http
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))
    .baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  val createAnnotations_Scn = scenario("Annotations")
    .exec(IDAMHelper.getIdamAuthCode)
    .exec( S2SHelper.getOTP)
    .exec(S2SHelper.S2SAuthToken)
    .exec(CreateAnnotationsSet.annotationSet_create_small)

  //create annos and annosets
  val createBundling_Scn = scenario("Bundling")
    .exec(IDAMHelper.getIdamAuthCode)
    .exec(S2SHelper.getOTP)
    .exec(S2SHelper.S2SAuthToken)
    .repeat(1) {
      //exec(CreateBundle.postCreateBundleReq_30MB)
      randomSwitch(
        //34d -> exec(CreateBundle.postCreateBundleReq_15MB),
        100d -> exec(CreateBundle.postCreateBundleReq_75MB)
        //33d -> exec(CreateBundle.postCreateBundleReq_300MB)
      )
        .pause(10)
    }

  val DMStore_Scn = scenario("DM Store")
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

  val docAssembly_Scn = scenario("Doc assembly")
    .exec(IDAMHelper.getIdamAuthCode)
    .exec( S2SHelper.getOTP)
    .exec(S2SHelper.S2SAuthToken)
    //.exec(getUserHttp)
    .pause(1)
    .repeat(4) {
      exec(postUserHttp)
        .pause(10)
    }

  setUp(
    createAnnotations_Scn.inject(rampUsers(10) during (300)).protocols(httpProtocolAnnotation),
    createBundling_Scn.inject(rampUsers(10) during (300)).protocols(httpProtocolBundling),
    DMStore_Scn.inject(rampUsers(10) during (300)).protocols(httpProtocolDMStore),
    docAssembly_Scn.inject(rampUsers(10) during (300)).protocols(httpProtocolDocAssembly))
  //.maxDuration(90 minutes)


}
