package com.wp.mballem.soup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Marcio Ballem
 * Date: 25/02/12
 * Time: 22:52
 * http://mballem.wordpress.com/
 */
public class HtmlParserFooter {
    private Document document;

    public HtmlParserFooter(Document document) {
        this.document = document;
    }

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://mballem.wordpress.com/").get();
            HtmlParserFooter parserFooter = new HtmlParserFooter(document);
            parserFooter.getFooter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFooter() {
        Elements elements = document.getElementsByClass("the-content");
        
        getFooterPaginas(elements.eq(0));
        //getFooterArtigos(elements.eq(1));
        //getFooterEmail(elements.eq(2));
    }

    private void getFooterEmail(Elements elements) {
        Element element = elements.get(0);
        String h4 = element.getElementsByClass("title").get(0).text();
        Elements children = element.getElementsByTag("form");
        for (Element p : children) {
            String desc = p.getElementsByTag("p").text();
            System.out.println(h4 + "\n" + desc);
        }
    }

    private void getFooterArtigos(Elements elements) {
        Element element = elements.get(0);
        String h4 = element.getElementsByClass("title").text();
        Elements children = element.getElementsByTag("li");
        for (Element li : children) {
            String desc = li.getElementsByTag("a").text();
            String href = li.getElementsByTag("a").attr("href");
            System.out.println(h4 + " " + desc + " " + href);
        }
    }

    private void getFooterPaginas(Elements elements) {
        for (Element element : elements) {
            String h4 = element.getElementsByClass("title").text();
            Elements children = element.getElementsByTag("li");
            for (Element li : children) {
                String desc = li.getElementsByTag("a").text();
                String href = li.getElementsByTag("a").attr("href");
                System.out.println(h4 + " " + desc + " " + href);
            }
        }
    }


}
