package pt.isel.ls.academicActivities.engine;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.academicActivities.utils.html.*;


import java.io.*;

import static pt.isel.ls.academicActivities.utils.html.Html.*;
import static pt.isel.ls.academicActivities.utils.html.Html.text;
import static pt.isel.ls.academicActivities.utils.html.Html.th;

public class HtmlTest {

    private BufferedWriter wr ;

    @Before
    public void init() throws IOException {
        FileWriter fw = new FileWriter(new File("teste.html"));
        wr = new BufferedWriter(fw);

    }

    @Test
    public void testPageHtml() throws IOException {
      HtmlPage page = new HtmlPage("Pagina de Teste",Html.h1(Html.text("text")));
      page.writeTo(wr);
      wr.close();
    }

    @Test
    public void testPageHtmlInConsole() throws IOException {
        HtmlPage page = new HtmlPage("Pagina de Teste",Html.h1(Html.text("text")));
        StringWriter sr = new StringWriter();
        page.writeTo(sr);
        sr.close();
        System.out.println(sr);
    }

    @Test
    public void testHrefinHtml() throws IOException {
        HtmlPage page = new HtmlPage(
                "Pagina de Teste",
                Html.h1(Html.text("text")),
                Html.a("https://www.google.pt","GOOGLE"));
        page.writeTo(wr);
        wr.close();
    }

    @Test
    public void testFormHtml() throws IOException {
        HtmlPage page = new HtmlPage(
                "Pagina de Teste",
                Html.h1(Html.text("text")),
                Html.form("POST","WWW.google.pt",textInput("Name")));
        page.writeTo(wr);
        wr.close();
    }

    @Test
    public void testCssFormHtml() throws IOException{
        HtmlPage p = new HtmlPage("Pagina de Teste",
                "src\\main\\resource\\public\\stylesheets\\bootstrap.min.css",
                div(
                        Html.h1(Html.text("Testing....")),
                        Html.form("POST","WWW.google.pt",
                                div(
                                        textInputWithCss("Name")
                                ).withAttr("class","form-group"),
                                div(
                                        submitInputWithCss("submit")
                                ).withAttr("class","form-group"))
                ).withAttr("class","container"));
        p.writeTo(wr);
        wr.close();
    }
    @Test
    public void testCssTableHtml() throws IOException {
        HtmlElem tableBody = new HtmlElem("tbody");
        tableBody.withContent( tr(
                td(text(41484+"")),
                td(text("Bruno")),
                td(text("41484@alunos.isel.ipl.pt")),
                td((text("LEIC"))))
        );
        tableBody.withContent( tr(
                td(text(41893+"")),
                td(text("Joao")),
                td(text("41893@alunos.isel.ipl.pt")),
                td((text("LEIC"))))
        );
        HtmlPage p = new HtmlPage("Pagina de Teste",
                                                "src\\main\\resource\\public\\stylesheets\\bootstrap.min.css",
                                                div( Html.h1(Html.text("Testing....")),
                                                        Html.h1(Html.text("Table with Css")),
                                                        Html.table(
                                                                new HtmlElem("thead",
                                                                        tr(
                                                                                th(text("student ID")),
                                                                                th(text("student Name")),
                                                                                th(text("student Email")),
                                                                                th(text("programme Acr"))
                                                                        )),tableBody
                                                        ).withAttr("border","1")
                                                        .withAttr("class","table")
                                                ).withAttr("class","container")
                                               );
        p.writeTo(wr);
        wr.close();
    }

    @Test
    public void testTable() throws IOException {
        StringWriter sr = new StringWriter();
        String title = "Existing courses :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        tableBody.withContent(
                tr(
                        td(text("Laboratorio de Software")),
                        td(text("LS")),
                        td(text(23+""))
                )
        );
        HtmlPage page = new HtmlPage(
                title,
                h3(text(title)),
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("Course Name")),
                                        th(text("Course Acr")),
                                        th(text("Coordinator ID"))
                                )
                        ),
                        tableBody
                ).withAttr("border","1")
        );
        page.writeTo(wr);
        wr.close();
    }

    @Test
    public void testCssHtml() throws IOException {
        HtmlElem tableBody = new HtmlElem("tbody");
        tableBody.withContent( tr(
                td(text(41484+"")),
                td(text("Bruno")),
                td(text("41484@alunos.isel.ipl.pt")),
                td((text("LEIC"))))
        );
        tableBody.withContent( tr(
                td(text(41893+"")),
                td(text("Joao")),
                td(text("41893@alunos.isel.ipl.pt")),
                td((text("LEIC"))))
        );
        HtmlPage p = new HtmlPage("Pagina de Teste",
                "src\\main\\resource\\public\\stylesheets\\bootstrap.min.css",
                div( Html.h1(Html.text("Testing....")),
                        Html.h1(Html.text("Table with Css")),
                        Html.table(
                                new HtmlElem("thead",
                                        tr(
                                                th(text("student ID")),
                                                th(text("student Name")),
                                                th(text("student Email")),
                                                th(text("programme Acr"))
                                        )),tableBody
                        ).withAttr("border","1")
                                .withAttr("class","table"),
                        div(
                                textInputWithCss("Name")
                        ).withAttr("class","form-group"),
                        div(
                                submitInputWithCss("submit")
                        ).withAttr("class","form-group")
                ).withAttr("class","container")
        );
        p.writeTo(wr);
        wr.close();
    }

}
