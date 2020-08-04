package com.vixd.servicestatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.beans.Statement;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class ServicestatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicestatusApplication.class, args);


        isAccessable("https://vix.digital", 400);



    }
    public static boolean isAccessable(String url, int timeout) {
        url = url.replaceFirst("http", "https"); // Otherwise an exception may
        // be thrown on invalid SSL
        // certificates.

        try {
        HttpURLConnection connection = (HttpURLConnection) new URL(url)
        .openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
        System.out.println("Error " + responseCode);
            var db = "jdbc:h2:mem:Servicesddb";

            try (var con = DriverManager.getConnection(db);
                 var stm = con.createStatement();
                 var rs = stm.executeQuery("Insert into VALUES(1, responseCode, 'https://vix.digital')")) {

                if (rs.next()) {

                    System.out.println(rs.getInt(1));
                }

            } catch (SQLException ex) {
                System.out.println("error");
            }

            return false;
        }
        } catch (IOException exception) {
        return false;
        }
        return true;
    }
}