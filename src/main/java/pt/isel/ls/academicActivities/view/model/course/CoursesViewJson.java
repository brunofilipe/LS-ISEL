package pt.isel.ls.academicActivities.view.model.course;

import pt.isel.ls.academicActivities.model.Course;
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

public class CoursesViewJson implements Writable {
    private List<Course> courses;

    public CoursesViewJson(List<IEntity> courses) {
        this.courses = courses
                .stream()
                .map(entity -> (Course)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[] objs = new JsonObj[courses.size()];
        int i = 0;
        for (Course c : courses) {
            JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("courseName", new JsonText(c.getCourseName())),
                    new JsonPairKeyValue("courseAcr", new JsonText(c.getCourseAcr())),
                    new JsonPairKeyValue("coordinatorId", new JsonText(c.getCoordinatorId()))
            );
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
