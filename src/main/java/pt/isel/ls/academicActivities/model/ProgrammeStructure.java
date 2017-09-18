package pt.isel.ls.academicActivities.model;

public class ProgrammeStructure implements IEntity {
    private String courseAcr;
    private String programmeAcr;
    private int courseAvailabilitySemester;
    private boolean hasMandatory;

    public ProgrammeStructure(String courseAcr, String programmeAcr, int courseAvailabilitySemester, boolean hasMandatory) {
        this.courseAcr = courseAcr;
        this.programmeAcr = programmeAcr;
        this.courseAvailabilitySemester = courseAvailabilitySemester;
        this.hasMandatory = hasMandatory;
    }

    public String getCourseAcr() {
        return courseAcr;
    }

    public void setCourseAcr(String courseAcr) {
        this.courseAcr = courseAcr;
    }

    public String getProgrammeAcr() {
        return programmeAcr;
    }

    public void setProgrammeAcr(String programmeAcr) {
        this.programmeAcr = programmeAcr;
    }

    public int getCourseAvailabilitySemester() {
        return courseAvailabilitySemester;
    }

    public void setCourseAvailabilitySemester(int courseAvailabilitySemester) {
        this.courseAvailabilitySemester = courseAvailabilitySemester;
    }

    public boolean getHasMandatory() {
        return hasMandatory;
    }

    public void setHasMandatory(boolean hasMandatory) {
        this.hasMandatory = hasMandatory;
    }

    @Override
    public boolean equals(Object obj) {
        ProgrammeStructure ps = (ProgrammeStructure) obj;
        return this.courseAcr.equals(ps.courseAcr) &&
                this.programmeAcr.equals(ps.programmeAcr) &&
                this.courseAvailabilitySemester == ps.courseAvailabilitySemester &&
                this.hasMandatory == ps.hasMandatory;
    }
}
