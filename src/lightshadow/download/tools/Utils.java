/**
 * 
 */
package lightshadow.download.tools;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author jack
 *
 */
public class Utils {
	public static final String IMAGE_NAME = "微信公众号@光影人像@ols-lightshadow 看美女~读美文";

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String driverGet(String url) {

		String html = null;

		// 打开指定路径的
		System.setProperty(
				"webdriver.firefox.bin",
				"D:\\XMLRPC\\Firefox_Portable_33.1.1\\FirefoxPortable\\App\\Firefox\\firefox.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		FirefoxProfile firefoxprofile = new FirefoxProfile(
				new File(
						"D:\\XMLRPC\\Firefox_Portable_33.1.1\\FirefoxPortable\\Data\\profile\\"));
		capabilities.setCapability(FirefoxDriver.PROFILE, firefoxprofile);

		WebDriver driver = new FirefoxDriver(capabilities);
		// // 隐性等待
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//
		// // set browser window size
		// Dimension targetSize = new Dimension(0, 0);
		// driver.manage().window().setSize(targetSize);
		// // set browser position
		// java.awt.Dimension screenSize = Toolkit.getDefaultToolkit()
		// .getScreenSize();
		// Point targetPosition = new Point(screenSize.width,
		// screenSize.height);
		// driver.manage().window().setPosition(targetPosition);
		// // WebDriver driver = new InternetExplorerDriver();

		try {

			driver.get(url);
			// ((JavascriptExecutor) driver).executeScript("scrollTo(0,10000)");
			Thread.sleep(5000);
			// 获取当前网页源码
			html = driver.getPageSource();// 打印网页源码

		} catch (Exception e) {// 打印堆栈信息
			e.printStackTrace();
		} finally {
			try {// 关闭并退出
				driver.close();
				driver.quit();
			} catch (Exception e) {
			}
		}
		return html;
	}

	/**
	 * httpget
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpGet(String url) throws ClientProtocolException,
			IOException {
		String result = "";
		HttpGet request = new HttpGet(url);
		HttpResponse response = HttpClients.createDefault().execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity());
			// System.out.println(result);
		}
		return result;

	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param imgUrl
	 * @param fileURL
	 */

	public static void downloadImg(String imgUrl, String fileURL) {
		try {

			// System.out.println(imgUrl);
			// 创建流
			BufferedInputStream in = new BufferedInputStream(
					new URL(imgUrl).openStream());

			// 生成图片名
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date());
			String sName = Utils.IMAGE_NAME + timestamp + ".jpg";
			// 存放地址
			File imgdir = new File(fileURL);
			if (!imgdir.exists()) {
				imgdir.mkdirs();
			}
			File img = new File(fileURL + sName);

			// 生成图片
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(img));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param imgUrl
	 * @param fileURL
	 * @param tagName
	 */
	public static void downloadImg(String imgUrl, String fileURL, String tagName) {
		try {

			// System.out.println(imgUrl);
			// 创建流
			BufferedInputStream in = new BufferedInputStream(
					new URL(imgUrl).openStream());

			// 生成图片名
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date());
			String sName = Utils.IMAGE_NAME + timestamp + "_" + tagName
					+ ".jpg";
			// 存放地址
			File imgdir = new File(fileURL);
			if (!imgdir.exists()) {
				imgdir.mkdirs();
			}
			File img = new File(fileURL + sName);

			// 生成图片
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(img));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException,
			IOException {

		String img = "https://ppcdn.500px.org/96193277/566abc5f7e1debcedd2f3353e1ba9fa8b7c0845f/2048.jpg";
		downloadImg(img, "D:/Images/");

	}

	public static void httpsGet(String pxUrl) {
		// TODO Auto-generated method stub

	}
}
