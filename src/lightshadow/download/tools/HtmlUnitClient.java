/**
 * 
 */
package lightshadow.download.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author jack
 *
 */
public class HtmlUnitClient {
	static String url = "https://500px.com/popular?categories=Macro";

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 * @throws InterruptedException
	 */
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException, InterruptedException {
		HttpGet(url);

	}

	public static String HttpGet(String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException, InterruptedException {
		// 模拟chorme浏览器，其他浏览器请修改BrowserVersion.后面
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		/*
		 * htmluni 2.11 version webclient.setJavaScriptEnabled(true);
		 * webclient.setCssEnabled(false); webclient.setAjaxController(new
		 * NicelyResynchronizingAjaxController()); webclient.setTimeout(5000);
		 * webclient.setThrowExceptionOnScriptError(false);
		 */
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setUseInsecureSSL(true);

		HtmlPage page = webClient.getPage(new URL(url));
		// String content = page.getTextContent();
		// System.out.println(content);
		// return content;

		String pageContent = page.getWebResponse().getContentAsString();
		System.out.println(pageContent);
		return pageContent;

	}

}
