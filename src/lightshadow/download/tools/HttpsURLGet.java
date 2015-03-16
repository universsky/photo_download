/**
 * 
 */
package lightshadow.download.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * @author Jack Chen
 *
 */

public class HttpsURLGet {

	private String url = "https://500px.com/popular?categories=Macro";

	private myX509TrustManager xtm = new myX509TrustManager();

	private myHostnameVerifier hnv = new myHostnameVerifier();

	public HttpsURLGet() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS"); // 或SSL
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HttpsURLGet httpsTest = new HttpsURLGet();
		httpsTest.run();
	}

	/**
	 * 
	 */
	public void run() {
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length", "1024");
			urlCon.setUseCaches(false);
			urlCon.setDoInput(true);
			urlCon.getOutputStream().write("request content".getBytes("utf-8"));
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			// 增加自己的代码
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ************************************************* //

}

/**
 * 重写三个方法
 * 
 * @author Jack Chen
 *
 */
class myX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		System.out.println("cert: " + chain[0].toString() + ", authType: "
				+ authType);
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

/**
 * 重写一个方法
 * 
 * @author Jack Chen
 *
 */
class myHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		System.out.println("Warning: URL Host: " + hostname + " vs. "
				+ session.getPeerHost());
		return true;
	}
}
