package pl.licho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    private static final Logger LOG = LoggerFactory.getLogger(ThymeleafController.class);

    @GetMapping("/")
    public String getIndex(ModelMap map) {
        return "index";
    }
}
