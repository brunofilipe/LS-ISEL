package pt.isel.ls.academicActivities.view.model.teacher;

import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class SingleTeacherViewJson implements Writable {
    private Teacher teacher;

    public SingleTeacherViewJson(IEntity teacher) {
        this.teacher = (Teacher)teacher;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj obj = new JsonObj(
                new JsonPairKeyValue("teacherName",new JsonText(teacher.getTeacherName())),
                new JsonPairKeyValue("teacherEmail",new JsonText(teacher.getTeacherEmail())),
                new JsonPairKeyValue("teacherId",new JsonText(teacher.getTeacherId()))
        );
        obj.writeTo(w);
    }
}
