package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightsFoundList {
    SelenideElement
            firstRegButton = $x("//button[@class='register-btn']"),
            flightsCount = $x("//div[@id='flightsCount']");


    public void chooseFirstFlight() {
        firstRegButton.click();
    }

    public void verifyEmptySearch() {
        flightsCount.shouldHave(text("Найдено рейсов: 0"));
        firstRegButton.shouldNotBe(visible);
        $x("//body").shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    public void verifySuccessfullSearch() {
        flightsCount.shouldNotHave(text("Найдено рейсов: 0"));
        //assertEquals(7, flightsList.size());
        firstRegButton.shouldBe(visible);
    }
}
