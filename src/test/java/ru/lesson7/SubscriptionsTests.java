package ru.lesson7;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.request.RequestModel;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class SubscriptionsTests {

    @Test
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
                .extract()
                .response();
    }

    @Test
    public void postCreateTinkoffSubscription(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("instrument_id", "TCS_SPBXM");
        jsonAsMap.put("sec_name", "TCS");
        jsonAsMap.put("sec_type", "equity");
        jsonAsMap.put("price_alert", 100);

        TradeInfo tradeInfo = given().spec(RequestModel.getRequestSpecification())
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
                .as(TradeInfo.class);
        System.out.println("Result: " + tradeInfo);
    }

    @Test
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
    public void deleteFailClientSubscriptionTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "mamysheva")
                .pathParam("subscription_id", "4c30555c-ec0f-11e8-926a-02a0d1954eca")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .when()
                .delete("/contacts/{siebel_id}/subscriptions/{subscription_id}")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void deleteFailIdSubscriptionTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", "t.mamysheva")
                .pathParam("subscription_id", "3654")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .when()
                .delete("/contacts/{siebel_id}/subscriptions/{subscription_id}")
                .then()
                .assertThat()
                .statusCode(500);
    }

}
