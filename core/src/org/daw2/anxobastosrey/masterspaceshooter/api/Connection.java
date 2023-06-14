package org.daw2.anxobastosrey.masterspaceshooter.api;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    private String userId;
    private String apiToken;

    public void connect(String email, String password){
        String body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
        try {
            StringBuffer response = new StringBuffer();
            URL loginUrl = new URL("http://127.0.0.1:80/auth/login");
            HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            try(DataOutputStream dos = new DataOutputStream(conn.getOutputStream())){
                dos.writeBytes(body);
            }
            try(BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                String line;
                while ((line = bf.readLine()) != null){
                    response.append(line);
                }
            }
            JsonValue value = new JsonReader().parse(response.toString());
            this.apiToken = value.getString("access_token");
            this.userId = value.get("user").getString("id");
            conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void store(int score){
        if(this.apiToken != null){
            String body = "{\"id\":\"" + this.userId + "\", \"score\":\"" + score + "\", \"date\":\"" + (long)(System.currentTimeMillis()/1000) + "\"}";
            try {
                URL loginUrl = new URL("http://127.0.0.1:80/score/store");
                HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + this.apiToken);
                try(DataOutputStream dos = new DataOutputStream(conn.getOutputStream())){
                    dos.writeBytes(body);
                }
                try(BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                    String line;
                    while ((line = bf.readLine()) != null){
                        System.out.println(line);
                    }
                }
                conn.disconnect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
