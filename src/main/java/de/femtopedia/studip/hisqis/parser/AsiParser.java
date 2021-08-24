package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.HisqisClient;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import oauth.signpost.exception.OAuthException;

@RequiredArgsConstructor
public class AsiParser {

    public static final Pattern ASI_PATTERN = Pattern.compile("asi=(.+?)\".*");
    private static final String ASI_URL = "https://qisserver.uni-passau"
            + ".de/qisserver/rds?state=change&type=1&moduleParameter=studyPOSMenu&nextdir=change&next=menu"
            + ".vm&subdir=applications&xml=menu&purge=y&navigationPosition=functions%2CstudyPOSMenu&breadcrumb"
            + "=studyPOSMenu&topitem=functions&subitem=studyPOSMenu";

    private final HisqisClient client;

    public String parse() throws IOException, IllegalArgumentException, IllegalAccessException, OAuthException {
        try (CustomAccessHttpResponse resp = client.get(ASI_URL)) {
            Matcher asiMatcher = ASI_PATTERN.matcher(resp.readLine());
            if (!asiMatcher.find()) {
                return null;
            }
            return asiMatcher.group(1);
        }
    }

}
