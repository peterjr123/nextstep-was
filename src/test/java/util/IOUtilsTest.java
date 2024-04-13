package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import http.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @Test
    public void readFile() throws IOException {
        String filePath = "/index.html";
        Path path = Paths.get("./webapp"+ filePath);
        Assert.assertEquals(Files.size(path), IOUtils.readFile(filePath).length);
    }

    @Test
    public void readRequestHeader() throws IOException {
        String headerData = "" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";
        String data = headerData +
                "\n" +
                "body";

        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        Assert.assertEquals(headerData.length(), IOUtils.readRequestHeader(br).length());
    }
}
