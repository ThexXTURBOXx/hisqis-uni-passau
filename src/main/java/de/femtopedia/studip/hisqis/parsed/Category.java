package de.femtopedia.studip.hisqis.parsed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = "accounts")
public class Category {

    private final int number;
    private final String subject;
    private final float grade;
    private final int ects;
    private final List<Account> accounts;

}
