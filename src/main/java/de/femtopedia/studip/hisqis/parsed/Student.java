package de.femtopedia.studip.hisqis.parsed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(exclude = "courses")
public class Student {

    private final List<CourseOfStudy> courses;

}
