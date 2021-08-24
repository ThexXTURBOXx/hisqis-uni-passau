package de.femtopedia.studip.hisqis.parser;

import lombok.NoArgsConstructor;
import org.jsoup.select.Elements;

@NoArgsConstructor
public class GraduationParser {

    public String parse(Elements header) {
        return header.text();
    }

}
