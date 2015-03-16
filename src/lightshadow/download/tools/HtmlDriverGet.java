/**
 * 
 */
package lightshadow.download.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author jack
 *
 */
public class HtmlDriverGet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url1 = "https://500px.com/popular?categories=Macro";
		driverGet(url1);
	}

	public static String driverGet(String url) {

		String html = null;
		// HtmlUnitDriver driver = new HtmlUnitDriver();
		// System.setProperty("webdriver.firefox.driver",
		// "C:/Program Files (x86)/Mozilla Firefox/firefox.exe"); // add this,
		// error! ?
		// WebDriver driver = new HtmlUnitDriver();

		WebDriver driver = new FirefoxDriver();

		try {

			driver.get(url);// 获取当前网页源码
			html = driver.getPageSource();// 打印网页源码
			// driver.wait(); //java.lang.IllegalMonitorStateException
			Thread.sleep(10000);
			// System.out.println(html);
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
}
