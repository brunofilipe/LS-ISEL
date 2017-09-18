package pt.isel.ls.academicActivities.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.academicActivities.request.Request;

public class RequestTest {
    Request request;

    @Before
    public void init(){
        request = new Request("get","/courses/class");
    }

    @Test
    public void testGetMethod(){
        String method = "GET";
        assertEquals(method,request.getMethod());
    }

    @Test
    public void testGetPath(){
        String path = "/courses/class";
        assertArrayEquals(path.split("/"),request.getPath());
    }
}
