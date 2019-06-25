package uk.gov.hmcts.reform.docgen.util;

import java.util.Base64;
import java.util.Properties;

public class Env {
    private static String file = "CV-CMC-GOR-ENG-0004-UI-Test.docx";
    static Properties defaults = new Properties();

    static {
        defaults.setProperty("PROXY", "false");
        defaults.setProperty("TEST_URL", "");
        defaults.setProperty("IDAM_API_BASE_URI", "http://idam-api-idam-sprod.service.core-compute-idam-sprod.internal");
        defaults.setProperty("OAUTH_CLIENT", "webshow");
        defaults.setProperty("IDAM_WEBSHOW_WHITELIST", "https://em-show-aat.service.core-compute-aat.internal/oauth2/callback");
        defaults.setProperty("FUNCTIONAL_TEST_CLIENT_OAUTH_SECRET", "IL0GzT26MeityyHNbrwK8EDOmByLTBzk");
        defaults.setProperty("S2S_BASE_URI", "http://rpe-service-auth-provider-sprod.service.core-compute-sprod.internal");
        defaults.setProperty("FUNCTIONAL_TEST_CLIENT_S2S_TOKEN", "ZTUJMGDXR4ATXB4O");
        defaults.setProperty("S2S_SERVICE_NAME", "em_gw");
    }

    public static String getUseProxy() {
        return require("PROXY");
    }

    public static String getTestUrl() {
        return require("TEST_URL");
    }

    public static String require(String name) {
        return System.getenv(name) == null ? defaults.getProperty(name) : System.getenv(name);
    }

    public static String getIdamUrl() {
        return require("IDAM_API_BASE_URI");
    }

    public static String getOAuthClient() {
        return require("OAUTH_CLIENT");
    }

    public static String getOAuthRedirect() {
        return require("IDAM_WEBSHOW_WHITELIST");
    }

    public static String getOAuthSecret() {
        return require("FUNCTIONAL_TEST_CLIENT_OAUTH_SECRET");
    }

    public static String getS2sUrl() {
        return require("S2S_BASE_URI");
    }

    public static String getS2sSecret() {
        return require("FUNCTIONAL_TEST_CLIENT_S2S_TOKEN");
    }

    public static String getS2sMicroservice() {
        System.out.println("getS2sMicroservice");
        return require("S2S_SERVICE_NAME");
    }

    public String getTemplateID() {
        return Base64.getEncoder().encodeToString(Env.file.getBytes());
    }



}
