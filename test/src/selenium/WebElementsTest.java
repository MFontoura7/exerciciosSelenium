package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;



public class WebElementsTest {

	private WebDriver driver;

	String metodoExecutado;
	
	private double valor1 = 10; // usado nos testes da calculadora
	private double valor2 = 25; // usado nos testes da calculadora

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\fontourm\\Desktop\\Selenium\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("http://www.treinoautomacao.hol.es/");
	}

	@After
	public void tearDown() throws Exception {
		//File scrnShot =	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//FileUtils.copyFile(scrnShot , new File("C:\\Users\\fontourm\\eclipse\\java-oxygen\\prints\\main_page.png"));
				
		new CaptureScreenShot(driver).captureScreenShot(metodoExecutado); 
		
		driver.quit();
	}

	// @Test
	public void test1_textField() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		WebElement textFieldNome = driver.findElement(By.name("txtbox1"));
		textFieldNome.sendKeys("Marlon");
		assertEquals("Marlon", textFieldNome.getAttribute("value"));
	}

	// @Test
	public void test2_RadioButton() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		List<WebElement> radioButtons = driver.findElements(By.name("radioGroup1"));
		for (WebElement e : radioButtons) {
			if (e.getAttribute("value").equals("Radio Button 3 selecionado")) {
				e.click();
				assertTrue(e.isSelected());
			}
		}
	}

	// @Test
	public void test3_CheckBox() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		List<WebElement> checkBoxes = driver.findElements(By.name("chkbox"));
		for (WebElement e : checkBoxes) {
			if (e.getAttribute("value").equals("Check Box 3 selecionado")
					|| e.getAttribute("value").equals("Check Box 4 selecionado")) {
				e.click();
				assertTrue(e.isSelected());
			}
		}
	}

	// @Test
	public void test4_DropdownSingle() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		WebElement dropdownSingle = driver.findElement(By.name("dropdownlist"));
		Select listBoxesElements = new Select(dropdownSingle);
		listBoxesElements.selectByVisibleText("Item 7");// listBoxesElements.selectByIndex(6);
		assertEquals("Item 7", listBoxesElements.getFirstSelectedOption().getText());
	}

	// @Test
	public void test5_DropdownMulti() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		WebElement dropdownMulti = driver.findElement(By.name("multiselectdropdown"));
		Select listBoxesElements = new Select(dropdownMulti);

		listBoxesElements.selectByVisibleText("Item 5");
		listBoxesElements.selectByVisibleText("Item 8");
		listBoxesElements.selectByVisibleText("Item 9");

		List<WebElement> selecionados = listBoxesElements.getAllSelectedOptions();

		for (WebElement e : selecionados) {
			boolean isSelected = e.getText().equals("Item 5") || e.getText().equals("Item 8")
					|| e.getText().equals("Item 9");

			assertTrue(isSelected);
		}
	}

	// @Test
	public void test6_iFrame() throws InterruptedException {
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		driver.switchTo().frame("iframe_b");
		WebElement txtNomeTarget = driver.findElement(By.name("s"));
		txtNomeTarget.sendKeys("marlon");
		Thread.sleep(3000);
		assertEquals("marlon", txtNomeTarget.getAttribute("value"));

		driver.switchTo().defaultContent(); // volta pro iFrame origem

		driver.switchTo().frame("iframe_d");
		WebElement txtNomeSelenium = driver.findElement(By.name("q"));
		txtNomeSelenium.sendKeys("marlon");
		Thread.sleep(3000);
		assertEquals("marlon", txtNomeSelenium.getAttribute("value"));
	}

	@Test
	public void test7_Alerts() throws InterruptedException {
		
		metodoExecutado = new Object(){}.getClass().getEnclosingMethod().getName();
		
		driver.get("http://www.treinoautomacao.hol.es/elementsweb.html");
		WebElement alertButton = driver.findElement(By.name("alertbtn"));
		alertButton.click();
		Alert myAlert = driver.switchTo().alert(); // capturar um alerta
		assertEquals("Eu sou um alerta!", myAlert.getText());
		myAlert.accept();// enter
		// myAlert.dismiss();//esc
	}

	 @Test
	public void test8a_desafioCalculadora_Somar() throws InterruptedException {
		
		metodoExecutado = new Object(){}.getClass().getEnclosingMethod().getName();

		WebElement calculadora = driver.findElement(By.xpath("/html/body/div/div/ul/li[3]/a"));
		calculadora.click();

		WebElement textFieldNumber1 = driver.findElement(By.id("number1"));
		WebElement textFieldNumber2 = driver.findElement(By.id("number2"));
		WebElement buttonSomar = driver.findElement(By.id("somar"));
		WebElement textFieldTotal = driver.findElement(By.id("total"));

		textFieldNumber1.sendKeys(String.valueOf(valor1));
		textFieldNumber2.sendKeys(String.valueOf(valor2));
		buttonSomar.click();

		if (valor1 % (int) valor1 == 0 && valor2 % (int) valor2 == 0)// ambos numeros são decimais
			assertEquals(String.valueOf((int) valor1 + (int) valor2), textFieldTotal.getAttribute("value"));
		else // algum é decimal
			assertEquals(String.valueOf(valor1 + valor2), textFieldTotal.getAttribute("value"));
	}

	 @Test
	public void test8b_desafioCalculadora_Subtrair() throws InterruptedException {
		 
		 metodoExecutado = new Object(){}.getClass().getEnclosingMethod().getName();

		WebElement calculadora = driver.findElement(By.xpath("/html/body/div/div/ul/li[3]/a"));
		calculadora.click();

		WebElement textFieldNumber1 = driver.findElement(By.id("number1"));
		WebElement textFieldNumber2 = driver.findElement(By.id("number2"));
		WebElement buttonSomar = driver.findElement(By.id("subtrair"));
		WebElement textFieldTotal = driver.findElement(By.id("total"));

		textFieldNumber1.sendKeys(String.valueOf(valor1));
		textFieldNumber2.sendKeys(String.valueOf(valor2));
		buttonSomar.click();

		if (valor1 % (int) valor1 == 0 && valor2 % (int) valor2 == 0)// ambos numeros são decimais
			assertEquals(String.valueOf((int) valor1 - (int) valor2), textFieldTotal.getAttribute("value"));
		else // algum é decimal
			assertEquals(String.valueOf(valor1 - valor2), textFieldTotal.getAttribute("value"));
	}

	@Test
	public void test8c_desafioCalculadora_Multiplicar() throws InterruptedException {
		
		metodoExecutado = new Object(){}.getClass().getEnclosingMethod().getName();

		WebElement calculadora = driver.findElement(By.xpath("/html/body/div/div/ul/li[3]/a"));
		calculadora.click();

		WebElement textFieldNumber1 = driver.findElement(By.id("number1"));
		WebElement textFieldNumber2 = driver.findElement(By.id("number2"));
		WebElement buttonSomar = driver.findElement(By.id("multiplicar"));
		WebElement textFieldTotal = driver.findElement(By.id("total"));

		textFieldNumber1.sendKeys(String.valueOf(valor1));
		textFieldNumber2.sendKeys(String.valueOf(valor2));
		buttonSomar.click();

		if (valor1 % (int) valor1 == 0 && valor2 % (int) valor2 == 0)// ambos numeros são decimais
			assertEquals(String.valueOf((int) valor1 * (int) valor2), textFieldTotal.getAttribute("value"));
		else // algum é decimal
			assertEquals(String.valueOf(valor1 * valor2), textFieldTotal.getAttribute("value"));
	}

	// @Test
	public void test8d_desafioCalculadora_Dividir() throws InterruptedException {

		WebElement calculadora = driver.findElement(By.xpath("/html/body/div/div/ul/li[3]/a"));
		calculadora.click();

		WebElement textFieldNumber1 = driver.findElement(By.id("number1"));
		WebElement textFieldNumber2 = driver.findElement(By.id("number2"));
		WebElement buttonSomar = driver.findElement(By.id("dividir"));
		WebElement textFieldTotal = driver.findElement(By.id("total"));

		textFieldNumber1.sendKeys(String.valueOf(valor1));
		textFieldNumber2.sendKeys(String.valueOf(valor2));
		buttonSomar.click();

		if (valor1 % (int) valor1 == 0 && valor2 % (int) valor2 == 0)// ambos numeros são decimais
			assertEquals(String.valueOf((int) valor1 / (int) valor2), textFieldTotal.getAttribute("value"));
		else // algum é decimal
			assertEquals(String.valueOf(valor1 / valor2), textFieldTotal.getAttribute("value"));
	}

	// @Test
	public void test8e_desafioCalculadora_DividirPorZero() throws InterruptedException {

		WebElement calculadora = driver.findElement(By.xpath("/html/body/div/div/ul/li[3]/a"));
		calculadora.click();

		WebElement textFieldNumber1 = driver.findElement(By.id("number1"));
		WebElement textFieldNumber2 = driver.findElement(By.id("number2"));
		WebElement buttonSomar = driver.findElement(By.id("dividir"));
		WebElement textFieldTotal = driver.findElement(By.id("total"));

		textFieldNumber1.sendKeys(String.valueOf(valor1));
		textFieldNumber2.sendKeys("0");
		buttonSomar.click();

		assertEquals("Infinity", textFieldTotal.getAttribute("value"));
	}

	// @Test
	public void test9_dragAndDrop() throws InterruptedException {
		driver.get("http://sahitest.com/demo/dragDropMooTools.htm");

		WebElement origem = driver.findElement(By.id("dragger"));
		WebElement destino1 = driver.findElement(By.xpath("/html/body/div[2]"));
		WebElement destino2 = driver.findElement(By.xpath("/html/body/div[3]"));
		WebElement destino3 = driver.findElement(By.xpath("/html/body/div[4]"));
		WebElement destino4 = driver.findElement(By.xpath("/html/body/div[5]"));

		new Actions(driver).dragAndDrop(origem, destino1).perform();
		new Actions(driver).dragAndDrop(origem, destino2).perform();
		new Actions(driver).dragAndDrop(origem, destino3).perform();
		new Actions(driver).dragAndDrop(origem, destino4).perform();

		assertEquals("deveria ser dropped", "dropped", destino1.getText());
		assertEquals("deveria ser dropped", "dropped", destino2.getText());
		assertEquals("deveria ser dropped", "dropped", destino3.getText());
		assertEquals("deveria ser dropped", "dropped", destino4.getText());
	}

	// @Test
	public void test10_multiplasAbas() throws InterruptedException {

		assertEquals("Treino Automação de Testes", driver.getTitle());

		WebElement link = driver.findElement(By.linkText("Drag and Drop"));
		link.click();

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < tabs.size(); i++) {
			driver.switchTo().window(tabs.get(i));
			System.out.println("Indice: " + i + " : " + driver.getTitle());
		}

		driver.switchTo().window(tabs.get(1));
		assertEquals("Mootools Drag and Drop Example", driver.getTitle());

		driver.switchTo().window(tabs.get(0));
		assertEquals("Treino Automação de Testes", driver.getTitle());
	}

	//Test
	public void test() throws InterruptedException {

		assertEquals("Treino Automação de Testes", driver.getTitle());

		driver.findElement(By.linkText("Calculadora")).click();
		Thread.sleep(3000);
		assertEquals("Desafio Automação Cálculos", driver.getTitle());

		driver.findElement(By.linkText("Localizar Table")).click();
		Thread.sleep(3000);
		assertEquals("Trabalhando com tables", driver.getTitle());

		driver.navigate().back();
		assertEquals("Desafio Automação Cálculos", driver.getTitle());

		driver.navigate().forward();
		assertEquals("Trabalhando com tables", driver.getTitle());
	}

}
