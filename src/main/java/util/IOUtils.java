package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static byte[] readFile(String fileUrl) throws IOException {
        return Files.readAllBytes(Paths.get("./webapp" + fileUrl));
    }

    // last line include \n (line-feed)
    public static String readRequestHeader(BufferedReader br) throws IOException {
        String message = "";
        String line;

        while((line = br.readLine()) != null) {
            if("".equals(line))
                break;
            message += (line + "\n");
        }

        return message;
    }
}
