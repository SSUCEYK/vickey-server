package com.example.vickey.tosspay;


import com.example.vickey.SubscriptionType;
import com.example.vickey.service.SubscriptionService;
import com.example.vickey.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BillingJob implements Job {

    private final TossService tossService;
    private final SubscriptionService subscriptionService;

    @Autowired
    private ApplicationContext applicationContext;
    private UserService userService;

    public BillingJob(TossService tossService, SubscriptionService subscriptionService) {
        this.tossService = tossService;
        this.subscriptionService = subscriptionService;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (this.userService == null) {
            this.userService = (UserService)this.applicationContext.getBean(UserService.class);
        }

        String billingKey = context.getJobDetail().getJobDataMap().getString("billingKey");
        String customerKey = context.getJobDetail().getJobDataMap().getString("customerKey");
        String subscriptionName = context.getJobDetail().getJobDataMap().getString("subscriptionName");
        SubscriptionType subscriptionType = SubscriptionType.valueOf(subscriptionName);
        this.subscriptionService.createOrUpdateSubscription(customerKey, subscriptionType);
        this.tossService.processPaymentWithBillingKey(billingKey, customerKey, subscriptionType);
    }
}
