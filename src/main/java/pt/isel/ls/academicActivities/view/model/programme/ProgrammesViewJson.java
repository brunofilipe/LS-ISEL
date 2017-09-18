package pt.isel.ls.academicActivities.view.model.programme;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class ProgrammesViewJson implements Writable {
    private List<Programme> programmes;

    public ProgrammesViewJson(List<IEntity> programmes){
        this.programmes = programmes
                .stream()
                .map(entity -> (Programme)entity)
                .collect(Collectors.toList());    }

    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[] objs = new JsonObj[programmes.size()];
        int i = 0;
        for (Programme pg : programmes) {
            JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("programmeName",new JsonText(pg.getProgrammeName())),
                    new JsonPairKeyValue("programmeAcr",new JsonText(pg.getProgrammeAcr())),
                    new JsonPairKeyValue("programmeSemesters",new JsonText(pg.getProgrammeSemesters()))
            );
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
