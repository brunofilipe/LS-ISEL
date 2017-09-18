package pt.isel.ls.academicActivities.utils.html;


import pt.isel.ls.academicActivities.utils.CompositeWritable;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class Html implements Writable {

    private Writable _content;

    protected Html(Writable... cs) {
        _content = new CompositeWritable(cs);
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        _content.writeTo(w);
    }

    public static Writable br() { return new HtmlRaw("<br>");}

    public static Writable text(String s) { return new HtmlText(s);}

    public static Writable h1(Writable... c) { return new HtmlElem("h1", c);}

    public static Writable h2(Writable... c) { return new HtmlElem("h2", c);}

    public static Writable h3(Writable... c) { return new HtmlElem("h3", c);}

    public static Writable p(Writable... c) { return new HtmlElem("p", c);}

    public static Writable form(String method, String url, Writable... c) {
        return new HtmlElem("form", c)
                .withAttr("method", method)
                .withAttr("action", url);
    }

    public static Writable formWithCss(String method, String url, Writable... c) {
        return new HtmlElem("form", c)
                .withAttr("class","form-inline")
                .withAttr("method", method)
                .withAttr("action", url);
    }

    public static Writable label(String to, String text) {
        return new HtmlElem("label", new HtmlText(text))
                .withAttr("for", to);
    }

    public static Writable textInput(String name) {
        return new HtmlElem("input")
                .withAttr("type", "text")
                .withAttr("name", name);
    }

    public static Writable textInputWithCss(String name) {
        return new HtmlElem("input")
                .withAttr("type", "text")
                .withAttr("name", name)
                .withAttr("class","form-control");
    }

    public static Writable submitInput(String value) {
        return new HtmlElem("input")
                .withAttr("value", value)
                .withAttr("type", "submit");
    }

    public static Writable submitInputWithCss(String value) {
        return new HtmlElem("input")
                .withAttr("value", value)
                .withAttr("type", "submit")
                .withAttr("class","form-control");
    }


    public static Writable input(String name, String type) {
        return new HtmlElem("input")
                .withAttr("type", type)
                .withAttr("name", name);
    }

    public static Writable inputWithCss(String name, String type) {
        return new HtmlElem("input")
                .withAttr("type", type)
                .withAttr("name", name)
                .withAttr("class","form-control");
    }

    public static Writable radioInput(String name, String value) {
        return new HtmlElem("input")
                .withAttr("type", "radio")
                .withAttr("name", name)
                .withAttr("value", value);
    }

    public static Writable ul(Writable... c) {
        return new HtmlElem("ul", c);
    }

    public static Writable li(Writable... c) {
        return new HtmlElem("li", c);
    }

    public static Writable a(String href, String t) {
        return new HtmlElem("a", text(t))
                .withAttr("href", href);
    }

    public static HtmlElem table(Writable columns, Writable rows) {
        return new HtmlElem("table", columns, rows);

    }

    public static Writable thead(Writable... c) {
        return new HtmlElem("thead", c);
    }

    public static Writable tbody(Writable... c) {
        return new HtmlElem("tbody", c);
    }

    public static Writable tr(Writable... c) {
        return new HtmlElem("tr", c);
    }

    public static Writable th(Writable... c) {
        return new HtmlElem("th", c);
    }

    public static Writable td(String t) {
        return new HtmlElem("td", text(t));
    }

    public static Writable td(Writable t) {
        return new HtmlElem("td", t);
    }

    public static Writable css(String link) {
        return new HtmlElem("link", false).withAttr("rel", "stylesheet").withAttr("href", link);
    }

    public static HtmlElem div(Writable... c) {
        return new HtmlElem("div", c);
    }

    public static HtmlElem fieldset(Writable... c) {
        return new HtmlElem("fieldset", c);
    }

    public static HtmlElem legend(Writable... c) {
        return new HtmlElem("legend", c);
    }
}
