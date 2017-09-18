package pt.isel.ls.academicActivities.view.model.programme;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class ProgrammesView implements Writable {
    private List<Programme> programmes;

    public ProgrammesView(List<IEntity> programmes) {
        this.programmes = programmes
                .stream()
                .map(entity -> (Programme)entity)
                .collect(Collectors.toList());
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        programmes.forEach(programme -> {
            view.append(String.format(" %s: %s\n", "Programme name", programme.getProgrammeName()));
            view.append(String.format(" %s: %s\n", "Programme acr", programme.getProgrammeAcr()));
            view.append(String.format(" %s: %d\n", "Programme semesters", programme.getProgrammeSemesters()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
