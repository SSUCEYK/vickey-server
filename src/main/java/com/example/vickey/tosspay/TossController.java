package com.example.vickey.tosspay;


import com.example.vickey.SubscriptionType;
import com.example.vickey.service.SubscriptionService;
import com.example.vickey.service.UserService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TossController {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(TossController.class);
    private final TossService tossService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @GetMapping({"/success"})
    public String success(@RequestParam("authKey") String authKey, @RequestParam("customerKey") String customerKey, @RequestParam("subscriptionTypeName") String subscriptionTypeName, Model model) {
        SubscriptionType subscriptionType = SubscriptionType.valueOf(subscriptionTypeName);

        try {
            String billingKey = this.tossService.issueBillingKey(authKey, customerKey);
            this.tossService.processPaymentWithBillingKey(billingKey, customerKey, subscriptionType);
            this.subscriptionService.createOrUpdateSubscription(customerKey, subscriptionType);
            this.tossService.scheduleNextBilling(billingKey, customerKey, subscriptionType, 31);
            return "paySuccess";
        } catch (Exception var7) {
            model.addAttribute("message", var7.getMessage());
            return "payFail";
        }
    }

    @Generated
    public TossController(TossService tossService, SubscriptionService subscriptionService, UserService userService) {
        this.tossService = tossService;
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }
}
