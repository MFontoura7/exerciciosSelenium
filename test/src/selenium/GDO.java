package selenium;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class GDO {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\fontourm\\Desktop\\Selenium\\NewDriver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("https://gdo-ui.k8s.dev.bancoagiplan.com.br");

		WebElement textfield;
		textfield = driver.findElement(By.name("UserName"));
		textfield.sendKeys("dxc.marlon@agiplan.com.br");
		textfield = driver.findElement(By.name("Password"));
		textfield.sendKeys("0134679Mf72");

		WebElement botaoEntrar = driver.findElement(By.xpath("//*[@id=\"submitButton\"]"));
		botaoEntrar.click();
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
	}

	// @Test
	public void test0() throws InterruptedException {
		WebElement titulo = driver.findElement(By.xpath("/html/body/app-root/app-header/mat-toolbar[2]"));
		assertEquals("Gestão de Desenvolvimento Organizacional", titulo.getText());
	}

	/*
	 * @Test public void test1_Login() throws InterruptedException { WebElement
	 * textfield; textfield = driver.findElement(By.name("UserName"));
	 * textfield.sendKeys("dxc.marlon@agiplan.com.br"); textfield =
	 * driver.findElement(By.name("Password")); textfield.sendKeys("0134679Mf72");
	 * 
	 * WebElement botaoEntrar =
	 * driver.findElement(By.xpath("//*[@id=\"submitButton\"]"));
	 * botaoEntrar.click();
	 * 
	 * WebElement titulo =
	 * driver.findElement(By.xpath("/html/body/app-root/app-header/mat-toolbar[2]"))
	 * ;
	 * 
	 * assertEquals("Gestão de Desenvolvimento Organizacional", titulo.getText()); }
	 */

	@Test
	public void test2_tabela() throws InterruptedException {

		WebElement aba = driver.findElement(By.xpath("/html/body/app-root/app-header/mat-toolbar[3]/ul/li[4]/a/span"));
		aba.click();

		ArrayList<List<WebElement>> todasLinhas = new ArrayList<List<WebElement>>();

		WebElement tabela = driver
				.findElement(By.xpath("/html/body/app-root/div/app-configuracoes/mat-card/mat-card-content"));

		todasLinhas.add(tabela.findElements(By.xpath(
				"/html/body/app-root/div/app-configuracoes/mat-card/mat-card-content/div/div/div/form/mat-table/mat-header-row")));
		for (int i = 1; i <= 16; i++) {
			String xPath = "/html/body/app-root/div/app-configuracoes/mat-card/mat-card-content/div/div/div/form/mat-table/mat-row["
					+ i + "]";
			todasLinhas.add(tabela.findElements(By.xpath(xPath)));
		}

		for (int i = 0; i < todasLinhas.size(); i++) {
			System.out.println(todasLinhas.get(i).get(0).getText());
		}
	}

}
