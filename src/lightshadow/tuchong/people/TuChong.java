/**
 * 
 */
package lightshadow.tuchong.people;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lightshadow.download.tools.Utils;

import org.apache.http.client.ClientProtocolException;

/**
 * @author jack
 *
 */
public class TuChong {

	static String articleRegex = "<a class=\"post-cover theatre-view\"(.*)href=\"http://(.*)\" target=\"_blank\">";
	static String imgSrcRegex = "<figure style=\"\">\\s+<img src=\"http://photos.tuchong.com/(.*)\\s+class=";

	static int articleCount = 0;
	static int imgCount = 0;
	static ArrayList<String> articleUrls = new ArrayList<String>();
	static ArrayList<String> imgUrls = new ArrayList<String>();

	/**
	 * get img urls
	 * 
	 * @param url
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static ArrayList<String> getWeeklyHotImgUrls(String url,
			String savePath) throws ClientProtocolException, IOException {

		ArrayList<String> ImgUrls = new ArrayList<String>();

		Pattern pArticle = Pattern.compile(articleRegex,
				Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象
		String httpResponse = Utils.httpGet(url);
		Matcher mArticle = pArticle.matcher(httpResponse);

		// 使用find()方法查找第一个匹配的对象
		while (mArticle.find()) {
			articleCount++;
			String articleurl = mArticle.group();

			articleurl = articleurl.substring(articleurl.indexOf("http://"),
					articleurl.indexOf("target=\"_blank\"") - 2);

			// System.out.println("Article-" + articleCount + " " + articleurl);
			String regexImgSrc = "<figure style=\"\">\\s+<img src=\"http://photos.tuchong.com/(.*)\\s+class=";

			Pattern pImgSrc = Pattern.compile(regexImgSrc,
					Pattern.CASE_INSENSITIVE);
			String articleResponse = Utils.httpGet(articleurl);
			Matcher mImgSrc = pImgSrc.matcher(articleResponse);
			while (mImgSrc.find()) {
				imgCount++;
				String imgSrc = mImgSrc.group();
				int s = imgSrc.indexOf("http://photos.tuchong.com");
				int e = imgSrc.indexOf(".jpg") + 4;
				if (s < e) {
					imgSrc = imgSrc.substring(s, e);

					System.out.println("img-" + imgCount + " " + imgSrc);

					ImgUrls.add(imgSrc);

					Utils.downloadImg(imgSrc, savePath);
				}

			}

		}

		return ImgUrls;

	}

	/**
	 * 
	 * @param url
	 * @param savePath
	 * @param tagName
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static ArrayList<String> getWeeklyHotImgUrls(String url,
			String savePath, String tagName) throws ClientProtocolException,
			IOException {

		ArrayList<String> ImgUrls = new ArrayList<String>();

		Pattern pArticle = Pattern.compile(articleRegex,
				Pattern.CASE_INSENSITIVE);

		// 用Pattern类的matcher()方法生成一个Matcher对象
		String httpResponse = Utils.httpGet(url);
		Matcher mArticle = pArticle.matcher(httpResponse);

		// 使用find()方法查找第一个匹配的对象
		while (mArticle.find()) {
			articleCount++;
			String articleurl = mArticle.group();
			int s = articleurl.indexOf("http://");
			int e = articleurl.indexOf("target=\"_blank\"") - 2;
			if (s > e) {
				continue;
			}
			articleurl = articleurl.substring(s, e);

			// System.out.println("Article-" + articleCount + " " + articleurl);
			String regexImgSrc = "<figure style=\"\">\\s+<img src=\"http://photos.tuchong.com/(.*)\\s+class=";

			Pattern pImgSrc = Pattern.compile(regexImgSrc,
					Pattern.CASE_INSENSITIVE);
			String articleResponse = Utils.httpGet(articleurl);
			Matcher mImgSrc = pImgSrc.matcher(articleResponse);
			while (mImgSrc.find()) {
				imgCount++;
				String imgSrc = mImgSrc.group();
				int s1 = imgSrc.indexOf("http://photos.tuchong.com");
				int e1 = imgSrc.indexOf(".jpg") + 4;
				if (s1 < e1) {
					imgSrc = imgSrc.substring(s1, e1);

					System.out.println("img-" + imgCount + " " + imgSrc);

					ImgUrls.add(imgSrc);

					Utils.downloadImg(imgSrc, savePath, tagName);
				}

			}

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
		String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss")
				.format(new Date());

		int endPage = 313;

		int LENGTH = Const.tags.length;

		for (int index = 0; index < LENGTH; index++) {

			// String savePath = "D:/ImagesTuChong/weekly/" + tags[index] + "/"
			// + timestamp + "/";
			String savePath = "F:/G/img/tuchong/" + timestamp + "/";
			System.out.println(savePath);
			for (int i = 1; i <= endPage; i++) {
				getWeeklyHotImgUrls(Const.urls_weekly[index] + i, savePath,
						Const.tags[index]);
			}

			for (int i = 1; i <= endPage; i++) {
				getWeeklyHotImgUrls(Const.urls_new[index] + i, savePath,
						Const.tags[index]);
			}
			for (int i = 1; i <= endPage; i++) {
				getWeeklyHotImgUrls(Const.urls_favorited[index] + i, savePath,
						Const.tags[index]);
			}
		}

	}
}
