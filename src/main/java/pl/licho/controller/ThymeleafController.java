package pl.licho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.licho.dto.DistilleryDataDto;
import pl.licho.service.DistilleryService;

import java.text.SimpleDateFormat;

@Controller
public class ThymeleafController {

    private static final Logger LOG = LoggerFactory.getLogger(ThymeleafController.class);

    @Autowired
    DistilleryService distilleryService;

    @GetMapping("/")
    public String getIndex(ModelMap map) {
        DistilleryDataDto distilleryDataDto = distilleryService.getDistilleryData();

        map.addAttribute("level1", distilleryDataDto.getLevel1());
        map.addAttribute("level2", distilleryDataDto.getLevel2());
        map.addAttribute("level3", distilleryDataDto.getLevel3());
        map.addAttribute("level4", distilleryDataDto.getLevel4());
        map.addAttribute("last_snap", new SimpleDateFormat("HH:mm:ss").format(distilleryDataDto.getTs()));
        map.addAttribute("chart", distilleryDataDto.getChart());
        return "index";
    }
}
