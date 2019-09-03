package uk.gov.hmcts.reform.docgen.util;

/*import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;*/

import java.util.UUID;

public class AnnotationsUtil {

    /*private String annotationSetId;
    private String documentId;

    public String uploadDocument(String pdfName, TestUtil testUtil) throws Exception {
        String newDocUrl = testUtil.authRequest()
                .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("files", "test.pdf",  ClassLoader.getSystemResourceAsStream(pdfName), "application/pdf")
                .multiPart("classification", "PUBLIC")
                .request("POST", Environment.DM_URL() + "/documents")
                .getBody()
                .jsonPath()
                .get("_embedded.documents[0]._links.self.href");

        this.documentId = newDocUrl.substring(newDocUrl.lastIndexOf("/") + 1);
        return this.documentId;
    }

    public JSONObject createAnnotationSet(String documentId) {
        JSONObject annotationSetJsonObject = new JSONObject();
        this.annotationSetId = UUID.randomUUID().toString();
        annotationSetJsonObject.put("documentId", documentId);
        annotationSetJsonObject.put("id", this.annotationSetId);

        return annotationSetJsonObject;
    }

    public String getAnnotationSetId() {
        return this.annotationSetId;
    }

    public JSONObject createAnnotations(String annotationSetId) {
        JSONObject annotationJsonObject = new JSONObject();
        String annotationId = UUID.randomUUID().toString();
        annotationJsonObject.put("annotationSetId", annotationSetId);
        annotationJsonObject.put("id", annotationId);
        annotationJsonObject.put("page", "1");
        annotationJsonObject.put("color", "FFFF00");
        annotationJsonObject.put("type", "highlight");

        JSONArray commentsJsonArray = new JSONArray();
        JSONObject commentJsonObject = new JSONObject();
        String commentId = UUID.randomUUID().toString();
        commentJsonObject.put("id", commentId);
        commentJsonObject.put("content", "Added comment for performance test");
        commentJsonObject.put("annotationId", annotationId);
        commentsJsonArray.put(0, commentJsonObject);
        annotationJsonObject.put("comments", commentsJsonArray);

        JSONArray rectangles = new JSONArray();
        JSONObject rectangle = new JSONObject();
        String rectangleId = UUID.randomUUID().toString();
        rectangle.put("id", rectangleId);
        rectangle.put("annotationId", annotationId);
        rectangle.put("x", 0);
        rectangle.put("y", 0);
        rectangle.put("width", 10);
        rectangle.put("height", 10);
        rectangles.put(0, rectangle);
        annotationJsonObject.put("rectangles", rectangles);

        return annotationJsonObject;
    }*/
}
