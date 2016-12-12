package com.javabeast.routes;

import org.springframework.stereotype.Component;

/**
 * Created by jeffreya on 12/12/2016.
 */
@Component
public class RouteTest extends RouteBuilder {

    @Autowired
    private HealthEndpoint health;

    @Override
    public void configure() {
        from("timer:trigger")
                .transform().simple("ref:myBean")
                .to("log:out");

        from("timer:status")
                .bean(health, "invoke")
                .log("Health is ${body}");
    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

