package com.szepang.htmlviews;

/**
 * Created by Sze on 28/08/2017.
 */
public class HtmlBuilder {

    public static String getSimpleTableCard(String title, String desc){
        String link = "#";
        String html = "<div class=\"row\">\n" +
                "        <div class=\"col s12 m6\">\n" +
                "          <div class=\"card blue-grey darken-1\">\n" +
                "            <div class=\"card-content white-text\">\n" +
                "              <span class=\"card-title\">" + title + "</span>\n" +
                "              <p>"+desc+"</p>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>";
        return html;
    }
}
