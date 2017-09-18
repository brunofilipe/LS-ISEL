package pt.isel.ls.academicActivities.view.model.academicClass;

import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class SingleClassViewJson implements Writable {
    private Class aClass;

    public SingleClassViewJson(IEntity aClass) {
        this.aClass = (Class)aClass;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("classId",new JsonText(aClass.getClassId())),
                    new JsonPairKeyValue("courseAcr",new JsonText(aClass.getCourseAcr())),
                    new JsonPairKeyValue("academicSemesterYear",new JsonText(aClass.getAcademicSemesterYear())),
                    new JsonPairKeyValue("academicSemesterType",new JsonText(aClass.getAcademicSemesterType()))
            );
        obj.writeTo(w);
    }
}
