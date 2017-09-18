package pt.isel.ls.academicActivities.view.model.programmeStructure;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.json.JsonArray;
import pt.isel.ls.academicActivities.utils.json.JsonObj;
import pt.isel.ls.academicActivities.utils.json.JsonPairKeyValue;
import pt.isel.ls.academicActivities.utils.json.JsonText;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class ProgrammeStructuresViewJson implements Writable {
    private List<ProgrammeStructure> programmeStructures;

    public ProgrammeStructuresViewJson(List<IEntity> programmeStructures){
        this.programmeStructures = programmeStructures
                .stream()
                .map(entity -> (ProgrammeStructure)entity)
                .collect(Collectors.toList());    }
    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj[] objs = new JsonObj[programmeStructures.size()];
        int i = 0;
        for (ProgrammeStructure ps : programmeStructures) {
            JsonObj obj = new JsonObj(
                    new JsonPairKeyValue("courseAcr", new JsonText(ps.getCourseAcr())),
                    new JsonPairKeyValue("programmeAcr", new JsonText(ps.getProgrammeAcr())),
                    new JsonPairKeyValue("courseAvailabilitySemester", new JsonText(ps.getCourseAvailabilitySemester())),
                    new JsonPairKeyValue("hasMandatory", new JsonText(ps.getHasMandatory()))
            );
            objs[i++] = obj;
        }
        JsonArray array = new JsonArray(objs);
        array.writeTo(w);
    }
}
