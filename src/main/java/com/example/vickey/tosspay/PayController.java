package com.example.vickey.tosspay;


import com.example.vickey.SubscriptionType;
import com.example.vickey.service.UserService;
import lombok.Generated;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PayController {
    private final UserService userService;

    @GetMapping({"/pay"})
    public String subscribe(@RequestParam("uid") String uid, @RequestParam("subscriptionType") SubscriptionType subscriptionType, Model model) {
        System.out.println("uid: " + uid);
        System.out.println("구독 타입: " + String.valueOf(subscriptionType));
        System.out.println("이름: " + subscriptionType.getName());
        System.out.println("가격: " + subscriptionType.getPrice());
        model.addAttribute("subscriptionType", subscriptionType.name());
        model.addAttribute("uid", uid);
        return "pay";
    }

    @GetMapping({"/toss"})
    public String getTossPage(@RequestParam("uid") String uid, @RequestParam("subscriptionType") String subscriptionTypeName, Model model) {
        SubscriptionType subscriptionType = SubscriptionType.valueOf(subscriptionTypeName);
        System.out.println("uid: " + uid);
        System.out.println("구독 타입: " + String.valueOf(subscriptionType));
        System.out.println("이름: " + subscriptionType.getName());
        System.out.println("가격: " + subscriptionType.getPrice());
        model.addAttribute("subscriptionName", subscriptionType.name());
        model.addAttribute("subscriptionPrice", subscriptionType.getPrice());
        model.addAttribute("uid", uid);
        return "toss";
    }

    @Generated
    public PayController(UserService userService) {
        this.userService = userService;
    }

    public static class SubscriptionRequest {
        private String uid;
        private SubscriptionType subscriptionType;

        public SubscriptionRequest() {
        }

        @Generated
        public String getUid() {
            return this.uid;
        }

        @Generated
        public SubscriptionType getSubscriptionType() {
            return this.subscriptionType;
        }

        @Generated
        public void setUid(String uid) {
            this.uid = uid;
        }

        @Generated
        public void setSubscriptionType(SubscriptionType subscriptionType) {
            this.subscriptionType = subscriptionType;
        }
    }
}
