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

    private static int lastRecordsNumber = 5;

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

        distilleryDataDto.setChart(getAllData(temperatureSensorsDtos));
        distilleryDataDto.setLimitedChart(getLimitedData(temperatureSensorsDtos, lastRecordsNumber));
        return distilleryDataDto;
    }

    @Override
    public String getLimitedData() {
        List<TemperatureSensorsDto> temperatureSensorsDtos = temperatureSensorsService.getTemperatureSensorsData();
        int sizeOfTheSet = temperatureSensorsDtos.size();
        if (sizeOfTheSet == 0) {
            return null;
        }
        return getLimitedData(temperatureSensorsDtos, lastRecordsNumber);
    }

    @Override
    public String getAllData() {
        List<TemperatureSensorsDto> temperatureSensorsDtos = temperatureSensorsService.getTemperatureSensorsData();
        int sizeOfTheSet = temperatureSensorsDtos.size();
        if (sizeOfTheSet == 0) {
            return null;
        }
        return getAllData(temperatureSensorsDtos);
    }

    @Override
    public TemperatureSensorsDto getTemps() {
        List<TemperatureSensorsDto> temperatureSensorsDtos = temperatureSensorsService.getTemperatureSensorsData();
        int sizeOfTheSet = temperatureSensorsDtos.size();
        if (sizeOfTheSet == 0) {
            return null;
        }

        int indexOfLastElementInList = temperatureSensorsDtos.size() - 1;
        return temperatureSensorsDtos.get(indexOfLastElementInList);
    }

    @Override
    public void updateNumberOfLastMeasurements(int number) {
        lastRecordsNumber = number;
    }

    private String getLimitedData(List<TemperatureSensorsDto> temperatureSensorsDtos, int lastRecordsNumber) {
        StringBuilder sB = new StringBuilder();
        sB.append("Czas, Poziom 1, Poziom 2, Poziom 3, Poziom 4");
        sB.append(getStringData(temperatureSensorsDtos, lastRecordsNumber));
        return sB.toString();
    }


    public String getAllData(List<TemperatureSensorsDto> temperatureSensorsDtos) {
        StringBuilder sB = new StringBuilder();
        sB.append("Czas, Poziom 1, Poziom 2, Poziom 3, Poziom 4");
        sB.append(getStringData(temperatureSensorsDtos, temperatureSensorsDtos.size()));
        return sB.toString();
    }

    private String getStringData(List<TemperatureSensorsDto> temperatureSensorsDtos, int lastRecordsNumber) {
        StringBuilder sB = new StringBuilder();

        int startPosition = 0;

        if(temperatureSensorsDtos.size() > lastRecordsNumber){
            startPosition = temperatureSensorsDtos.size() - lastRecordsNumber;
        }

        for (int i = startPosition; i < temperatureSensorsDtos.size(); i++) {
            Instant instant = temperatureSensorsDtos.get(i).getTs().toInstant();
            ZoneId zone = ZoneId.of("Europe/Berlin");
            LocalDateTime ldt = instant.atZone(zone).toLocalDateTime();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            float t1 = temperatureSensorsDtos.get(i).getLevel1();
            float t2 = temperatureSensorsDtos.get(i).getLevel2();
            float t3 = temperatureSensorsDtos.get(i).getLevel3();
            float t4 = temperatureSensorsDtos.get(i).getLevel4();

            String t1Str = "";
            String t2Str = "";
            String t3Str = "";
            String t4Str = "";

            if (t1 != -999.9f && t1 != -666.6f) {
                t1Str = Float.toString(t1);
            }
            if (t2 != -999.9f && t2 != -666.6f) {
                t2Str = Float.toString(t2);
            }
            if (t3 != -999.9f && t3 != -666.6f) {
                t3Str = Float.toString(t3);
            }
            if (t4 != -999.9f && t4 != -666.6f) {
                t4Str = Float.toString(t4);
            }
            sB.append(System.lineSeparator()).append(ldt.format(fmt)).append(",").append(t1Str).append(",").append(t2Str).append(",").append(t3Str).append(",").append(t4Str);
        }
        return sB.toString();
    }
}
