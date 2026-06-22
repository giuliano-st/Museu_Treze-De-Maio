package inf.laboratorio.museutreze.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWeb {

    @GetMapping("/")
    public String home() {
        return "redirect:/obras";
    }
}
