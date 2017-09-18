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
import java.util.stream.Collectors;

public class SingleCourseViewJson implements Writable {
    private Course course;

    public SingleCourseViewJson(IEntity course) {
        this.course = (Course)course;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj obj = new JsonObj(
                new JsonPairKeyValue("courseName", new JsonText(course.getCourseName())),
                new JsonPairKeyValue("courseAcr", new JsonText(course.getCourseAcr())),
                new JsonPairKeyValue("coordinatorId", new JsonText(course.getCoordinatorId()))
        );
        obj.writeTo(w);
    }
}
