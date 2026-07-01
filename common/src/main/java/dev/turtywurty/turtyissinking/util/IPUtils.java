package dev.turtywurty.turtyissinking.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class IPUtils {
    private static final long IP_CACHE_REVALIDATE_INTERVAL_MS = 1000 * 60 * 5; // 5 minutes

    private static final String[] CHECK_IP_URLS = {
            "https://checkip.amazonaws.com/",
            "https://ipv4.icanhazip.com/",
            "https://myexternalip.com/raw",
            "https://ipecho.net/plain"
    };

    private static final String GEO_LOCATE_API = "http://ip-api.com/line/%s?fields=countryCode";

    private static volatile String cachedExternalIP;
    private static volatile long lastExternalIPValidation;
    private static final Map<String, String> cachedCountryCodes = new HashMap<>();
    private static final Map<String, Long> lastCountryCodeValidations = new HashMap<>();

    public static String getExternalIP() {
        long currentTime = System.currentTimeMillis();
        if (cachedExternalIP != null && currentTime - lastExternalIPValidation < IP_CACHE_REVALIDATE_INTERVAL_MS)
            return cachedExternalIP;

        synchronized (IPUtils.class) {
            currentTime = System.currentTimeMillis();
            if (cachedExternalIP != null && currentTime - lastExternalIPValidation < IP_CACHE_REVALIDATE_INTERVAL_MS)
                return cachedExternalIP;

            String externalIP = fetchExternalIP();
            lastExternalIPValidation = currentTime;
            if (externalIP != null)
                cachedExternalIP = externalIP;

            return cachedExternalIP;
        }
    }

    private static String fetchExternalIP() {
        for (String url : CHECK_IP_URLS) {
            try {
                URL checkIPUrl = new URI(url).toURL();
                HttpURLConnection connection = (HttpURLConnection) checkIPUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String ip = reader.readLine();
                        if (ip != null && !ip.isEmpty())
                            return ip.trim();
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    public static String getCountryCode(String ip) {
        if (ip == null)
            return null;

        long currentTime = System.currentTimeMillis();
        synchronized (IPUtils.class) {
            String cachedCountryCode = cachedCountryCodes.get(ip);
            Long lastCountryCodeValidation = lastCountryCodeValidations.get(ip);
            if (cachedCountryCode != null && lastCountryCodeValidation != null &&
                    currentTime - lastCountryCodeValidation < IP_CACHE_REVALIDATE_INTERVAL_MS)
                return cachedCountryCode;

            String countryCode = fetchCountryCode(ip);
            lastCountryCodeValidations.put(ip, currentTime);
            if (countryCode != null)
                cachedCountryCodes.put(ip, countryCode);

            return cachedCountryCodes.get(ip);
        }
    }

    private static String fetchCountryCode(String ip) {
        try {
            URL geoLocateUrl = new URI(String.format(GEO_LOCATE_API, ip)).toURL();
            HttpURLConnection connection = (HttpURLConnection) geoLocateUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String countryCode = reader.readLine();
                    if (countryCode != null && !countryCode.isEmpty())
                        return countryCode.trim();
                }
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    public static String getCountryCodeFromExternalIP() {
        String externalIP = getExternalIP();
        if (externalIP != null)
            return getCountryCode(externalIP);

        return null;
    }

    private IPUtils() {
    }
}
