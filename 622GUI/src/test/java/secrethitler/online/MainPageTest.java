package secrethitler.online;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {

    private final MainPage mainPage = new MainPage();
    private ArrayList<String> nameArray = new ArrayList<>();
    private ArrayList<String> roleArray = new ArrayList<>();


    @BeforeAll
    public static void setUpAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @BeforeEach
    public void setUp() {
        String name1 = "Max";
        String name2 = "Emma";
        String name3 = "Qiao";
        String name4 = "Ali";
        String name5 = "John";
        String name6 = "Tom";
        String name7 = "Nancy";
        String name8 = "Abbey";
        String name9 = "Alex";
        String name10 = "Selina";
        String name11 = "Jacob";
        nameArray.add(name1);
        nameArray.add(name2);
        nameArray.add(name3);
        nameArray.add(name4);
        nameArray.add(name5);
        nameArray.add(name6);
        nameArray.add(name7);
        nameArray.add(name8);
        nameArray.add(name9);
        nameArray.add(name10);
        nameArray.add(name11);

        Configuration.startMaximized = true;
        open("https://secret-hitler.online/");
    }

    public void fivePlayerSetUp() {
        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("/html/body/div/div/div[1]/div[2]/div/div[1]/img[1]").click();
        $x("//*[@id=\"prompt-button\"]").click();

        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //open 4 new windows
        for (int i = 0; i < 4; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 6; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index +"]";
            $x(profileImg).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"prompt-button\"]")));
            $x("//*[@id=\"prompt-button\"]").click();
        }
        driver.switchTo().window(tabs.get(0));
    }



    public void tenPlayerSetUp() {

        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();

        //open 9 new windows
        for (int i = 0; i < 9; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 11; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index +"]";
            $x(profileImg).click();
            $x("//*[@id=\"prompt-button\"]").click();
        }
        driver.switchTo().window(tabs.get(0));
    }

    @Test
    public void testJoinDisabled() {
        $x("//*[@id=\"root\"]/div/div[1]/button").shouldBe(Condition.disabled);
    }

    @Test
    public void testCreateLobbyDisabled() {
        $x("//*[@id=\"root\"]/div/div[2]/button").shouldBe(Condition.disabled);
    }

    @Test
    public void createLobby() {
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys("Max");
        String str = $x("//*[@id=\"root\"]/div/div[2]/div/label/input").getValue();
        assertTrue(str.contains("Max"));
        $x("//*[@id=\"root\"]/div/div[2]/button").shouldNotBe(Condition.disabled);
    }

    @Test
    public void selectLookButtonDisabledBeforeSelectingLook() {
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys("Max");
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"prompt-button\"]").shouldBe(Condition.disabled);
    }

    @Test
    public void selectLookSuccess() {
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys("Max");
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").shouldNotBe(Condition.disabled);
    }

    @Test
    public void createLobbyDoneSuccess() {
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys("Max");
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();

        //should have a link to direct to lobby name
        String str = $x("//*[@id=\"linkText\"]").getValue();
        assertTrue(str.contains("https://secret-hitler.online/?lobby="));

        //display player name and host identity correctly
        String name = $x("//*[@id=\"player-name\"]/div").getText();
//        System.out.println(name);
        assertTrue(name.contains("Max [★VIP]"));

        //these buttons should not be disabled:
        $x("//*[@id=\"root\"]/div/div[2]/div[2]/button").shouldNotBe(Condition.disabled);
        $x("//*[@id=\"lobby-change-icon-button\"]").shouldNotBe(Condition.disabled);
        $x("//*[@id=\"lobby-button-container\"]/button[2]").shouldNotBe(Condition.disabled);

        //start game button is disabled because of not enough players:
        $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldBe(Condition.disabled);
    }

    @Test
    public void createLobbyExit() {
        String playerName = "Max";
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(playerName);
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();

        $x("//*[@id=\"lobby-button-container\"]/button[2]").click();
        String str = $x("//*[@id=\"linkText\"]").getValue();
        //auto fill lobby and player name:
        String lobby = $x("//*[@id=\"root\"]/div/div[1]/div[1]/label/input").getValue();
        assertTrue(str.contains(lobby));
        String name = $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").getValue();
        assertTrue(name.equals(playerName));
    }

    @Test
    public void joinLobby() {
        String playerName = "Max";
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(playerName);
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        $x("//*[@id=\"lobby-button-container\"]/button[2]").click();

        //rejoin lobby, display player name and identity correctly:
        String name = $x("//*[@id=\"player-name\"]/div").getText();
        assertTrue(name.contains("Max [★VIP]"));
    }

    @Test
    public void sameNameJoinsLobby() {
        String name = "Max";
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(name);
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(lobbyLink);

        //use same name
        $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(name);
        $x("//*[@id=\"root\"]/div/div[1]/button").click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String errorMsg = $x("//*[@id=\"errormessage\"]").getText();
//        System.out.println(errorMsg);
        assertTrue(errorMsg.equals("There is already a user with the name 'Max' in the lobby."));
    }

    @Test
    public void fivePlayerJoinsLobby() {

        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();

        //open 4 new windows
        for (int i = 0; i < 4; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 6; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index +"]";
            $x(profileImg).click();
            $x("//*[@id=\"prompt-button\"]").click();
            $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldBe(Condition.disabled);
        }
        driver.switchTo().window(tabs.get(0));
        $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldNotBe(Condition.disabled);
    }

    @Test
    public void tenPlayersJoinLobby() {

        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();

        //open 4 new windows
        for (int i = 0; i < 10; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 11; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index +"]";
            $x(profileImg).click();
            $x("//*[@id=\"prompt-button\"]").click();
            $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldBe(Condition.disabled);
        }
        driver.switchTo().window(tabs.get(0));
        $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldNotBe(Condition.disabled);
    }


    @Test
    public void elevenPlayersJoinLobby() {

        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("//*[@id=\"icon\"]").click();
        $x("//*[@id=\"prompt-button\"]").click();
        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();

        //open 4 new windows
        for (int i = 0; i < 11; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 11; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index +"]";
            $x(profileImg).click();
            $x("//*[@id=\"prompt-button\"]").click();
            $x("//*[@id=\"lobby-button-container\"]/button[1]").shouldBe(Condition.disabled);
        }

        // the 11th player
        driver.switchTo().window(tabs.get(10));
        driver.get(lobbyLink);
        $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(10));
        $x("//*[@id=\"root\"]/div/div[1]/button").click();
        String errMsg = $x("//*[@id=\"errormessage\"]").getText();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(errMsg.equals("The lobby is currently full."));
    }

    /**
     * Find an error: player identity page not showing up on some players' page. See selenium web driver test img
     */
    @Test
    public void startFiveGame() {
        //set up lobby, max is host
        $x("//*[@id=\"root\"]/div/div[2]/div/label/input").sendKeys(nameArray.get(0));
        $x("//*[@id=\"root\"]/div/div[2]/button").click();
        $x("/html/body/div/div/div[1]/div[2]/div/div[1]/img[1]").click();
        $x("//*[@id=\"prompt-button\"]").click();

        //copy lobby link
        String lobbyLink = $x("//*[@id=\"linkText\"]").getValue();

        //get web driver
        WebDriver driver = getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //open 4 new windows
        for (int i = 0; i < 4; i++) {
            js.executeScript("window.open()");
        }
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        for (int index = 2; index < 6; index++) {
            //player i joins
            driver.switchTo().window(tabs.get(index - 1));
            driver.get(lobbyLink);
            $x("//*[@id=\"root\"]/div/div[1]/div[2]/label/input").sendKeys(nameArray.get(index - 1));
            $x("//*[@id=\"root\"]/div/div[1]/button").click();
            String profileImg = "/html/body/div/div/div[1]/div[2]/div/div[1]/img[" + index + "]";
            $x(profileImg).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"prompt-button\"]")));
            $x("//*[@id=\"prompt-button\"]").click();
        }

        String info = "";
        String identity = "";

        for (int i = 0; i < 5; i++) {
            driver.switchTo().window(tabs.get(i));
            //start game
            if (i == 0) {
                $x("//*[@id=\"lobby-button-container\"]/button[1]").click();
            }
            js.executeScript("window.scrollTo(0, 0)");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"alert-box\"]/div/div")));
            $x("//*[@id=\"alert-box\"]/div/button").click();
            info = $x("/html/body/div/div/div[1]/div[2]/div/div/h2").getText();
            if (info.contains("LIBERAL")) {
                identity = "LIBERAL";
            } else if (info.contains("FASCIST")) {
                identity = "FASCIST";
            } else if (info.contains("HITLER")) {
                identity = "HITLER";
            }
            System.out.println(identity);
        }

    }

}