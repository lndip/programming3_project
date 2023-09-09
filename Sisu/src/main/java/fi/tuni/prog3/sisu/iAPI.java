/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Interface for extracting data from the Sisu API.
 */
public interface iAPI {
    /**
     * Returns a JsonObject that is extracted from the Sisu API.
     * 
     * @param urlString URL for retrieving information from the Sisu API.
     * @return a JsonObject that is extracted from the Sisu API
     * @throws IOException when reading from the API fails
     */
    public static String readUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder buffer = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                buffer.append((char) c);
            }
            return buffer.toString();
        }
    };
}
