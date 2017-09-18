package pt.isel.ls.academicActivities.view.model.programme;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;

public class SingleProgrammeViewJson implements Writable {
    private Programme programme;

    public SingleProgrammeViewJson(IEntity programme){
        this.programme = (Programme)programme;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj obj = new JsonObj(
                new JsonPairKeyValue("programmeName",new JsonText(programme.getProgrammeName())),
                new JsonPairKeyValue("programmeAcr",new JsonText(programme.getProgrammeAcr())),
                new JsonPairKeyValue("programmeSemesters",new JsonText(programme.getProgrammeSemesters()))
        );
        obj.writeTo(w);
    }
}
