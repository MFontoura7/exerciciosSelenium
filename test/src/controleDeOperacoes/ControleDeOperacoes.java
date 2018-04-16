package controleDeOperacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ControleDeOperacoes {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\fontourm\\Desktop\\Selenium\\NewDriver\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--allow-running-insecure-content");
		// IWebDriver driver = new ChromeDriver(options);

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	// @Test
	public void test0() throws InterruptedException {

		int totalDeRegistros = 742; // 742

		String dataInicio = "DataInicio=" + "2018-01-30";
		String dataFim = "DataFim=" + "2018-02-01";
		String contaContabil = "ContaContabil=" + "18892000513";
		String ambiente = "qa"; // qa, dev
		String url = ambiente + "-wso2-core.agiplan.aws.local:8280/services/MateraContabil/ConsultaAnalitica?";

		driver.get(url + dataFim + "&" + contaContabil + "&" + dataInicio);

		ArrayList<MateraContabil> registrosMateraContabil = new ArrayList<MateraContabil>();

		int i = 1;
		while (i <= totalDeRegistros) {

			String cssInicio = "#collapsible" + i + " > div.expanded > div.collapsible-content > div:nth-child(";
			String cssFim = ") > span.text";

			String numeroGuia = driver.findElement(By.cssSelector(cssInicio + "1" + cssFim)).getText();
			char tipo = driver.findElement(By.cssSelector(cssInicio + "2" + cssFim)).getText().charAt(0);
			String cpfCnpj = driver.findElement(By.cssSelector(cssInicio + "3" + cssFim)).getText();
			String conta = driver.findElement(By.cssSelector(cssInicio + "4" + cssFim)).getText();
			String data = driver.findElement(By.cssSelector(cssInicio + "5" + cssFim)).getText();

			String historico;
			try {
				historico = driver.findElement(By.cssSelector(cssInicio + "6" + cssFim)).getText();
			} catch (org.openqa.selenium.NoSuchElementException e) {

				String css2Inicio = "#collapsible";
				int temp = i + 1;
				String css2Fim = " > div.expanded > div.collapsible-content > span";

				historico = driver.findElement(By.cssSelector(css2Inicio + temp + css2Fim)).getText();
				i++;// quando Historico é collapse, incrementa o número, e quebra a lógica
			}

			double valor = Double.parseDouble(driver.findElement(By.cssSelector(cssInicio + "7" + cssFim)).getText());

			registrosMateraContabil.add(new MateraContabil(numeroGuia, tipo, cpfCnpj, conta, data, historico, valor));

			System.out.println(registrosMateraContabil.get(registrosMateraContabil.size() - 1).toString());
			i++;
		}
	}

	//@Test
	public void test1_versao2() throws InterruptedException {

		String ambiente = "qa"; // qa, dev
		String url = "https://cof-ui.k8s." + ambiente + ".bancoagiplan.com.br/";

		driver.get(url);

		WebElement conta = driver.findElement(By.id("mat-input-0"));
		WebElement dataMovimento = driver.findElement(By.id("mat-input-1"));
		WebElement botaoPesquisa = driver.findElement(By.xpath(
				"/html/body/app-root/div/app-conta/mat-card/mat-card-content/form/div/div[3]/div/div/button/span"));

		conta.sendKeys("18892000513");
		dataMovimento.sendKeys("01/01/2018");

		botaoPesquisa.click();

		String xPath = "/html/body/app-root/div/app-conta/mat-card[2]/mat-card-content/div/div/div/form/mat-table";
		WebElement tabela = driver.findElement(By.xpath(xPath));

		List<WebElement> linhasTabela = new ArrayList<WebElement>();
		xPath = "/html/body/app-root/div/app-conta/mat-card[2]/mat-card-content/div/div/div/form/mat-table/mat-header-row";
		linhasTabela.add(tabela.findElement(By.xpath(xPath)));

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		xPath = "//*[@id=\"paginatorAnalitico\"]/div/div[2]/div";

		WebElement paginador = driver.findElement(By.xpath(xPath));
		while (paginador.getText().equals("0 of 0"))
			paginador = driver.findElement(By.xpath(xPath));

		String numeroRegistros = paginador.getText().substring(10);

		int numeroPaginas = Integer.parseInt(numeroRegistros) / 10;

		xPath = "//*[@id=\"paginatorAnalitico\"]/div/div[2]/button[2]/span/div";
		WebElement avancarPagina = driver.findElement(By.xpath(xPath));

		int registrosUltimaPagina = Integer.parseInt(numeroRegistros) % 10;

		if (registrosUltimaPagina != 0)
			numeroPaginas++;

		xPath = "/html/body/app-root/div/app-conta/mat-card[2]/mat-card-content/div/div/div/form/mat-table/mat-row[";

		int limite;

		List<String> conteudoLinha = new ArrayList<String>();

		for (int i = 1; i <= numeroPaginas; i++) {
			if (i != numeroPaginas || registrosUltimaPagina == 0)
				limite = 10;
			else
				limite = registrosUltimaPagina;

			for (int j = 1; j <= limite; j++) {
				linhasTabela.add(tabela.findElement(By.xpath(xPath + j + "]")));
				conteudoLinha.add(linhasTabela.get(linhasTabela.size() - 1).getText());
			}

			if (i != numeroPaginas) {
				avancarPagina.click();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			}
		}

		for (int k = 0; k < conteudoLinha.size(); k++)
			System.out.println(conteudoLinha.get(k));

	}
	
	@Test
	public void teste2() throws InterruptedException {
		String selectorMenu = "md-primary mat-button";
		WebElement menu = driver.findElement(By.className(selectorMenu));
		menu.click();
		//WebElement subMenu = driver.findElement(By.xpath("//*[@id=\"cdk-overlay-7\"]/div/div/button[2]"));
		//subMenu.click();
		
	}

}
