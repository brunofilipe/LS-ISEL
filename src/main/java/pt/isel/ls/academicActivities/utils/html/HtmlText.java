package pt.isel.ls.academicActivities.utils.html;

import org.apache.commons.lang.StringEscapeUtils;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class HtmlText implements Writable {

    public final String _text;
    
    public HtmlText(String text) {
        _text = text;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        if(_text==null)
            w.write("");
        else
            w.write(StringEscapeUtils.escapeHtml("\t" + _text + "\n"));
    }
}
