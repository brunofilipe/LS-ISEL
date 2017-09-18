package pt.isel.ls.academicActivities.view.model.programmeStructure;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class ProgrammeStructuresView implements Writable {
    private List<ProgrammeStructure> programmeStructures;

    public ProgrammeStructuresView(List<IEntity> programmeStructures){
        this.programmeStructures = programmeStructures
                .stream()
                .map(entity -> (ProgrammeStructure)entity)
                .collect(Collectors.toList());    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        programmeStructures.forEach(programmeStructure -> {
            view.append(String.format(" %s: %s\n", "Course Acr", programmeStructure.getCourseAcr()));
            view.append(String.format(" %s: %s\n", "Programme Acr", programmeStructure.getProgrammeAcr()));
            view.append(String.format(" %s: %s\n", "Course Availability Semester", programmeStructure.getCourseAvailabilitySemester()));
            view.append(String.format(" %s: %s\n", "Has Mandatory", programmeStructure.getHasMandatory()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
