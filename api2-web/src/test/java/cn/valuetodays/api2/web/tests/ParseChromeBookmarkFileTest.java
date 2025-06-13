package cn.valuetodays.api2.web.tests;

import java.io.IOException;
import java.io.StringWriter;

import cn.vt.test.TestBase;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

/**
 * @author lei.liu
 * @since 2023-01-17
 */
class ParseChromeBookmarkFileTest extends TestBase {

    @Test
    void parseChromeBookmarkFile() throws IOException {
        String chromeBookmarkFile = "/data/chrome_bookmarks_8_17.html";
        String string = getFileAsString(chromeBookmarkFile);
        Document document = Jsoup.parse(string);
        Elements dtEles = document.select("dt");
        Element firstDtEle = dtEles.get(0);
        Element firstChildInDt = firstDtEle.child(0);
        StringWriter stringWriter = new StringWriter(2048);
        parseFolderAndAElement(firstChildInDt, stringWriter, 0);
        /*String tagName = firstChildInDt.tagName();
        if (StringUtils.equalsIgnoreCase("h3", tagName)) {
            String text = firstChildInDt.text(); // Bookmarks bar
            getLog().info(": {}", text);
            Elements siblingElements = firstChildInDt.siblingElements();
            Element dlEle = siblingElements.get(0);
            Elements childrenInDl = dlEle.children();
            for (Element childInDl : childrenInDl) {
                String childInDlTagName = childInDl.tagName();
                if (StringUtils.equalsIgnoreCase("dt", childInDlTagName)) {
                    Element aOrH3Ele = childInDl.child(0);
                    parseFolderAndAElement(aOrH3Ele);
                }
            }
        }*/
        getLog().info("end");
        getLog().info("content: \n{}", stringWriter);
    }

    private void parseFolderAndAElement(Element aOrH3Ele, StringWriter stringWriter, int indent) {
        String aOrH3EleTagName = aOrH3Ele.tagName();
        if (StringUtils.equalsIgnoreCase("a", aOrH3EleTagName)) {
            String iconStringInAEle = aOrH3Ele.attr("icon");
            String hrefInAEle = aOrH3Ele.attr("href");
            String textInAEle = aOrH3Ele.text();
//            getLog().info("    {}, {}", textInAEle, hrefInAEle);
            stringWriter.append(StringUtils.repeat("\t", indent)).append(textInAEle).append(", ").append(hrefInAEle).append("\n");
        } else if (StringUtils.equalsIgnoreCase("h3", aOrH3EleTagName)) {
            String folderName = aOrH3Ele.text();
            Element childrenInFolder = aOrH3Ele.parent().child(1);
//            getLog().info("{}", folderName);
            stringWriter.append(StringUtils.repeat("\t", indent)).append(folderName).append("\n");
            String childTagNameInFolder = childrenInFolder.tagName();
            if (StringUtils.equalsIgnoreCase("dl", childTagNameInFolder)) {
                Elements childrenInDl = childrenInFolder.children();
                for (Element childInDl : childrenInDl) {
                    String childInDlTagName = childInDl.tagName();
                    if (StringUtils.equalsIgnoreCase("dt", childInDlTagName)) {
                        Element innerAOrH3Ele = childInDl.child(0);
                        parseFolderAndAElement(innerAOrH3Ele, stringWriter, indent + 1);
                    }
                }
            }
        }

    }
}
