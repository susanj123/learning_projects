package com.mycompany.fastpassui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class FastPassController {
    @Autowired
    WebClient.Builder wBuilder;

    @RequestMapping(path = "/customerdetails")
    public String getFastPassCustomerDetails(@RequestParam(defaultValue = "800") String fastpassid, Model m) {

        // WebClient client = WebClient.create();

        FastPassCustomer customer =
                // client.get()
                wBuilder.build().get()
                        .uri("http://fastpass-service/fastpass?fastpassid=" + fastpassid)
                        .retrieve()
                        .bodyToMono(FastPassCustomer.class)
                        .block();

        System.out.println("fastpassid: " + fastpassid);
        m.addAttribute("customer", customer);
        return "console";

    }

}
