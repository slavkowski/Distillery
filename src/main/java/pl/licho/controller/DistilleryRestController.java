package pl.licho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.licho.service.DistilleryService;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class DistilleryRestController {
    private static final Logger LOG = LoggerFactory.getLogger(DistilleryRestController.class);

    @Autowired
    DistilleryService distilleryService;

    @GetMapping("/update/{number}")
    public ResponseEntity<String> updateLastMeasurements(@PathVariable int number){
        distilleryService.updateNumberOfLastMeasurements(number);
        return new ResponseEntity<>("Data has been updated", HttpStatus.OK);
    }
    @GetMapping("/get/limited")
    public ResponseEntity<String> getLimitedData(){
        String jsonResponse = distilleryService.getLimitedData();
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<String> getAll(){
        String jsonResponse = distilleryService.getAllData();
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
