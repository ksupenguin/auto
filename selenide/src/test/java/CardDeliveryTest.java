import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private static final String VALID_CITY = "Москва";
    private static final String VALID_PHONE = "+79001234455";
    private static final String VALID_NAME = "Ксения Лаврова-Глинка";
    SelenideElement form = $("form");
    SelenideElement city = form.$(By.xpath("//*[@data-test-id = 'city']//input"));
    SelenideElement notification = $("[data-test-id = 'notification']");
    SelenideElement date = form.$("[placeholder='Дата встречи']");

    private static String generateDate() {
        LocalDate date = LocalDate.now().plusDays(3);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    public void openUrl() {
        open("http://localhost:7778");
    }

    @Test
    public void shouldDelivery() {
        enterCity(VALID_CITY);
        enterDate(generateDate());
        enterName(VALID_NAME);
        enterPhone(VALID_PHONE);
        checkCheckbox();
        clickButton();

        notification.waitUntil(visible, 15000);
        notification.shouldHave(text("Встреча успешно забронирована на"));
    }

    private void enterCity(String city) {
        this.city.sendKeys(city);
    }

    private void enterName(String name) {
        form.$(By.xpath("//input[@name = 'name']")).sendKeys(name);
    }

    private void enterPhone(String phone) {
        form.$(By.xpath("//input[@name = 'phone']")).sendKeys(phone);
    }

    private void enterDate(String date) {
        this.date.clear();
        this.date.sendKeys(date);
    }

    private void checkCheckbox() {
        form.$(".checkbox__box").click();
    }

    private void clickButton() {
        form.$(".button").click();
    }
}
