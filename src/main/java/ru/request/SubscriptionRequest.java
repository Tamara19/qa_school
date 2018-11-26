package ru.request;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public abstract class SubscriptionRequest {
    @Step("Выполняется запрос /contacts/{siebel_id}/subscriptions/{subscription_id}")
    public static Response deleteSubscriptionRequest(String siebelId, String subscriptionId, String requestId, String systemCode, int code) {
        return given().spec(RequestModel.getRequestSpecification())
                .pathParam("siebel_id", siebelId)
                .pathParam("subscription_id", subscriptionId)
                .queryParam("request_id", requestId)
                .queryParam("system_code", systemCode)
                .delete("/contacts/{siebel_id}/subscriptions/{subscription_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(code)
                .extract().response();
    }
}
