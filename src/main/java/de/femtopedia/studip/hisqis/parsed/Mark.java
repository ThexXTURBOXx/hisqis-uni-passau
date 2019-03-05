package de.femtopedia.studip.hisqis.parsed;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Mark {

	private final int number;
	private final String subject;
	private final String semester;
	private final float grade;
	private final String state;
	private final int ects;
	private final String note;
	private final int attempt;
	private final Date date;

}
