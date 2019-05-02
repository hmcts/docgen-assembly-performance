# docgen-assembly-performance
Document Generation Assembly Performance Test Framework

## Build and run instructions
1. Build
```
 FUNCTIONAL_TEST_CLIENT_S2S_TOKEN=XXXX FUNCTIONAL_TEST_CLIENT_OAUTH_SECRET=XXXX IDAM_API_BASE_URI=XXXX S2S_BASE_URI=XXXX IDAM_WEBSHOW_WHITELIST=XXXX baseURL=XXXX mvn gatling:test -Dgatling.simulationClass=uk.gov.hmcts.reform.docgen.simulation.GeneratePDF
```
2. **Contact EM & Docgen team to get the build config**