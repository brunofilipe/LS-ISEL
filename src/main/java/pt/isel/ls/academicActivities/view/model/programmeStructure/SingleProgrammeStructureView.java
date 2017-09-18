package pt.isel.ls.academicActivities.view.model.programmeStructure;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class SingleProgrammeStructureView implements Writable {
    private ProgrammeStructure programmeStructure;

    public SingleProgrammeStructureView(IEntity programmeStructure){
        this.programmeStructure = (ProgrammeStructure) programmeStructure;
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Course Acr", programmeStructure.getCourseAcr()));
        view.append(String.format(" %s: %s\n", "Programme Acr", programmeStructure.getProgrammeAcr()));
        view.append(String.format(" %s: %s\n", "Course Availability Semester", programmeStructure.getCourseAvailabilitySemester()));
        view.append(String.format(" %s: %s\n", "Has Mandatory", programmeStructure.getHasMandatory()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
