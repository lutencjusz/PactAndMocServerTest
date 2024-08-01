package org.example;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.runner.RunWith;

/**
 * Klasa testowa, która weryfikuje, czy serwer HTTP zwraca odpowiedź zgodną z oczekiwaniami.
 * Weryfikacja odbywa się na podstawie plików zapisanych w katalogu pacts test_consumer-test_provider.json
 * UWAGA: Wymaga wcześniejszego uruchomienia serwera HTTP na porcie 8082, co oznacza uruchomienie skryptu startMockServer.bat
 * oraz uruchomienie testu initMockServer z klasy MyMockServer.
 */
@RunWith(PactRunner.class)
@Provider("test_provider")
@PactFolder("pacts")
public class MyMockServerTestByPactFile {

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8082);

    @State("test state")
    public void testState() {
        System.out.println("test state");
    }
}
