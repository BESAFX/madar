package com.besafx.app.rest;

import com.besafx.app.config.SendSMS;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/api/sms/")
public class SmsRest {

    private final static Logger log = LoggerFactory.getLogger(SmsRest.class);

    @Autowired
    private SendSMS sendSMS;

    @GetMapping(value = "getCredit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getCredit() throws JSONException, ExecutionException, InterruptedException {
        return sendSMS.getCredit().get();
    }
}
