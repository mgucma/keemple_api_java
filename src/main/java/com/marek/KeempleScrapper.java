package com.marek;

import org.apache.commons.cli.*;

public class KeempleScrapper {
    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("c", "config", true, "Ścieżka do pliku konfiguracyjnego");
        options.addOption("d", "device", true, "Nazwa urządzenia");
        options.addOption("i", "index", true, "Indeks przełącznika");
        options.addOption("s", "state", true, "Stan urządzenia (1: ON, 0: OFF)");
        options.addOption("m", "manual", false, "Wyłącz tryb headless");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            // Zmieniono domyślną ścieżkę do pliku konfiguracji
            String configPath = cmd.getOptionValue("config", "src/main/resources/application.conf");
            String device = cmd.getOptionValue("device");
            int index = Integer.parseInt(cmd.getOptionValue("index", "1"));
            int state = Integer.parseInt(cmd.getOptionValue("state", "0"));
            boolean headless = !cmd.hasOption("manual");

            ConfigLoader configLoader = new ConfigLoader();
            configLoader.load(configPath);

            String gatewayAddress = configLoader.getGatewayAddress();
            if (gatewayAddress == null) {
                System.err.println(
                        "Nie znaleziono bramki. Upewnij się, że urządzenie jest włączone i podłączone do sieci.");
                System.exit(-1);
            }
            System.out.println("Adres bramki: " + gatewayAddress);

            SeleniumScrapper scrapper = new SeleniumScrapper();
            scrapper.initialize(headless);
            scrapper.performAction(device, index, state);
            scrapper.cleanup();

        } catch (ParseException e) {
            System.err.println("Błąd parsowania argumentów: " + e.getMessage());
            new HelpFormatter().printHelp("KeempleScrapper", options);
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }
}
