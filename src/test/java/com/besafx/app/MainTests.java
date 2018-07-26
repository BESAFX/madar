package com.besafx.app;

import com.besafx.app.config.DropboxManager;
import com.besafx.app.config.EmailSender;
import com.besafx.app.config.GatewaySMS;
import com.besafx.app.config.SendSMS;
import com.besafx.app.service.ContractService;
import com.besafx.app.service.CustomerService;
import com.besafx.app.util.JSONConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    private final static Logger log = LoggerFactory.getLogger(MainTests.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SendSMS sendSMS;

    @Autowired
    private GatewaySMS gatewaySMS;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CustomerService customerService;

    @Test
    public void contextLoads() throws Exception {
        log.info("Start Initializing From Main...");
        context.getBean(EmailSender.class).init();
        context.getBean(DropboxManager.class).init();
        context.getBean(JSONConverter.class).init();
        Thread.sleep(500000);

//        contractService.findAll().forEach(contract -> {
//            if(contract.getWrittenDate() != null){
//                contract.getCustomer().setRegisterDate(contract.getWrittenDate());
//            }else{
//                contract.getCustomer().setRegisterDate(contract.getDate());
//            }
//            customerService.save(contract.getCustomer());
//        });

//        Log.info(sendSMS.getCredit());

//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//
//        StringBuilder param = new StringBuilder();
//        param.append("username={username}");
//        param.append("&password={password}");
//        param.append("&message={message}");
//        param.append("&numbers={numbers}");
//        param.append("&sender={sender}");
//        param.append("&unicode=U&return=json");
//        String uri = new String("https://www.enjazsms.com/api/sendsms.php?" + param.toString());
//
//        Map<String, String> vars = new HashMap<>();
//        vars.put("username", "Anni");
//        vars.put("password", "Ahmad_316");
//        vars.put("message", "هلا بالخميس");
//        vars.put("numbers", "966590780551");
//        vars.put("sender", "Anni");
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Content-Type", "application/json");
//
//        JSONObject json = new JSONObject();
//
//        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
//
//        String response = restTemplate.postForObject(uri, httpEntity, String.class, vars);
//
//        JSONObject jsonObj = new JSONObject(response);
//        log.info(jsonObj.toString());
    }


}
