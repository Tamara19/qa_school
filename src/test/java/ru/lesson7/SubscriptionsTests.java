package ru.lesson7;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.request.RequestModel;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static ru.request.SubscriptionRequest.deleteSubscriptionRequest;

@Epic("Изучение Allure")
@Feature("Подписки Tinkoff инвестиций")
@Story("Прогон тестов")
@DisplayName("Тесты методов GET, POST, DELETE подписки инвестиций")
public class SubscriptionsTests {

    @Test
    @Link("https://fintech-trading-qa.tinkoff.ru/v1/md/docs/#/Subscriptions/md-contacts-subscription-create")
    @Issue("TSГ-1")
    @TmsLink("123456789")
    @DisplayName("Создание подписки Apple")
    @Description("Позитивный сценарий запроса на создание Apple подписки по siebel_id " +
            "клиента с помощью отправки параметров подписки в теле запроса и проверка кода, instrument_id, status в ответе.")
    public void postCreateAppleSubscription(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("instrument_id", "AAPL_SPBXM");
        jsonAsMap.put("sec_name", "AAPL");
        jsonAsMap.put("sec_type", "equity");
        jsonAsMap.put("price_alert", 150);

        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .body(jsonAsMap)
                .when()
                .post("/contacts/{siebel_id}/subscriptions")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200)
                .body("instrument_id", Matchers.equalTo("AAPL_SPBXM"))
                .body("status", Matchers.equalTo("active"));
    }

    @Test
    @Link("https://fintech-trading-qa.tinkoff.ru/v1/md/docs/#/Subscriptions/md-contacts-subscription-create")
    @Issue("TSГ-2")
    @TmsLink("123456790")
    @DisplayName("Создание подписки Tinkoff.ru")
    @Description("Позитивный сценарий запроса на создание Tinkoff.ru подписки по siebel_id клиента с помощью отправки " +
            "параметров подписки в теле запроса и проверка в теле ответа параметра instrument_id с выводом всего ответа запроса.")
    public void postCreateTinkoffSubscription(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("instrument_id", "TCS_SPBXM");
        jsonAsMap.put("sec_name", "TCS");
        jsonAsMap.put("sec_type", "equity");
        jsonAsMap.put("price_alert", 100);

        SubscriptionInfo subscriptionInfo = given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .body(jsonAsMap)
                .when()
                .post("/contacts/{siebel_id}/subscriptions")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200)
                .body("instrument_id", Matchers.equalTo("TCS_SPBXM"))
                .extract()
                .as(SubscriptionInfo.class);
        System.out.println("Result: " + subscriptionInfo);
    }

    @Test
    @DisplayName("Создание несуществующей подписки по siebel_id клиента")
    @Description("Получение ошибки при попытке создания подписки по несуществующему instrument_id из тела запроса и " +
            "проверка описания ошибки ответа.")
    public void postCreateFailSubscription(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("instrument_id", "POIU");
        jsonAsMap.put("sec_name", "TCS");
        jsonAsMap.put("sec_type", "equity");
        jsonAsMap.put("price_alert", 200);

        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .body(jsonAsMap)
                .when()
                .post("/contacts/{siebel_id}/subscriptions")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(400)
                .body("error", Matchers.equalTo("instrument not found"));
    }

    @Test
    @DisplayName("Получение всех подписок клиента по siebel_id.")
    @Description("Позитивный сценарий запроса на получение всех подписок по  siebel_id клиента.")
    public void getSubscriptionListTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .when()
                .get("/contacts/{siebel_id}/subscriptions")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200);
    }

    @ParameterizedTest
    @MethodSource("getIdSubscription")
    @DisplayName("Удаление подписки по siebel_id клиента и идентификатору подписки subscription_id.")
    @Description("Позитивный сценарий на удаление существующей подписки по subscription_id с предусловием " +
            "создания подписки Gazprom.")
    public void deleteSubscriptionTest(String subscriptionId){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .pathParam("subscription_id", subscriptionId)
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .when()
                .delete("/contacts/{siebel_id}/subscriptions/{subscription_id}")
                .then()
                .assertThat()
                .statusCode(200);
    }

    static Stream<String> getIdSubscription(){
        Map<String, Object> jsonAsMap2 = new HashMap<>();
        jsonAsMap2.put("instrument_id", "GAZP_TQBR");
        jsonAsMap2.put("sec_name", "GAZP");
        jsonAsMap2.put("sec_type", "equity");
        jsonAsMap2.put("price_alert", 200);

        String idSubscription = given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .body(jsonAsMap2)
                .when()
                .post("/contacts/{siebel_id}/subscriptions")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        return Stream.of(idSubscription);
    }

    @Test
    @DisplayName("Удаление подписки по неправильному идентификатору клиента siebel_id и неправильной подписки subscription_id.")
    @Description("Получение ошибки при попытке удаления подписки по несуществующему siebel_id и subscription_id, " +
            "проверка описания ошибка.")
    public void deleteFailClientSubscriptionTest(){
        deleteSubscriptionRequest("mamysheva", "4c30555c-ec0f-11e8-926a-02a0d1954eca", "6f994192-e701-11e8-9f32-f2801f1b9fd1", "T-API", 404)
                .then()
                .body("error", Matchers.equalTo("could not cancel subscription: subscription not found"));
    }

    @Test
    @DisplayName("Удаление подписки по неправильному формату идентификатора подписки subscription_id.")
    @Description("Получение ошибки при попытке удаления подписки по неправильному формату subscription_id, проверка описания ошибка.")
    public void deleteFailIdSubscriptionTest(){
        String errorSubscriptionId = "3654";
        deleteSubscriptionRequest("t.mamysheva", errorSubscriptionId, "6f994192-e701-11e8-9f32-f2801f1b9fd1", "T-API", 500)
                .then()
                .body("error", Matchers.equalTo("could not cancel subscription: pq: invalid input syntax " +
                        "for uuid: \"" + errorSubscriptionId + "\""));
    }

    @Attachment("Request")
    private byte[] createAttachment(String attachment) {
        return attachment.getBytes();
    }

}
