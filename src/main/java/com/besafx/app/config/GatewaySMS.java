package com.besafx.app.config;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
public class GatewaySMS {
    public String username = "madar";

    public String password = "147896325Zxc";

    public String senderId = "MADAR";

    public String returnType = "string";

    public GatewaySMS() {

    }

    public GatewaySMS(String u, String p, String s) {
        this.username = u;
        this.password = p;
        this.senderId = s;
    }

    public GatewaySMS(String u, String p, String s, String r) {
        this.username = u;
        this.password = p;
        this.senderId = s;
        this.returnType = r;
    }

    public void setReturnType(String r) {
        this.returnType = r;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setSenderId(String s) {
        this.senderId = s;
    }

    @Async("threadMultiplePool")
    public String sendSMS(String phonenumber, String msg) {
        String op = "";
        try {
            // Construct The Post Data
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(this.username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(this.password, "UTF-8");
            data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
            data += "&" + URLEncoder.encode("numbers", "UTF-8") + "=" + URLEncoder.encode(phonenumber, "UTF-8");
            data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(this.senderId, "UTF-8");
            data += "&" + URLEncoder.encode("unicode", "UTF-8") + "=" + URLEncoder.encode("e", "UTF-8");
            data += "&" + URLEncoder.encode("return", "UTF-8") + "=" + URLEncoder.encode(this.returnType, "UTF-8");

            //Push the HTTP Request
            URL url = new URL("https://sms.gateway.sa/api/sendsms.php?");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            //Read The Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                op += line;
            }
            wr.close();
            rd.close();
            System.out.println(op);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return op;

    }

}
