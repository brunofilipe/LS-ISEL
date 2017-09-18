package pt.isel.ls.academicActivities.utils.html;

import pt.isel.ls.academicActivities.utils.Writable;


public class HtmlPage extends Html{
    //html page without css
    public HtmlPage(String title, Writable... c) {
       super(new HtmlRaw("<!DOCTYPE html>"),
               new HtmlElem("html",
                new HtmlElem("head", new HtmlElem("title", new HtmlText(title))),
                new HtmlElem("body", c)

        ));
    }
    //html page with css
    public HtmlPage(String title,String link, Writable... c){
        super(new HtmlRaw("<!DOCTYPE html>"),
                new HtmlElem("html",
                        new HtmlElem("head",
                                new HtmlElem("title", new HtmlText(title)),
                                new HtmlElem("meta").withAttr("charset", "UTF-8"),
                                Html.css(link)),
                        new HtmlElem("body", c)

                ));
    }
}
