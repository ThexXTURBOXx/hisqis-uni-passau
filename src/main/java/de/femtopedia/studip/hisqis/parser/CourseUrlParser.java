package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.HisqisAPI;
import de.femtopedia.studip.hisqis.HisqisClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor
public class CourseUrlParser {

	private final HisqisClient client;

	public List<String> parse() throws IllegalAccessException, IOException, OAuthException {
		List<String> list = new ArrayList<>();
		Document doc = Jsoup.parse(client.get(HisqisAPI.BASE_URL + "rds?state=notenspiegelStudent&next=tree.vm&nextdir=qispos/notenspiegel/student&menuid=notenspiegelStudent&breadcrumb=notenspiegel&breadCrumbSource=menu&asi=" + client.getAsi()).readLine());
		for (Element content : doc.select("div")) {
			if (!content.hasClass("content"))
				continue;
			for (Element element : content.select("a")) {
				if (!OAuth.isEmpty(element.attr("title"))) {
					list.add(element.attr("href"));
				}
			}
		}
		return list;
	}

}
