/**
 * 
 */
package lightshadow.netease.photo;

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
public class NetEasePhoto {
	static String[] tags = { "3", "2", "1" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	static String url[] = { "http://pp.163.com/pp/#p=-1&c=-1&m=", "&page=" }; //$NON-NLS-1$ //$NON-NLS-2$

	static String articleRegex = Messages
			.getString("NetEasePhoto.articleRegex"); //$NON-NLS-1$
	static String imgSrcRegex = Messages.getString("NetEasePhoto.imgSrcRegex"); //$NON-NLS-1$

	static int articleCount = 0;
	static int imgCount = 0;
	static ArrayList<String> articleUrls = new ArrayList<String>();
	static ArrayList<String> imgUrls = new ArrayList<String>();

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss") //$NON-NLS-1$
				.format(new Date());

		String savePath = "D:/ImagesNetEase/" + timestamp + "/"; //$NON-NLS-1$ //$NON-NLS-2$

		for (String tag : tags) {
			for (int page = 1; page <= 500; page++)
				getWeeklyHotImgUrls(url[0] + tag + url[1] + page, savePath);
		}

	}

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
		String httpResponse = Utils.driverGet(url);
		// System.out.println(httpResponse);
		Matcher mArticle = pArticle.matcher(httpResponse);

		// 使用find()方法查找第一个匹配的对象
		while (mArticle.find()) {
			articleCount++;
			String articleurl = mArticle.group();
			int startArticleurl = articleurl.indexOf("http://pp.163.com");
			int endArticleurl = articleurl.indexOf(".html\"") + 5;
			if (startArticleurl > endArticleurl) {
				continue;
			}

			articleurl = articleurl.substring(startArticleurl, endArticleurl); // .html
																				// length
																				// is
																				// 5

			System.out.println("Article " + articleCount + " " + articleurl);
			String regexImgSrc = Messages.getString("NetEasePhoto.imgSrcRegex"); //$NON-NLS-1$

			Pattern pImgSrc = Pattern.compile(regexImgSrc,
					Pattern.CASE_INSENSITIVE);
			String articleResponse = Utils.driverGet(articleurl);
			// System.out.println(articleResponse);
			Matcher mImgSrc = pImgSrc.matcher(articleResponse);
			while (mImgSrc.find()) {
				imgCount++;
				String imgSrc = mImgSrc.group();
				int s = imgSrc.indexOf("http://");
				int e = imgSrc.indexOf(".jpg\" src=\"") + 4; // .jpg length is 4
				if (s < e) {
					imgSrc = imgSrc.substring(s, e);

					System.out.println("img " + imgCount + " " + imgSrc); //$NON-NLS-1$ //$NON-NLS-2$

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

			articleurl = articleurl.substring(articleurl.indexOf("http://"), //$NON-NLS-1$
					articleurl.indexOf("target=\"_blank\"") - 2); //$NON-NLS-1$

			// System.out.println("Article-" + articleCount + " " + articleurl);
			String regexImgSrc = Messages.getString("NetEasePhoto.imgSrcRegex"); //$NON-NLS-1$

			Pattern pImgSrc = Pattern.compile(regexImgSrc,
					Pattern.CASE_INSENSITIVE);
			String articleResponse = Utils.httpGet(articleurl);
			Matcher mImgSrc = pImgSrc.matcher(articleResponse);
			while (mImgSrc.find()) {
				imgCount++;
				String imgSrc = mImgSrc.group();
				int s = imgSrc.indexOf("http://photos.tuchong.com"); //$NON-NLS-1$
				int e = imgSrc.indexOf(".jpg") + 4; //$NON-NLS-1$
				if (s < e) {
					imgSrc = imgSrc.substring(s, e);

					System.out.println("img-" + imgCount + " " + imgSrc); //$NON-NLS-1$ //$NON-NLS-2$

					ImgUrls.add(imgSrc);

					Utils.downloadImg(imgSrc, savePath, tagName);
				}

			}

		}

		return ImgUrls;

	}

}
