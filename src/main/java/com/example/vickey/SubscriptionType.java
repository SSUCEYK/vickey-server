package com.example.vickey;


public enum SubscriptionType {
    BASIC("Vickey Basic 구독", 9900),
    STANDARD("Vickey Standard 구독", 11900),
    PREMIUM("Vickey Premium 구독", 14900);

    private final String name;
    private final int price;

    private SubscriptionType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }
}
