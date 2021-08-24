package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.HisqisClient;
import de.femtopedia.studip.hisqis.parsed.Category;
import de.femtopedia.studip.hisqis.parsed.CourseOfStudy;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oauth.signpost.exception.OAuthException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequiredArgsConstructor
public class CurseOfStudyParser {

    private final HisqisClient client;

    private final List<Category> categories = new ArrayList<>();
    private float averageGrade;
    private String graduationName;

    public CourseOfStudy parse(String courseOfStudyUrl) throws IOException, IllegalAccessException, OAuthException,
            ParseException {
        Document doc = Jsoup.parse(client.get(courseOfStudyUrl).readLine());
        Elements tables = doc.select("table");
        for (Element table : tables) {
            String summary = table.attr("summary");
            if (!summary.isEmpty()) {
                continue;
            }
            TableParser parser = new TableParser();
            categories.addAll(parser.parse(table));
            averageGrade = parser.getAverageGrade();
            graduationName = parser.getGraduation();
        }
        return new CourseOfStudy(graduationName, averageGrade, categories);
    }

}
