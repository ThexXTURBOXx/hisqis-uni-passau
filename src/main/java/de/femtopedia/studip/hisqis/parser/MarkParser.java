package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.parsed.Mark;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MarkParser {

	public Mark parse(String[] cells) throws ParseException {
		return new Mark(
				Integer.parseInt(cells[0]),
				cells[1],
				cells[2],
				Float.parseFloat(cells[3].replace(",", ".")),
				cells[4],
				Integer.parseInt(cells[5].replace(",0", "")),
				cells[6],
				Integer.parseInt(cells[7]),
				DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMANY).parse(cells[8]));
	}

}
