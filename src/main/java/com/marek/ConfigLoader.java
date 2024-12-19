package com.marek;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private String login;
    private String password;
    private String driverType;
    private String gatewayAddress;

    public void load(String configPath) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(configPath)) {
            properties.load(input);
            login = properties.getProperty("login").trim();
            password = properties.getProperty("password").trim();
            driverType = properties.getProperty("driver").trim();
            gatewayAddress = properties.getProperty("gateway_address");

            if (gatewayAddress == null || gatewayAddress.isEmpty()) {
                System.out.println("Nie znaleziono adresu bramki w konfiguracji. Rozpoczynam wykrywanie...");
                gatewayAddress = GatewayDiscovery.discoverGateway();
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas ładowania konfiguracji: " + e.getMessage());
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverType() {
        return driverType;
    }

    public String getGatewayAddress() {
        return gatewayAddress;
    }
}
