package pt.isel.ls.academicActivities.view.model.programme;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class SingleProgrammeView implements Writable {
    private Programme programme;

    public SingleProgrammeView(IEntity programme) {
        this.programme = (Programme)programme;
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Programme name", programme.getProgrammeName()));
        view.append(String.format(" %s: %s\n", "Programme acr", programme.getProgrammeAcr()));
        view.append(String.format(" %s: %d\n", "Programme semesters", programme.getProgrammeSemesters()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
