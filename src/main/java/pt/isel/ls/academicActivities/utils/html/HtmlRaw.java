package pt.isel.ls.academicActivities.utils.html;

import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class HtmlRaw implements Writable{

    public final String _text;

    public HtmlRaw(String text) {
        _text = text;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        if(_text==null)
            w.write("");
        else w.write("\t"+_text + "\n");
    }
}
