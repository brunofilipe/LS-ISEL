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

public class SingleProgrammeStructureViewJson implements Writable{
    private ProgrammeStructure programmeStructure;

    public SingleProgrammeStructureViewJson(IEntity programmeStructure){
        this.programmeStructure = (ProgrammeStructure)programmeStructure;
    }
    @Override
    public void writeTo(Writer w) throws IOException {
        JsonObj obj = new JsonObj(
                new JsonPairKeyValue("courseAcr", new JsonText(programmeStructure.getCourseAcr())),
                new JsonPairKeyValue("programmeAcr", new JsonText(programmeStructure.getProgrammeAcr())),
                new JsonPairKeyValue("courseAvailabilitySemester", new JsonText(programmeStructure.getCourseAvailabilitySemester())),
                new JsonPairKeyValue("hasMandatory", new JsonText(programmeStructure.getHasMandatory()))
        );
        obj.writeTo(w);
    }
}
