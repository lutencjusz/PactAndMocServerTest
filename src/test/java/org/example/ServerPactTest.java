package org.example;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ServerPactTest {

    /**
     * Tworzy mock, który jest odpowiedzialna za uruchomienie serwera HTTP, który będzie dostarczał dane do testów na porcie 8081.
     */
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 8081, this);

    /**
     * Tworzy obiekt RequestResponsePact, który zawiera informacje o żądaniu, które zostanie wysłane do serwera HTTP, oraz o odpowiedzi, którą oczekujemy od serwera.
     * @param builder - obiekt PactDslWithProvider, który jest używany do tworzenia obiektu RequestResponsePact
     * @return - obiekt RequestResponsePact, który zawiera informacje o żądaniu, które zostanie wysłane do serwera HTTP, oraz o odpowiedzi, którą oczekujemy od serwera
     */
    @Pact(provider = "test_provider", consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/test/1")
                .method("GET")
                .headers("Content-Type", "application/json")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json")
                .body("{\"baseURI\" : \"http://localhost:8081\"}")
                .toPact();
    }

    /**
     * Weryfikuje, czy Pact serwer na porcie 8081 zwraca odpowiedź zgodną z oczekiwaniami.
     */
    @Test
    @PactVerification
    public void runPactServerTest() {
        RestAssured
                .given()
                .baseUri("http://localhost:8081")
                .header("Content-Type", "application/json")
                .pathParam("id", "1")
                .log().all()
                .when()
                .get("/test/{id}")
                .then()
                .assertThat()
                .log().all()
                .statusCode(200)
                .body("baseURI", equalTo("http://localhost:8081"));
    }

    /**
     * Weryfikuje, czy mock serwer netty na porcie 8082 zwraca odpowiedź zgodną z oczekiwaniami.
     * UWAGA: Wymaga wcześniejszego uruchomienia skryptu startMockServer.bat
     */
    @Test
    public void runMockServerTest() {
        // given
        RestAssured
            .given()
            .baseUri("http://localhost:8082")
            .header("Content-Type", "application/json")
            .pathParam("id", "1")
            .log().all()
            .when()
            .get("/test/{id}")
            .then()
            .assertThat()
            .log().all()
            .statusCode(200)
            .body("baseURI", equalTo("http://localhost:8081"), "description", equalTo("mock test"));
    }
}
