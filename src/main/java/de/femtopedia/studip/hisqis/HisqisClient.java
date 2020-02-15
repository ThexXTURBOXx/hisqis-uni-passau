package de.femtopedia.studip.hisqis;

import de.femtopedia.studip.hisqis.parser.AsiParser;
import de.femtopedia.studip.shib.CustomAccessClient;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import de.femtopedia.studip.shib.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import oauth.signpost.exception.OAuthException;
import okhttp3.Request;

public class HisqisClient extends CustomAccessClient {

	@Getter
	private String asi;

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
		CustomAccessHttpResponse response = null;
		try {
			List<Pair<String, String>> nvpms = new ArrayList<>();
			nvpms.add(new Pair<>("asdf", username));
			nvpms.add(new Pair<>("fdsa", password));
			response = post("https://qisserver.uni-passau.de/qisserver/rds?state=user&type=1&category=auth.login&startpage=portal.vm&breadCrumbSource=portal", nvpms);
			response.close();
			this.asi = grabAsi();
		} catch (OAuthException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
	}

	/**
	 * Performs a HTTP GET Request.
	 *
	 * @param url        The URL to get
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse get(String url, String[] headerKeys, String[] headerVals) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url)
				.get();
		return executeRequest(requestBuilder, headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP POST Request.
	 *
	 * @param url        The URL to post
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse post(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		return executeRequest(
				requestBuilder.post(getFormBody(nvps)),
				headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP PUT Request.
	 *
	 * @param url        The URL to put
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse put(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		return executeRequest(
				requestBuilder.put(getFormBody(nvps)),
				headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP DELETE Request.
	 *
	 * @param url        The URL to delete
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse delete(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		return executeRequest(
				requestBuilder.delete(getFormBody(nvps)),
				headerKeys, headerVals);
	}

	@Override
	public boolean isErrorCode(int statusCode) {
		return statusCode != 200
				&& statusCode != 302;
	}

	private String grabAsi() throws IOException, IllegalArgumentException, IllegalAccessException, OAuthException {
		return new AsiParser(this).parse();
	}

	@Override
	public boolean isSessionValid() throws IOException, OAuthException {
		return this.asi != null && !this.asi.equals("");
	}

}
