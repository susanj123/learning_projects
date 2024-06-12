package com.mycompany.tollrateui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class DashboardController {

    @Autowired
    ReactiveCircuitBreakerFactory rcbf;

    @Autowired
    WebClient.Builder wBuilder;

    @RequestMapping("/dashboard")
    public String GetTollRate(@RequestParam(defaultValue = "1000") Integer stationId, Model m) {

        // WebClient client = WebClient.create();
        ReactiveCircuitBreaker rcb = rcbf.create("tollrate-cb");

        // TollRate rate =
        // client.get()
        // wBuilder.build().get()
        Mono<TollRate> rate = rcb.run(
                wBuilder.build().get()
                        .uri("http://tollrate-service/tollrateslow/" + stationId)
                        .retrieve()
                        .bodyToMono(TollRate.class),
                Throwable -> getDefaultTollRates());
        // .block();

        System.out.println("stationId: " + stationId);
        m.addAttribute("rate", rate.block());
        return "dashboard";
    }

    Mono<TollRate> getDefaultTollRates() {

        System.out.println("Fallback method called");

        return Mono.just(new TollRate(0, 2.0f, ""));
    }

}
