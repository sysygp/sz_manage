package org.system.zhrt.showcase.functional.ajax;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.system.zhrt.showcase.functional.BaseSeleniumTestCase;

/**
 * 测试Ajax Mashup.
 * 
 * @wangjx
 */
public class AjaxFT extends BaseSeleniumTestCase {

	@Test
	public void mashup() {
		s.open("/");
		s.click(By.linkText("Web演示"));
		s.click(By.linkText("跨域名Mashup演示"));

		s.click(By.xpath("//input[@value='获取内容']"));
		s.waitForVisible(By.id("mashupContent"));
		assertEquals("你好，世界！", s.getText(By.id("mashupContent")));
	}
}
