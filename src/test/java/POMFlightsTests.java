import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.FlightsFoundList;
import pages.FlightsLoginPage;
import pages.FlightsRegPage;
import pages.FlightsSearchPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
        getWebDriver().manage().window().maximize();
    }

    //Тест-кейсы
    @Test
    @DisplayName("POM-01. Неуспешный логин")
    void test01() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "wrong_stand_pass1");
        login_page.verify_wrong_username_or_password();
    }

    @Test
    @DisplayName("Pom-02. Не задана дата вылета")
    void test02() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва", "Лондон", "");
        searchPage.verifyEmptyDate();
    }

    @Test
    @DisplayName("POM-03. Не найдены рейсы")
    void test03() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Казань", "Париж", "15-12-2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifyEmptySearch();
    }

    @Test
    @DisplayName("POM-04. Регистрация - некорректно заполнен номер паспорта")
    void test04() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва", "Лондон", "15-12-2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();

        FlightsRegPage regPage = new FlightsRegPage ();
        regPage.numberPassport("Мой паспорт1");
        regPage.verifyPassNumber();
    }

    @Test
    @DisplayName("POM-05. Регистрация - успешно пройдена")
    void test05() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");
        login_page.verify_successful_login();

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Москва", "Лондон", "15-12-2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();

        FlightsRegPage regPage = new FlightsRegPage ();
        regPage.numberPassport("1234567890");
        regPage.regAlert();
    }

    @Test
    @DisplayName("POM-06. Регистрация - поле номер паспорта не заполнено")
    void test06() {
        FlightsLoginPage login_page = new FlightsLoginPage();
        login_page.login("standard_user", "stand_pass1");

        FlightsSearchPage searchPage = new FlightsSearchPage();
        searchPage.findFlights("Санкт-Петербург", "Нью-Йорк", "30-10-2025");

        FlightsFoundList flightsList = new FlightsFoundList();
        flightsList.verifySuccessfullSearch();
        flightsList.chooseFirstFlight();

        FlightsRegPage regPage = new FlightsRegPage ();
        regPage.numberPassport("");
        regPage.verifyFieldEmpty();
    }
}
