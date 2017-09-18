package pt.isel.ls.academicActivities.utils.html;



import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlElem implements Writable {

    private final String _name;
    private final boolean _endTag;
    private final List<Writable> _content = new ArrayList<Writable>();
    private final Map<String, String> _attrs = new HashMap<String, String>();

    public HtmlElem(String name, Writable... cs) {
        _endTag = true;
        _name = name;
        for (Writable c : cs) {
            _content.add(c);
        }
    }

    public HtmlElem(String name, boolean endTag, Writable... cs) {
        _name = name;
        for (Writable c : cs) {
            _content.add(c);
        }
        _endTag = endTag;
    }

    public final HtmlElem withAttr(String name, String value) {
        _attrs.put(name, value);
        return this;
    }

    public final HtmlElem withContent(Writable w) {
        _content.add(w);
        return this;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(String.format("\n<%s", _name));
        for (Map.Entry<String, String> entry : _attrs.entrySet()) {
            w.write(String.format(" %s='%s'", entry.getKey(), entry.getValue()));
        }
        w.write(">\n");
        for (Writable c : _content) {
            c.writeTo(w);
        }
        if (_endTag)
            w.write(String.format("</%s>\n", _name));
    }
}
