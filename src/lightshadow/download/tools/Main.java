/**
 * 
 */
package lightshadow.download.tools;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

/**
 * @author jack
 *
 */
public class Main {

	static String fileURL = "D:/Images/";
	// static String tagUrls[] = {
	// "https://500px.com/popular?categories=Macro&page=1" };
	// static String AllCatUrl = "https://500px.com/popular?page=";

	static String recentUrl = "http://tuchong.com/photos/recent/?page=";

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss")
				.format(new Date());

		for (int i = 1; i <= 8; i++) {
			ImgUrlMatch.getImgUrls(recentUrl + i, timestamp);
		}

	}

}
