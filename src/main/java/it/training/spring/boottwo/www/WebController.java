package it.training.spring.boottwo.www;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("www")
public class WebController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

}
