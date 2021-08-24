package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.parsed.Account;
import java.util.ArrayList;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountParser {

    public Account parse(String[] cells) {
        return new Account(
                Integer.parseInt(cells[0]),
                cells[1],
                cells[2],
                cells[4],
                Integer.parseInt(cells[5].replace(",0", "")),
                new ArrayList<>());
    }

}
