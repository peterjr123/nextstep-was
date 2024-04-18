package http;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class HttpSessionTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Session_Response.txt"));
        HttpSession session = new HttpSessionImpl();
        response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId());
        response.sendRedirect("/index.html");
    }

    @Test
    public void existSessionRequest() throws Exception {
        String fileName = "Http_Session_Request.txt";
        HttpSession existingSession = HttpSessionRepository.createSession();

        DataOutputStream dos = new DataOutputStream(createOutputStream(fileName));
        dos.writeBytes("GET /user/create HTTP/1.1\r\n");
        dos.writeBytes("Cookie: JSESSIONID=" + existingSession.getId() + "\r\n");
        dos.writeBytes("\r\n");

        HttpRequest request = new HttpRequest(createInputStream(fileName));
        String sessionId = request.getCookie("JSESSIONID");

        Assert.assertEquals(existingSession, HttpSessionRepository.getSession(sessionId));
    }

    @Test
    public void responseCookieHeaderCreation() {

    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
    private InputStream createInputStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(new File(testDirectory + fileName));
    }
}
