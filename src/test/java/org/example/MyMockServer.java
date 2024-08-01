package org.example;

import org.junit.Test;
import org.mockserver.client.MockServerClient;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MyMockServer {

    /**
     * Inicjalizuje i wstępnie konfigurje serwer HTTP, który będzie dostarczał dane do testów na porcie 8082.
     * Serwer HTTP zwraca odpowiedź zgodną z oczekiwaniami, gdy otrzyma żądanie GET na ścieżce /test/1.
     * UWAGA: Wymaga wcześniejszego uruchomienia skryptu startMockServer.bat
     */
    @Test
    public void initMockServer() {
        MockServerClient mockServerClient = new MockServerClient("localhost", 8082);

        mockServerClient.reset();

        mockServerClient
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/test/1")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"baseURI\" : \"http://localhost:8081\"," +
                            "\"description\" : \"mock test\"}")
            );
        mockServerClient.retrieveRecordedRequests(request().withPath("/test/1"));
    }

    /**
     * Test resetuje serwer HTTP, który dostarcza dane do testów na porcie 8082.
     * UWAGA: Wymaga wcześniejszego uruchomienia skryptu startMockServer.bat
     */
    @Test
    public void resetMockServer() {
        MockServerClient mockServerClient = new MockServerClient("localhost", 8082);
        mockServerClient.reset();
    }
}
