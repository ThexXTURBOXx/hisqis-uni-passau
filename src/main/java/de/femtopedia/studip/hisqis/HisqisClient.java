package de.femtopedia.studip.hisqis;

import de.femtopedia.studip.hisqis.parser.AsiParser;
import de.femtopedia.studip.shib.CustomAccessClient;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import lombok.Getter;
import oauth.signpost.exception.OAuthException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

public class HisqisClient extends CustomAccessClient {

	@Getter
	private String asi;

	public HisqisClient() {
		this(null, "");
	}

	public HisqisClient(InputStream keyStore, String password) {
		super(keyStore, password);
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
		CustomAccessHttpResponse response = null;
		try {
			List<NameValuePair> nvpms = new ArrayList<>();
			nvpms.add(new BasicNameValuePair("asdf", username));
			nvpms.add(new BasicNameValuePair("fdsa", password));
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

	@Override
	public CustomAccessHttpResponse get(String url, String[] headerKeys, String[] headerVals) throws IOException, IllegalArgumentException, IllegalAccessException {
		HttpGet httpGet = new HttpGet(url);
		return this.executeRequest(httpGet, headerKeys, headerVals);
	}

	@Override
	public CustomAccessHttpResponse post(String url, String[] headerKeys, String[] headerVals, @Nullable List<NameValuePair> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		HttpPost httpPost = new HttpPost(url);
		if (nvps != null) {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		}
		return executeRequest(httpPost, headerKeys, headerVals);
	}

	@Override
	public CustomAccessHttpResponse put(String url, String[] headerKeys, String[] headerVals) throws IOException, IllegalArgumentException, IllegalAccessException {
		HttpPut httpPut = new HttpPut(url);
		return executeRequest(httpPut, headerKeys, headerVals);
	}

	@Override
	public CustomAccessHttpResponse delete(String url, String[] headerKeys, String[] headerVals) throws IOException, IllegalArgumentException, IllegalAccessException {
		HttpDelete httpDelete = new HttpDelete(url);
		return executeRequest(httpDelete, headerKeys, headerVals);
	}

	@Override
	public boolean isErrorCode(int statusCode) {
		return false;
	}

	private String grabAsi() throws IOException, IllegalArgumentException, IllegalAccessException, OAuthException {
		return new AsiParser(this).parse();
	}

	/**
	 * Shuts the client down.
	 */
	public void shutdown() {
		this.client.shutdown();
	}

}
