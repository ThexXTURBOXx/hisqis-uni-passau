package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.HisqisAPI;
import de.femtopedia.studip.hisqis.parsed.CourseOfStudy;
import de.femtopedia.studip.hisqis.parsed.Student;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oauth.signpost.exception.OAuthException;

@RequiredArgsConstructor
public class StudentParser {

    private final HisqisAPI api;

    public Student parse() throws IOException, IllegalAccessException, OAuthException, ParseException {
        List<CourseOfStudy> courses = new ArrayList<>();
        for (String course : api.getCourses()) {
            courses.add(new CurseOfStudyParser(api.getHisqisClient()).parse(course));
        }
        return new Student(courses);
    }

}
