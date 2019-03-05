package de.femtopedia.studip.hisqis;

import de.femtopedia.studip.hisqis.parsed.Student;
import de.femtopedia.studip.hisqis.parser.CourseUrlParser;
import de.femtopedia.studip.hisqis.parser.StudentParser;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import lombok.Getter;
import oauth.signpost.exception.OAuthException;

/**
 * A simple-to-use API class to access grades in the HISQIS Portal.
 */
public class HisqisAPI {

	public static String BASE_URL = "https://qisserver.uni-passau.de/qisserver/";

	@Getter
	private HisqisClient hisqisClient;

	/**
	 * Initializes a new default {@link HisqisAPI} instance.
	 */
	public HisqisAPI() {
		this(null, "");
	}

	/**
	 * Initializes a new {@link HisqisAPI} instance with a custom KeyStore.
	 *
	 * @param keyStore A custom KeyStore as {@link InputStream} to set or null
	 * @param password The KeyStore's password
	 */
	public HisqisAPI(InputStream keyStore, String password) {
		this.hisqisClient = new HisqisClient(keyStore, password);
	}

	/**
	 * Tries to authenticate with the HISQIS Server and save the required cookies.
	 *
	 * @param username The username of the account
	 * @param password The password of the account
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the user credentials are not correct
	 * @throws IllegalStateException    if cookies don't match the server
	 */
	public void authenticate(String username, String password)
			throws IOException, IllegalArgumentException, IllegalAccessException, IllegalStateException {
		this.hisqisClient.authenticate(username, password);
	}

	/**
	 * Performs a HTTP GET Request.
	 *
	 * @param url The URL to get
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 * @throws OAuthException           if any OAuth errors occur
	 */
	public CustomAccessHttpResponse get(String url)
			throws IOException, IllegalArgumentException, IllegalAccessException, OAuthException {
		if (url.startsWith("http"))
			return this.hisqisClient.get(url);
		return this.hisqisClient.get(BASE_URL + url);
	}

	public String getAsi() {
		return this.hisqisClient.getAsi();
	}

	/**
	 * Shuts down the {@link HisqisClient}.
	 */
	public void shutdown() {
		this.hisqisClient.shutdown();
	}

	public List<String> getCourses() throws IllegalAccessException, IOException, OAuthException {
		return new CourseUrlParser(this.getHisqisClient()).parse();
	}

	public Student getStudent() throws IOException, IllegalAccessException, OAuthException, ParseException {
		return new StudentParser(this).parse();
	}

}
