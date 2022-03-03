import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class Testclass {

    public static WebDriver driver;




    public void takeScreenShot(int NrTestu) {


        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(src, new File("src/main/resources/" + NrTestu + "_screenshot.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }


    @BeforeTest
    public void startBrowser() {
        System.out.println("BeforeTest");

        //Konfiguracja początkowa
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();


        driver.manage().window().maximize();

        driver.get("http://www.selenium-shop.pl/");


    }

    @AfterTest
    public void closeBrowser() {
        System.out.println("AfterTest");
        driver.quit();
    }

    //Kliknij w produkt o nazwie: KOSZULKA WEST HAM UNITED a następnie zweryfikuj adres strony, na którą zostałeś przeniesiony.
    //Zrób screen po przeniesieniu się na stronę szczegółów wybranego produktu i zapisz go w katalogu src/main/resources
    @Test(priority = 1)
    public void title() {

        driver.manage().deleteAllCookies();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement west_ham_tshirt = driver.findElement(By.xpath("//*[@class='row multi-columns-row']/div[5]"));
        wait.until(ExpectedConditions.visibilityOf(west_ham_tshirt));

        west_ham_tshirt.click();
        takeScreenShot(1);
        Assert.assertEquals(driver.getTitle(), "Koszulka West Ham United – Selenium Shop Automatyzacja Testów", "title is not correct");
        System.out.println("Web title is correct");
    }

    //Zweryfikuj nazwę produktu
    @Test(priority = 2)
    public void name() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        WebElement name = driver.findElement(By.xpath("//h1[@class='product_title entry-title']"));
        wait.until(ExpectedConditions.visibilityOf(name));
        Assert.assertEquals(name.getText(), "KOSZULKA WEST HAM UNITED");
        System.out.println("Product name is correct");

    }

    //Zweryfikuj cenę produktu
    @Test(priority = 3)
    public void price() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement price = driver.findElement(By.xpath("//*[@class='price']/span"));
        wait.until(ExpectedConditions.visibilityOf(price));
        Assert.assertEquals(price.getText(), "90,00 ZŁ");
        System.out.println("Price is correct");

    }

    //Zweryfikuj ilość sztuk produktu
    @Test(priority = 4)
    public void quantiity() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement quantity_field = driver.findElement(By.xpath("//input[@title='Szt.']"));
        wait.until(ExpectedConditions.visibilityOf(quantity_field));
        Assert.assertEquals(quantity_field.getAttribute("value"), "1");
        System.out.println("Value is correct");

    }

    //Kliknij przycisk DODAJ DO KOSZYKA oraz zweryfikuj, czy wyświetlony komunikat zawiera następującą treść: “Koszulka West Ham United” został dodany do koszyka.
    @Test(priority = 5)
    public void add_to_cart() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement addtocartbutton = driver.findElement(By.xpath("//*[@name='add-to-cart']"));
        wait.until(ExpectedConditions.elementToBeClickable(addtocartbutton));
        addtocartbutton.click();

        WebElement information = driver.findElement(By.className("woocommerce-message"));
        wait.until(ExpectedConditions.visibilityOf(information));
        Assert.assertTrue(information.isDisplayed());
        System.out.println("Informacje zostały wyświetlone. Informacje: " + information.getText());

    }

    //Kliknij Zobacz koszyk i zweryfikuj tytuł strony, na którą zostałeś przeniesiony.
    //Zrób screen po przeniesieniu się na stronę koszyka i zapisz go w katalogu src/main/resources
    @Test(priority = 6)
    public void checkthecart() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement checkthecartbutton = driver.findElement(By.linkText("Zobacz koszyk"));
        wait.until(ExpectedConditions.elementToBeClickable(checkthecartbutton));
        checkthecartbutton.click();
        takeScreenShot(6);

        Assert.assertEquals(driver.getTitle(), "Koszyk – Selenium Shop Automatyzacja Testów");
        System.out.println("Web title is correct");
    }

    //Zweryfikuj czy domyślnie została zaznaczona pozycja: Darmowa wysyłka
    @Test(priority = 7)
    public void free_shipment() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement checkbox = driver.findElement(By.xpath("//input[@value='free_shipping:2']"));
        wait.until(ExpectedConditions.visibilityOf(checkbox));
        Assert.assertTrue(checkbox.isSelected());
        System.out.println("Free delivery is selected");

    }

    //Kliknij przycisk: PRZEJDŹ DO KASY oraz zweryfikuj tytuł strony, na którą zostałeś przeniesiony.
    //Zrób screen po przeniesieniu się na ekran podsumowania zamówienia i zapisz go w katalogu src/main/resources
    @Test(priority = 8)
    public void counter() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement counterbutton = driver.findElement(By.linkText("PRZEJDŹ DO KASY"));
        counterbutton.click();
        takeScreenShot(8);
        Assert.assertEquals(driver.getTitle(), "Zamówienie – Selenium Shop Automatyzacja Testów");
        System.out.println("Web title is correct: " + driver.getTitle());

    }

    //Zweryfikuj nazwę produktu
    @Test(priority = 9)
    public void productnamecheck() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement productname = driver.findElement(By.xpath("//td[@class='product-name']"));
        Assert.assertEquals(productname.getText(), "Koszulka West Ham United  × 1");
        System.out.println("Product name is correct: " + productname.getText());

    }

    //Zweryfikuj cenę produktu
    @Test(priority = 10)
    public void final_price_check() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement finalprice = driver.findElement(By.xpath("//span[@class='woocommerce-Price-amount amount']"));
        wait.until(ExpectedConditions.visibilityOf(finalprice));
        Assert.assertEquals(finalprice.getText(), "90,00 zł");
        System.out.println("Price is correct, price is: " + finalprice.getText());

    }

    @Test(priority = 11)
    public void free_delivery() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement delivery = driver.findElement(By.xpath("//input[@value='free_shipping:2']"));
        wait.until(ExpectedConditions.visibilityOf(delivery));
        Assert.assertTrue(delivery.isSelected());
        System.out.println("Free delivery is selected");
    }

    //Zweryfikuj kwotę całkowitą produktu zamówienia (pole Suma)
    @Test(priority = 12)
    public void amount_price() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement amount = driver.findElement(By.xpath("//td/strong/span[@class='woocommerce-Price-amount amount']"));
        wait.until(ExpectedConditions.visibilityOf(amount));
        Assert.assertEquals(amount.getText(), "90,00 zł");
        System.out.println("Amount price is correct");
    } //
}


