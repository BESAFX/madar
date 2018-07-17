package com.besafx.app.config;

import com.besafx.app.init.Initializer;
import com.google.common.collect.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class SendSMS {

    private final static Logger log = LoggerFactory.getLogger(SendSMS.class);

    @Async("threadMultiplePool")
    public Future<String> getCredit() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String uri = new String("http://api.yamamah.com/GetCredit/{userName}/{password}");

        Map<String, String> vars = new HashMap<>();
        vars.put("userName", Initializer.company.getYamamahUserName());
        vars.put("password", Initializer.company.getYamamahPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);

        String response = restTemplate.postForObject(uri, httpEntity, String.class, vars);

        JSONObject jsonObj = new JSONObject(response);
        log.info("Object = " + jsonObj.toString());
        log.info("Credits = " + jsonObj.getJSONObject("GetCreditPostResult").getInt("Credit"));
        return new AsyncResult<>(jsonObj.toString());
    }

    @Async("threadMultiplePool")
    public Future<String> sendMessage(String mobile, String message) {
        return sendMessage(Lists.newArrayList(mobile), message);
    }

    @Async("threadMultiplePool")
    public Future<String> sendMessage(List<String> mobileList, String message) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        String uri = new String("http://api.yamamah.com/SendSMS");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        JSONObject json = new JSONObject();
        json.put("Username", Initializer.company.getYamamahUserName());
        json.put("Password", Initializer.company.getYamamahPassword());
        json.put("Tagname", "MADAR");
        json.put("RecepientNumber", String.join(",", mobileList.stream().distinct().collect(Collectors.toList())));
        json.put("VariableList", "");
        json.put("ReplacementList", "");
        json.put("Message", message);
        json.put("SendDateTime", 0);
        json.put("EnableDR", false);

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);

        String response = restTemplate.postForObject(uri, httpEntity, String.class);

        JSONObject jsonObj = new JSONObject(response);
        log.info("Message Response = " + jsonObj.getString("StatusDescription"));
        return new AsyncResult<>(jsonObj.getString("StatusDescription"));
    }
}
