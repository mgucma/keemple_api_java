package com.marek;

import java.net.*;
import java.io.*;

public class GatewayDiscovery {

    private static final int TIMEOUT = 200; // Czas oczekiwania w ms
    private static final int PORT = 8080; // Port bramki

    public static String discoverGateway() {
        String networkPrefix = getNetworkPrefix();
        if (networkPrefix == null) {
            System.err.println("Nie można wykryć sieci lokalnej.");
            return null;
        }

        for (int i = 1; i < 255; i++) {
            var ip = networkPrefix + i;
            try (var socket = new Socket()) {
                var address = new InetSocketAddress(ip, PORT);
                socket.connect(address, TIMEOUT);
                System.out.println("Bramka znaleziona pod adresem: " + ip);
                return ip;
            } catch (IOException ignored) {
                // Nie znaleziono serwera na tym adresie
            }
        }
        System.err.println("Nie znaleziono bramki w sieci lokalnej.");
        return null;
    }

    private static String getNetworkPrefix() {
        try {
            var localAddress = InetAddress.getLocalHost();
            var hostAddress = localAddress.getHostAddress();
            if (hostAddress.contains(".")) {
                return hostAddress.substring(0, hostAddress.lastIndexOf('.') + 1);
            }
        } catch (UnknownHostException e) {
            System.err.println("Błąd podczas wykrywania lokalnego adresu IP: " + e.getMessage());
        }
        return null;
    }
}
