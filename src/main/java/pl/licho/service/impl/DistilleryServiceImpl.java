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
            sB.append(System.lineSeparator()).append(ldt.format(fmt)).append(",").append(data.getLevel1()).append(",").append(data.getLevel2()).append(",").append(data.getLevel3()).append(",").append(data.getLevel4());
        }
        distilleryDataDto.setChart(sB.toString());
        return distilleryDataDto;
    }
}
