package com.example.vickey.tosspay;


import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.example.vickey.SubscriptionType;
import lombok.Generated;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TossService {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(TossService.class);
    @Value("${toss.secretKey}")
    private String secretKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Scheduler scheduler;

    public String issueBillingKey(String authKey, String customerKey) {
        String url = "https://api.tosspayments.com/v1/billing/authorizations/issue";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Base64.Encoder var10000 = Base64.getEncoder();
        String var10001 = this.secretKey + ":";
        String authHeader = "Basic " + var10000.encodeToString(var10001.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", authHeader);
        Map<String, String> requestBody = new HashMap();
        requestBody.put("authKey", authKey);
        requestBody.put("customerKey", customerKey);
        HttpEntity<Map<String, String>> entity = new HttpEntity(requestBody, headers);

        try {
            ResponseEntity<Map> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, Map.class, new Object[0]);
            Map<String, Object> responseBody = (Map)response.getBody();
            String billingKey = (String)responseBody.get("billingKey");
            log.info("빌링키 발급 성공: {}", billingKey);
            return billingKey;
        } catch (Exception var11) {
            log.error("빌링키 발급 실패: {}", var11.getMessage());
            throw new RuntimeException("Billing Key issuance failed.");
        }
    }

    public void processPaymentWithBillingKey(String billingKey, String customerKey, SubscriptionType subscriptionType) {
        String url = "https://api.tosspayments.com/v1/billing/" + billingKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Base64.Encoder var10000 = Base64.getEncoder();
        String var10001 = this.secretKey + ":";
        String authHeader = "Basic " + var10000.encodeToString(var10001.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", authHeader);
        Map<String, Object> requestBody = new HashMap();
        requestBody.put("customerKey", customerKey);
        requestBody.put("amount", subscriptionType.getPrice());
        requestBody.put("orderId", UUID.randomUUID().toString());
        requestBody.put("orderName", subscriptionType.getName());
        HttpEntity<Map<String, Object>> entity = new HttpEntity(requestBody, headers);

        try {
            ResponseEntity<Map> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, Map.class, new Object[0]);
            log.info("결제 성공: {}", response.getBody());
        } catch (Exception var10) {
            log.error("결제 실패: {}", var10.getMessage());
        }

    }

    public void scheduleNextBilling(String billingKey, String customerKey, SubscriptionType subscriptionType, int days) {
        try {
            String uniqueJobName = "billingJob-" + String.valueOf(UUID.randomUUID());
            JobDetail jobDetail = JobBuilder.newJob(BillingJob.class).withIdentity(uniqueJobName, billingKey).usingJobData("billingKey", billingKey).usingJobData("customerKey", customerKey).usingJobData("subscriptionName", subscriptionType.name()).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("billingTrigger-" + String.valueOf(UUID.randomUUID()), billingKey).startAt(Date.from(Instant.now().plusMillis(TimeUnit.DAYS.toMillis((long)days)))).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(TimeUnit.DAYS.toMillis((long)days)).repeatForever()).build();
            this.scheduler.scheduleJob(jobDetail, trigger);
            log.info("다음 결제가 {}일 후에 스케줄되었습니다.", days);
        } catch (SchedulerException var8) {
            log.error("스케줄 등록 실패: {}", var8.getMessage());
        }

    }

    @Generated
    public TossService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
