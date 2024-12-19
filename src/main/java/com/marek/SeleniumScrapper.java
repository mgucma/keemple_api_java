package com.marek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumScrapper {
    private WebDriver driver;

    /**
     * Inicjalizuje WebDriver.
     *
     * @param headless Jeśli true, uruchamia przeglądarkę w trybie bezgłowym (bez
     *                 GUI).
     */
    public void initialize(boolean headless) {
        // Ustawienie ścieżki do geckodriver
        System.setProperty("webdriver.gecko.driver", "C:\\Tools\\geckodriver.exe");

        // Konfiguracja opcji Firefox
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless); // Ustawienie trybu headless, jeśli wymagane

        // Inicjalizacja WebDriver z podanymi opcjami
        driver = new FirefoxDriver(options);
    }

    /**
     * Wykonuje akcję na urządzeniu.
     *
     * @param deviceName  Nazwa urządzenia.
     * @param switchIndex Indeks przełącznika urządzenia.
     * @param state       Stan urządzenia (1 = włącz, 0 = wyłącz).
     */
    public void performAction(String deviceName, int switchIndex, int state) {
        System.out.printf("Akcja na urządzeniu: %s, przełącznik: %d, stan: %d%n",
                deviceName, switchIndex, state);
        // Dodaj logikę Selenium, np. nawigację, interakcje z UI
        // Przykładowa nawigacja:
        driver.get("http://adres-bramki-keemple"); // Adres docelowy
    }

    /**
     * Zamyka przeglądarkę i czyści zasoby WebDriver.
     */
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}
