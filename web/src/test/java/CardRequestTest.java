import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardRequestTest {

    private static final String VALID_PHONE = "+79001234455";
    private static final String VALID_NAME = "Ксения Лаврова-Глинка";
    private static final String FIELD_REQUIRED = "Поле обязательно для заполнения";
    private static final String NAME_ERROR = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
    private static final String PHONE_ERROR = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

    SelenideElement form = $("form");
    SelenideElement successText = $("[data-test-id='order-success']");
    SelenideElement error = $(".input_invalid");

    @BeforeEach
    public void openUrl() {
        open("http://localhost:7777");
    }

    @Test
    public void shouldBeRequested() {
        enterName(VALID_NAME);
        enterPhone(VALID_PHONE);
        checkCheckbox();
        clickButton();

        successText.waitUntil(visible, 500);
        successText.shouldHave(matchesText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldNotBeRequestedWithoutName() {
        enterPhone(VALID_PHONE);
        checkCheckbox();
        clickButton();

        error.shouldHave(matchesText(FIELD_REQUIRED));
    }

    @Test
    public void shouldNotBeRequestedWithoutPhone() {
        enterName(VALID_NAME);
        checkCheckbox();
        clickButton();

        error.shouldHave(matchesText(FIELD_REQUIRED));
    }

    @Test
    public void shouldNotBeRequestedWithoutAgreement() {
        enterName(VALID_NAME);
        enterPhone(VALID_PHONE);
        clickButton();

        form.$("[data-test-id='agreement']").shouldHave(cssClass("input_invalid"));
    }

    @Test
    public void shouldNotBeRequestedEngName() {
        enterName("Kseniya Lavrova-Glinka");
        enterPhone(VALID_PHONE);
        checkCheckbox();
        clickButton();

        error.shouldHave(matchesText(NAME_ERROR));
    }

    @Test
    public void shouldNotBeRequestedSymbolName() {
        enterName("Ксения-$$@#$%^&%Лаврова");
        enterPhone(VALID_PHONE);
        checkCheckbox();
        clickButton();

        error.shouldHave(matchesText(NAME_ERROR));
    }

    @Test
    public void shouldNotBeRequestedUnvalidPhone() {
        enterName(VALID_NAME);
        enterPhone("89001234455");
        checkCheckbox();
        clickButton();

        error.shouldHave(text(PHONE_ERROR));
    }

    private void enterName(String name) {
        form.$(By.xpath("//input[@name = 'name']")).sendKeys(name);
    }

    private void enterPhone(String phone) {
        form.$(By.xpath("//input[@name = 'phone']")).sendKeys(phone);
    }

    private void checkCheckbox() {
        form.$(".checkbox__box").click();
    }

    private void clickButton() {
        form.$(".button").click();
    }
}
