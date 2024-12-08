package com.example.vickey.tosspay;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class QuartzConfig {
    public QuartzConfig() {
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }
}
