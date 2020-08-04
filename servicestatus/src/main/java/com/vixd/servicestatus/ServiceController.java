package com.vixd.servicestatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
public class ServiceController {

    @Autowired
    private ServiceLayer Sl;

    @GetMapping("/home")
    public boolean test() {
        return isAccessable("https://vix.digital", 400);
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
    @GetMapping("/service")
    public List<Service> getServices() {
        return Sl.getServices();
    }

    @PostMapping("/service/addNew")
    public void addService(@RequestBody com.vixd.servicestatus.Service service){
      Sl.addService(service);
    }

    @PutMapping("/service/{id}/edit")
    public void updateService(@PathVariable("id") Integer id, @RequestBody com.vixd.servicestatus.Service service){
        Sl.updateService(service);
    }

    @DeleteMapping("/service/{id}/delete")
    public void deleteSerice(@PathVariable("id") Integer id){
        Sl.removeService(id);
    }
}
