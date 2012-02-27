package com.wp.mballem.soup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Marcio Ballem
 * Date: 25/02/12
 * Time: 23:17
 * http://mballem.wordpress.com/
 */
public class HtmlParserRevistas {
    private Document document;
    
    private String href;
    private String src;
    private String p;

    public String getHref() {
        return href;
    }

    public String getSrc() {
        return src;
    }

    public String getP() {
        return p;
    }

    public HtmlParserRevistas(Document document) {
        this.document = document;
    }

    public HtmlParserRevistas(String href, String src, String p) {
        this.href = href;
        this.src = src;
        this.p = p;
    }

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://mballem.wordpress.com/revistas/").get();
            HtmlParserRevistas parserRevistas = new HtmlParserRevistas(document);
            parserRevistas.getRevistas();
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    private void getRevistas() {
        Elements elements = document.getElementsByClass("wp-caption");
        for (Element element : elements) {
            String href = element.getElementsByTag("a").attr("href");
            String src = element.getElementsByTag("img").attr("src");
            String p = element.getElementsByTag("p").text();
            System.out.println(href + " - " + src + " - " + p);
        }
    }

    public List<HtmlParserRevistas> getParserRevistas() {
        HtmlParserRevistas parser;
        List<HtmlParserRevistas> parserList = new ArrayList<HtmlParserRevistas>();

        Elements elements = document.getElementsByClass("wp-caption");
        for (Element element : elements) {
            String href = element.getElementsByTag("a").attr("href");
            String src = element.getElementsByTag("img").attr("src");
            String p = element.getElementsByTag("p").text();
            parser = new HtmlParserRevistas(href,src,p);
            parserList.add(parser);
        }
        return parserList;
    }
}
