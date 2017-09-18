package pt.isel.ls.academicActivities.utils;

import java.io.IOException;
import java.io.Writer;

public final class CompositeWritable implements Writable {

    private final Writable[] _content;

    public CompositeWritable(Writable... cs){
        _content = cs;
    }
    @Override
    public void writeTo(Writer w) throws IOException {
        for(Writable c : _content) {
            c.writeTo(w);
        }
    }
}