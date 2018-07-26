package com.besafx.app.config;

import com.besafx.app.init.Initializer;
import com.besafx.app.util.CompanyOptions;
import com.besafx.app.util.JSONConverter;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.Future;

@Service
public class GatewaySMS {

    private final static Logger LOG = LoggerFactory.getLogger(GatewaySMS.class);

    public String sid = "ALmadar";

    @Async("threadMultiplePool")
    public Future<String> getCredit() throws JSONException {
        return new AsyncResult<>("10000");
    }

    @Async("threadMultiplePool")
    public Future<String> sendSMS(String mobile, String msg) {
        String op = "";
        try {
            CompanyOptions options = JSONConverter.toObject(Initializer.company.getOptions(), CompanyOptions.class);
            // Construct The Post Data
            String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(options.getYamamahUserName(), "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(options.getYamamahPassword(), "UTF-8");
            data += "&" + URLEncoder.encode("msisdn", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
            data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(this.sid, "UTF-8");
            data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
            data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");

            //Push the HTTP Request
            URL url = new URL("http://apps.gateway.sa/vendorsms/pushsms.aspx?");
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
            LOG.info(op);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(op);
    }

}
