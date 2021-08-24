package de.femtopedia.studip.hisqis.parsed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = "categories")
public class CourseOfStudy {

    private final String graduationName;
    private final float averageGrade;
    private final List<Category> categories;

}
