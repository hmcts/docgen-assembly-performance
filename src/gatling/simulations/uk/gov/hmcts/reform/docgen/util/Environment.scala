package simulations.uk.gov.hmcts.reform.docgen.util

import java.util.Base64

import com.typesafe.config.ConfigFactory

object Environment {
  val httpConfig = scala.util.Properties.envOrElse("httpConfig", "http")

  val users = scala.util.Properties.envOrElse("numberOfUser", "10")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "500")
  val idamCookieName="SESSION_ID"

  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://dg-docassembly-aat.service.core-compute-aat.internal")
  val annotationURL = scala.util.Properties.envOrElse("annoURL", "http://em-anno-aat.service.core-compute-aat.internal")
  val bundlingURL = scala.util.Properties.envOrElse("bundleURL", "http://em-ccd-orchestrator-aat.service.core-compute-aat.internal")
  val dmStoreURL = scala.util.Properties.envOrElse("dmStoreURL", "http://dm-store-aat.service.core-compute-aat.internal")
  val idamURL = scala.util.Properties.envOrElse("idamURL", "https://idam-api.aat.platform.hmcts.net")
  val idamClient = scala.util.Properties.envOrElse("idamClient", "webshow")
  val idamSecret = scala.util.Properties.envOrElse("idamSecret", ConfigFactory.load.getString("auth.clientSecret"))
  val idamRedirectURL = scala.util.Properties.envOrElse("idamRedirectURL", "https://em-show-aat.service.core-compute-aat.internal/oauth2/callback")
  val s2sURL = scala.util.Properties.envOrElse("s2sURL", "http://rpe-service-auth-provider-aat.service.core-compute-aat.internal")
  val s2sService = scala.util.Properties.envOrElse("s2sService", "em_gw")
  val s2sSecret = scala.util.Properties.envOrElse("s2sSecret", ConfigFactory.load.getString("aat_service.pass"))
  val templateID = scala.util.Properties.envOrElse("templateID", Base64.getEncoder.encodeToString("CV-CMC-GOR-ENG-00023.docx".getBytes))

  //val bundlingURL = scala.util.Properties.envOrElse("bundleURL", "http://em-ccd-orchestrator-aat.service.core-compute-aat.internal")
  //val annotationURL = scala.util.Properties.envOrElse("annotationURL", "https://em-anno-aat-staging.service.core-compute-aat.internal")
  /*val DOCMOSIS_URL = "https://docmosis-development.platform.hmcts.net/"
  val IDAM_WEB_URL = "https://idam.preprod.ccidam.reform.hmcts.net"*/

  //val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2sSecret))

  val minThinkTime = 12
  val maxThinkTime = 15
  val waitForNextIteration = 80
  val constantthinkTime = 4
  val thinkTime =1
}
