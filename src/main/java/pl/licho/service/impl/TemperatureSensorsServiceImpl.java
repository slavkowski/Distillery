package pl.licho.service.impl;

import org.springframework.stereotype.Service;
import pl.licho.dto.TemperatureSensorsDto;
import pl.licho.service.TemperatureSensorsService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemperatureSensorsServiceImpl implements TemperatureSensorsService {
    private static List<TemperatureSensorsDto> temperatureSensorsDtoList = new ArrayList<>();

    @Override
    public List<TemperatureSensorsDto> getTemperatureSensorsData() {
        for (int i = 0; i<100;i++){
            TemperatureSensorsDto dto = new TemperatureSensorsDto();
            dto.setLevel1(23.4f + i);
            dto.setLevel2(53.4f+ i);
            dto.setLevel3(83.4f+ i);
            dto.setLevel4(93.4f+ i);
            dto.setTs(new Timestamp(1602942705 + i * 60000));
            temperatureSensorsDtoList.add(dto);
        }

        return temperatureSensorsDtoList;
    }
}
