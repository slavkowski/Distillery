package pl.licho.service;

import pl.licho.dto.DistilleryDataDto;
import pl.licho.dto.TemperatureSensorsDto;

public interface DistilleryService {
    DistilleryDataDto getDistilleryData();
    String getLimitedData();
    String getAllData();
    TemperatureSensorsDto getTemps();
    void updateNumberOfLastMeasurements(int number);
}
