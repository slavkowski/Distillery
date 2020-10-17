package pl.licho.service;

import pl.licho.dto.TemperatureSensorsDto;

import java.util.List;

public interface TemperatureSensorsService {
    List<TemperatureSensorsDto> getTemperatureSensorsData();
    void readTemperatures();
}
