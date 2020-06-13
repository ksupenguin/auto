import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

public class PostmanEchoTest {

    private final String body = "some data";

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://postman-echo.com")
            .log(LogDetail.ALL)
            .build();

    @Test
    public void shouldReturnDemoAccounts() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec) // со спецификацией проще (особенно когда много тестов)
                // Выполняемые действия
                .body(body)
                .when()
                .post("/post")
                // Проверки
                .then()
                .statusCode(200)
                .body(containsString(body));
    }

}
