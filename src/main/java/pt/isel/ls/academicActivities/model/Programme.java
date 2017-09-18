package pt.isel.ls.academicActivities.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Programme implements IEntity {
    private String programmeName;
    private String programmeAcr;
    private int programmeSemesters;
    private List<ProgrammeStructure> courses;

    public Programme(String programmeName, String programmeAcr, int programmeSemesters) {
        this.programmeName = programmeName;
        this.programmeAcr = programmeAcr;
        this.programmeSemesters = programmeSemesters;
        this.courses = new LinkedList<>();
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public String getProgrammeAcr() {
        return programmeAcr;
    }

    public void setProgrammeAcr(String programmeAcr) {
        this.programmeAcr = programmeAcr;
    }

    public int getProgrammeSemesters() {
        return programmeSemesters;
    }

    public void setProgrammeSemesters(int programmeSemesters) {
        this.programmeSemesters = programmeSemesters;
    }

    public List<ProgrammeStructure> getCourses() {
        return courses;
    }

    public void setCourses(List<IEntity> courses) {
        this.courses = courses
                .stream()
                .map(entity -> (ProgrammeStructure) entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        Programme pg = (Programme) obj;
        return this.programmeName.equals(pg.programmeName) &&
                this.programmeAcr.equals(pg.programmeAcr) &&
                this.programmeSemesters == pg.programmeSemesters;
    }
}
