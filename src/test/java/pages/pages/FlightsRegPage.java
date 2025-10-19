package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightsRegPage {
    SelenideElement NumberPassport, EndRegButton, MessageWrongPassport;
    String wrongNumberMessage, fieldEmptyMessage;

    public FlightsRegPage () {
        NumberPassport = $("#passportNumber");
        EndRegButton = $x("//button[text()='Завершить регистрацию']");
        MessageWrongPassport = $("#registrationMessage");

        wrongNumberMessage = "Номер паспорта должен содержать только цифры и пробелы.";
        fieldEmptyMessage = "Пожалуйста, заполните все поля.";
    }

    public void numberPassport (String numpassport) {
            NumberPassport.setValue(numpassport);
            EndRegButton.click();
    }

    public void verifyPassNumber(){
            MessageWrongPassport.shouldHave(text(wrongNumberMessage));
    }

    public void verifyFieldEmpty(){
        MessageWrongPassport.shouldHave(text(fieldEmptyMessage));
    }
    public void regAlert(){
        Alert alert = switchTo().alert();
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
    }
}