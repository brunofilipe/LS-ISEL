package pt.isel.ls.academicActivities.engine;

import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.academicActivities.utils.json.*;

import java.io.*;
import java.time.LocalDate;

public class JsonTests {
    private StringWriter wr ;

    @Before
    public void init() throws IOException {
        wr = new StringWriter();
    }

    @Test
    public void testArrayWithMultipleJsonObjects() throws IOException {
        JsonObj obj1 = new JsonObj(new JsonPairKeyValue("name",new JsonText("Bruno")));
        JsonObj obj2 = new JsonObj(new JsonPairKeyValue("name",new JsonText("Nuno")));
        JsonObj obj3 = new JsonObj(new JsonPairKeyValue("name",new JsonText("Joao")));
        JsonArray arr = new JsonArray(obj1,obj2,obj3);
        arr.writeTo(wr);
        wr.close();
        System.out.println(wr);
    }

    @Test
    public void testObjectWithMultipleJsonPair() throws IOException {
        JsonObj obj = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Bruno")),
                new JsonPairKeyValue("age",new JsonText(21)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,3,20)))
        );
        obj.writeTo(wr);
        wr.close();
        System.out.println(wr);
    }

    @Test
    public void testArrayWithMultipleObjectsAndMultipleJsonPair() throws IOException {
        JsonObj obj1 = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Bruno")),
                new JsonPairKeyValue("age",new JsonText(21)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,3,20)))
        );

        JsonObj obj2 = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Joao")),
                new JsonPairKeyValue("age",new JsonText(20)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,4,25)))
        );

        JsonObj obj3 = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Nuno")),
                new JsonPairKeyValue("age",new JsonText(21)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,2,18)))
        );

        JsonArray array = new JsonArray(obj1,obj2,obj3);
        array.writeTo(wr);
        wr.close();
        System.out.println(wr);
    }

    @Test
    public void testComplexJson() throws IOException{
        JsonObj obj1 = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Bruno")),
                new JsonPairKeyValue("age",new JsonText(21)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,3,20)))
        );

        JsonObj obj2 = new JsonObj(
                new JsonPairKeyValue("name",new JsonText("Joao")),
                new JsonPairKeyValue("age",new JsonText(20)),
                new JsonPairKeyValue("city",new JsonText("Lisbon")),
                new JsonPairKeyValue("birthdate",new JsonText(LocalDate.of(1996,4,25)))
        );
        JsonArray array = new JsonArray(obj1,obj2);
        JsonObj school = new JsonObj(
                new JsonPairKeyValue("school",new JsonText("ISEL")),
                new JsonPairKeyValue("country",new JsonText("Portugal")),
                new JsonPairKeyValue("city",new JsonText("Lisbon"))
        );

        Json json = new Json(school,array);
        json.writeTo(wr);
        wr.close();
        System.out.println(wr);
    }

}
