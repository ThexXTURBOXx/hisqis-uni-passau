package de.femtopedia.studip.hisqis.parser;

import lombok.NoArgsConstructor;
import org.jsoup.select.Elements;

@NoArgsConstructor
public class AverageGradeParser {

	public float parse(Elements columns) {
		return Float.parseFloat(columns.get(2).text()
				.replace(",", "."));
	}

}
