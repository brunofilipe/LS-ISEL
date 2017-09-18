package pt.isel.ls.academicActivities.utils.http;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class FormUrlEncoded {
    public static boolean canRetriveFrom(HttpServletRequest t) {
        return t.getHeader("Content-Type") != null && t.getHeader("Content-Type").equals("application/x-www-form-urlencoded");
    }

    public static String retrieveFrom(HttpServletRequest req) throws IOException {
        byte[] bytes = new byte[req.getContentLength()];
        req.getInputStream().read(bytes);
        String content = new String(bytes);
        return URLDecoder.decode(content, "UTF-8");
    }
}
