package uk.gov.hmcts.reform.docgen.util;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Base64;

import static io.restassured.specification.ProxySpecification.host;

public class TestUtil {

    private final String idamAuth;
    private final String s2sAuth;
    private static String file = "CV-CMC-GOR-ENG-0004-UI-Test.docx";

    public TestUtil() {
        IdamHelper idamHelper = new IdamHelper(
            Env.getIdamUrl(),
            Env.getOAuthClient(),
            Env.getOAuthSecret(),
            Env.getOAuthRedirect()
        );

        S2sHelper s2sHelper = new S2sHelper(
            Env.getS2sUrl(),
            Env.getS2sSecret(),
            Env.getS2sMicroservice()
        );

        RestAssured.proxy = host("proxyout.reform.hmcts.net").withPort(8080);
        RestAssured.useRelaxedHTTPSValidation();

        idamAuth = idamHelper.getIdamToken();
        s2sAuth = s2sHelper.getS2sToken();

    }

    public RequestSpecification authRequest() {
        return RestAssured
            .given()
            .header("Authorization", idamAuth)
            .header("ServiceAuthorization", s2sAuth);
    }

    public String getIdamAuth() {
        return idamAuth;
    }

    public String getS2sAuth() {
        return s2sAuth;
    }


    public String getTemplateID() {
        return Base64.getEncoder().encodeToString(TestUtil.file.getBytes());
    }
}
