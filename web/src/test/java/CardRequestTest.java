import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardRequestTest {

    SelenideElement form = $("form");
    SelenideElement successText = $("[data-test-id='order-success']");

    @Test
    public void shouldBeRequested() {
        open("http://localhost:7777");
        enterName("Ксения Лаврова-Глинка");
        enterPhone("+79001234455");
        form.$(".checkbox__box").click();
        form.$(".button").click();

        successText.waitUntil(visible, 500);
        successText.shouldHave(matchesText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    private void enterName(String name) {
        form.$(By.xpath("//input[@name = 'name']")).sendKeys(name);
    }

    private void enterPhone(String phone) {
        form.$(By.xpath("//input[@name = 'phone']")).sendKeys(phone);
    }
}
