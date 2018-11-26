package ru.request;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static ru.EndPoints.*;

public abstract class RequestModel {
    public static RequestSpecification getRequestSpecification(){
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(USER_LOGIN);
        authScheme.setPassword(USER_PASS);
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setAuth(authScheme)
//                .addFilter(new RequestLoggingFilter())
//                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}
