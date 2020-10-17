package pl.licho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.licho.dto.DistilleryDataDto;
import pl.licho.service.DistilleryService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Controller
public class DistilleryController {

    private static final Logger LOG = LoggerFactory.getLogger(DistilleryController.class);

    @Autowired
    DistilleryService distilleryService;

    @GetMapping("/")
    public String getIndex(ModelMap map) {
        DistilleryDataDto distilleryDataDto = distilleryService.getDistilleryData();
        if(distilleryDataDto == null){
            map.addAttribute("level1", "N/A");
            map.addAttribute("level2", "N/A");
            map.addAttribute("level3", "N/A");
            map.addAttribute("level4", "N/A");
            map.addAttribute("last_snap", new SimpleDateFormat("HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
            map.addAttribute("chart", null);
            return "index";
        }
        float t1 = distilleryDataDto.getLevel1();
        float t2 = distilleryDataDto.getLevel2();
        float t3 = distilleryDataDto.getLevel3();
        float t4 = distilleryDataDto.getLevel4();

        if(t1 != -666.6f && t1 != -999.9f){
            map.addAttribute("level1", String.format("%.1f", t1));
        }else{
            map.addAttribute("level1", "N/A");
        }
        if(t2 != -666.6f && t2 != -999.9f){
            map.addAttribute("level2", String.format("%.1f", t2));
        }else{
            map.addAttribute("level2", "N/A");
        }
        if(t3 != -666.6f && t3 != -999.9f){
            map.addAttribute("level3", String.format("%.1f", t3));
        }else{
            map.addAttribute("level3", "N/A");
        }
        if(t4 != -666.6f && t4 != -999.9f){
            map.addAttribute("level4", String.format("%.1f", t4));
        }else{
            map.addAttribute("level4", "N/A");
        }

        map.addAttribute("last_snap", new SimpleDateFormat("HH:mm:ss").format(distilleryDataDto.getTs()));
        map.addAttribute("chart", distilleryDataDto.getChart());
        return "index";
    }
}
