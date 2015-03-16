/**
 * 
 */
package lightshadow.download.tools;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

/**
 * @author jack
 *
 */
public class ImgUrlMatch {
	static int articleCount = 0;
	static int imgCount = 0;
	static ArrayList<String> articleUrls = new ArrayList<String>();
	static ArrayList<String> imgUrls = new ArrayList<String>();
	static String fileURL = "D:/ImagesPX/";

	/**
	 * get img urls
	 * 
	 * @param url
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static ArrayList<String> getImgUrls(String url, String timestamp)
			throws ClientProtocolException, IOException {

		ArrayList<String> ImgUrls = new ArrayList<String>();

		String httpResponse = Utils.httpGet(url);

		// String httpResponse = HtmlDriverGet.driverGet(url);

		// String regexArticle = "class=\"theatre-view\" href=\"(.*)\"";

		// String regexArticle =
		// "<a data-ga-category=\"Photo Thumbnail\" data-ga-action=\"Image\" data-bind=\"photo_link\" href=\"(.*)\" id=\"photo_";

		String regexArticle = "<a class=\"post-cover theatre-view\"(.*)href=\"http://(.*)target=\"_blank\">";

		Pattern pArticle = Pattern.compile(regexArticle,
				Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象

		Matcher mArticle = pArticle.matcher(httpResponse);

		// 使用find()方法查找第一个匹配的对象
		// boolean result = m.find();

		while (mArticle.find()) {
			articleCount++;
			String articleurl = mArticle.group();
			// System.out.println(articleurl);
			// articleurl = articleurl.substring(articleurl.indexOf("http://"),
			// articleurl.indexOf("/\" title="));

			// articleurl = articleurl.substring(articleurl.indexOf("https://"),
			// articleurl.indexOf("id=\"photo") - 2); // Illegal character
			// in query at index
			// 92:
			// https://500px.com/photo/96184907/umbrella-girl-by-alonk%27s-roby?from=popular&amp;only=Macro"

			articleurl = articleurl.substring(articleurl.indexOf("http://"),
					articleurl.indexOf("target=\"_blank\"") - 2);

			System.out.println("Article-" + articleCount + " " + articleurl);
			// articleUrls.add(articleurl);
			/*************************************************************************************/
			// String regexImgSrc = "<figure style=\"\"><img src=\"(.*)\"";
			String regexImgSrc = "<figure style=\"\">\\s+<img src=\"http://photos.tuchong.com/(.*)\\s+class=";

			// String regexImgSrc =
			// "img src=\"https://ppcdn.500px.org/(.*).jpg\"";

			Pattern pImgSrc = Pattern.compile(regexImgSrc,
					Pattern.CASE_INSENSITIVE);

			// String articleResponse = HtmlDriverGet.driverGet(articleurl);

			String articleResponse = Utils.httpGet(articleurl);

			// System.out.println(articleResponse);
			Matcher mImgSrc = pImgSrc.matcher(articleResponse);
			// System.out.println(mImgSrc.find());
			while (mImgSrc.find()) {
				imgCount++;
				String src = mImgSrc.group();
				// src = src.substring(src.indexOf("https://ppcdn.500px.org"),
				// src.indexOf(".jpg") + 4);

				src = src.substring(src.indexOf("http://photos.tuchong.com"),
						src.indexOf(".jpg") + 4);

				System.out.println("img-" + imgCount + " " + src);

				ImgUrls.add(src);
				// download img src
				Utils.downloadImg(src, fileURL + "/" + timestamp + "/");

			}
			/*************************************************************************************/

		}

		return ImgUrls;

	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		// String tagUrl = "https://500px.com/popular?categories=Macro&page=";
		// for (int i = 1; i < 2; i++) {
		// getImgUrls(tagUrl + i);
		// }

		String url = "http://tuchong.com/photos/recent/?page=1";
		System.out.println(getImgUrls(url, "20150101122532"));

	}
}
