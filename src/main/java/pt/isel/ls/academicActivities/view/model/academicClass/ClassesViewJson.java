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

public class ClassesViewJson implements Writable {
    private List<Class> classes;

    public ClassesViewJson(List<IEntity> classes) {
        this.classes = classes
                .stream()
                .map(entity -> (Class)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[]objs = new JsonObj[classes.size()];
        int i = 0;
        for (Class c : classes){
            JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("classId",new JsonText(c.getClassId())),
                    new JsonPairKeyValue("courseAcr",new JsonText(c.getCourseAcr())),
                    new JsonPairKeyValue("academicSemesterYear",new JsonText(c.getAcademicSemesterYear())),
                    new JsonPairKeyValue("academicSemesterType",new JsonText(c.getAcademicSemesterType()))
            );
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
