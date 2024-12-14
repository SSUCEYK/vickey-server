package com.example.vickey.kakaopay;

import com.example.vickey.SubscriptionType;
import com.example.vickey.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class KakaoController {

    private final String baseUrl = "http://3.37.105.22:8080";

    private final SubscriptionService subscriptionService;
    @Value("${kakao.secretKey}")
    private String secretKey;
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping({"/ready"})
    public String readyPayment(HttpSession session, @RequestParam("uid") String uid, @RequestParam("subscriptionType") String subscriptionTypeName) {
        SubscriptionType subscriptionType = SubscriptionType.valueOf(subscriptionTypeName);
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + this.secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap();
        requestBody.put("cid", "TC0ONETIME");
        requestBody.put("partner_order_id", "1");
        requestBody.put("partner_user_id", uid);
        requestBody.put("item_name", subscriptionType.getName());
        requestBody.put("quantity", "1");
        requestBody.put("total_amount", subscriptionType.getPrice());
        requestBody.put("vat_amount", "0");
        requestBody.put("tax_free_amount", "0");
        requestBody.put("approval_url", baseUrl+"/kakao/success");
        requestBody.put("fail_url", baseUrl+"/fail");
        requestBody.put("cancel_url", baseUrl+"/cancel");
        HttpEntity<Map<String, Object>> entity = new HttpEntity(requestBody, headers);

        try {
            ResponseEntity<Map> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, Map.class, new Object[0]);
            Map body = (Map)response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(body);
            System.out.println(prettyJson);
            String mobileUrl = (String)body.get("next_redirect_mobile_url");
            String tid = (String)body.get("tid");
            session.setAttribute("tid", tid);
            session.setAttribute("uid", uid);
            session.setAttribute("subscriptionType", subscriptionType.name());
            return "redirect:" + mobileUrl;
        } catch (Exception var16) {
            System.out.println(var16.getMessage());
            throw new RuntimeException(var16);
        }
    }

    @GetMapping({"/kakao/success"})
    public String approvePayment(@RequestParam("pg_token") String pgToken, HttpSession session) {
        System.out.println("pay completed");
        String tid = (String)session.getAttribute("tid");
        String uid = (String)session.getAttribute("uid");
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + this.secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap();
        requestBody.put("cid", "TC0ONETIME");
        requestBody.put("tid", tid);
        requestBody.put("partner_order_id", "1");
        requestBody.put("partner_user_id", uid);
        requestBody.put("pg_token", pgToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity(requestBody, headers);

        try {
            ResponseEntity<Map> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, Map.class, new Object[0]);
            Map body = (Map)response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(body);
            System.out.println(prettyJson);
            String subscriptionTypeName = (String)session.getAttribute("subscriptionType");
            SubscriptionType subscriptionType = SubscriptionType.valueOf(subscriptionTypeName);
            this.subscriptionService.createOrUpdateSubscription(uid, subscriptionType);
            return "paySuccess";
        } catch (Exception var16) {
            System.out.println(var16.getMessage());
            throw new RuntimeException(var16);
        }
    }

    @Generated
    public KakaoController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
