package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.parsed.Category;
import java.util.ArrayList;
import lombok.NoArgsConstructor;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@NoArgsConstructor
public class CategoryParser {

	public Category parse(Elements columns) {
		String[] cells = new String[9];
		int i = 0;
		for (Element col : columns) {
			cells[i++] = col.text();
		}
		String f = cells[2].replace(",", ".");
		return new Category(
				Integer.parseInt(cells[0]),
				cells[1],
				TextUtils.isEmpty(f) ? 0 : Float.parseFloat(f),
				Integer.parseInt(cells[4].replace(",0", "")),
				new ArrayList<>());
	}

}
