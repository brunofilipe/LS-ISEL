package pt.isel.ls.academicActivities.view.model.user;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class UsersViewJson implements Writable {
    private List<IEntity> users;

    public UsersViewJson(List<IEntity> users) {
        this.users = users;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[] objs = new JsonObj[users.size()];
        int i = 0;
        for (IEntity user : users) {
            JsonObj obj;
            if ( user instanceof Teacher) {
                Teacher teacher = (Teacher)user;
                obj = new JsonObj(
                        new JsonPairKeyValue("teacherName",new JsonText(teacher.getTeacherName())),
                        new JsonPairKeyValue("teacherEmail",new JsonText(teacher.getTeacherEmail())),
                        new JsonPairKeyValue("teacherId",new JsonText(teacher.getTeacherId()))
                );
            } else {
                Student student = (Student)user;
                obj = new JsonObj(
                        new JsonPairKeyValue("studentName",new JsonText(student.getStudentName())),
                        new JsonPairKeyValue("studentEmail",new JsonText(student.getStudentEmail())),
                        new JsonPairKeyValue("studentId",new JsonText(student.getStudentId())),
                        new JsonPairKeyValue("programmeAcr",new JsonText(student.getProgrammeAcr()))
                );
            }
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
