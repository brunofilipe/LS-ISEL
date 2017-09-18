package pt.isel.ls.academicActivities.view.model.student;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsViewJson implements Writable {
    private List<Student> students;

    public StudentsViewJson(List<IEntity> students) {
        this.students = students
                .stream()
                .map(entity -> (Student)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[] objs = new JsonObj[students.size()];
        int i = 0;
        for (Student student : students) {
            JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("studentName",new JsonText(student.getStudentName())),
                    new JsonPairKeyValue("studentEmail",new JsonText(student.getStudentEmail())),
                    new JsonPairKeyValue("studentId",new JsonText(student.getStudentId())),
                    new JsonPairKeyValue("programmeAcr",new JsonText(student.getProgrammeAcr()))
            );
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
