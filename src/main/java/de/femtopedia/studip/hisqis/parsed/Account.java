package de.femtopedia.studip.hisqis.parsed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = "marks")
public class Account {

    private final int number;
    private final String subject;
    private final String semester;
    private final String state;
    private final int ects;
    private final List<Mark> marks;

}
