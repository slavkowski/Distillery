package pl.licho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.licho.dto.DistilleryDataDto;
import pl.licho.dto.TemperatureSensorsDto;
import pl.licho.service.DistilleryService;
import pl.licho.service.TemperatureSensorsService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DistilleryServiceImpl implements DistilleryService {

    @Autowired
    TemperatureSensorsService temperatureSensorsService;

    @Override
    public DistilleryDataDto getDistilleryData() {

        List<TemperatureSensorsDto> temperatureSensorsDtos = temperatureSensorsService.getTemperatureSensorsData();
        int sizeOfTheSet = temperatureSensorsDtos.size();
        if (sizeOfTheSet == 0) {
            return null;
        }

        int indexOfLastElementInList = temperatureSensorsDtos.size() - 1;
        TemperatureSensorsDto temperatureSensorsDto = temperatureSensorsDtos.get(indexOfLastElementInList);

        DistilleryDataDto distilleryDataDto = new DistilleryDataDto();

        distilleryDataDto.setLevel1(temperatureSensorsDto.getLevel1());
        distilleryDataDto.setLevel2(temperatureSensorsDto.getLevel2());
        distilleryDataDto.setLevel3(temperatureSensorsDto.getLevel3());
        distilleryDataDto.setLevel4(temperatureSensorsDto.getLevel4());
        distilleryDataDto.setTs(temperatureSensorsDto.getTs());

        StringBuilder sB = new StringBuilder();
        sB.append("Czas, Poziom 1, Poziom 2, Poziom 3, Poziom 4");
        for (TemperatureSensorsDto data : temperatureSensorsDtos) {
            Instant instant = data.getTs().toInstant();
            ZoneId zone = ZoneId.of("Europe/Berlin");
            LocalDateTime ldt = instant.atZone(zone).toLocalDateTime();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            float t1  = data.getLevel1();
            float t2  = data.getLevel2();
            float t3  = data.getLevel3();
            float t4  = data.getLevel4();

            String t1Str = "";
            String t2Str = "";
            String t3Str = "";
            String t4Str = "";

            if (t1 != -999.9f && t1 != -666.6f){
                t1Str = Float.toString(t1);
            }
            if (t2 != -999.9f && t2 != -666.6f){
                t2Str = Float.toString(t2);
            }
            if (t3 != -999.9f && t3 != -666.6f){
                t3Str = Float.toString(t3);
            }
            if (t4 != -999.9f && t4 != -666.6f){
                t4Str = Float.toString(t4);
            }

            sB.append(System.lineSeparator()).append(ldt.format(fmt)).append(",").append(t1Str).append(",").append(t2Str).append(",").append(t3Str).append(",").append(t4Str);
        }
        distilleryDataDto.setChart(sB.toString());
        return distilleryDataDto;
    }
}
