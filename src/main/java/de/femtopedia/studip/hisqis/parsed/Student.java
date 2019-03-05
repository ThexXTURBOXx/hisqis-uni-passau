package de.femtopedia.studip.hisqis.parsed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString(exclude = "courses")
@Getter
@AllArgsConstructor
public class Student {

	private final List<CourseOfStudy> courses;

}
