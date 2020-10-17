package pl.licho.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.licho.controller.DistilleryController;
import pl.licho.dto.TemperatureSensorsDto;
import pl.licho.service.TemperatureSensorsService;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TemperatureSensorsServiceImpl implements TemperatureSensorsService {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureSensorsServiceImpl.class);

    private static List<TemperatureSensorsDto> temperatureSensorsDtoList = new ArrayList<>();

    List<String> sensors = Arrays.asList("1", "2", "3", "4");

    @Override
    public List<TemperatureSensorsDto> getTemperatureSensorsData() {
        return temperatureSensorsDtoList;
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void readTemperatures() {
        TemperatureSensorsDto temperatureSensorsDto = new TemperatureSensorsDto();

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        temperatureSensorsDto.setTs(ts);
        LOG.warn(ts.toString());

        int i = 0;
        for (String sensor : sensors) {
            i++;
            float temperature = -999.9f;
            try {
                temperature = getTempFromD18B20(sensor);
                switch (i) {
                    case 1:
                        temperatureSensorsDto.setLevel1(temperature);
                        break;
                    case 2:
                        temperatureSensorsDto.setLevel2(temperature);
                        break;
                    case 3:
                        temperatureSensorsDto.setLevel3(temperature);
                        break;
                    case 4:
                        temperatureSensorsDto.setLevel4(temperature);
                        break;
                }

            } catch (IOException e) {
                temperatureSensorsDto.setLevel4(temperature);
                LOG.warn("IO Exception {}", e.toString());
                e.printStackTrace();
            }

        }
        temperatureSensorsDtoList.add(temperatureSensorsDto);
    }

    public float getTempFromD18B20(String sensorID) throws IOException {
//        File fileCPU = new File("/sys/class/thermal/thermal_zone0/temp");
        float temp = -666.6f;
        String line;
        String[] tempLine;
        List<String> data = new ArrayList<>(2);

//        FileReader fr = new FileReader("/sys/bus/w1/devices/" + sensorID + "/w1_slave");
//        BufferedReader br = new BufferedReader(fr);
//
//        while ((line = br.readLine()) != null) {
//            tempLine = line.split(" ");
//            data.add(tempLine[tempLine.length - 1]);
//        }
//        if (data.get(0).equals("YES")) {
//            temp = (Float.parseFloat(data.get(1).substring(2))) / 1000;
//        } else {
//            LOG.warn("Temperature sensor number: {} is not YES", sensorID);
//        }
//        br.close();

        temp = generateRandomFloat();
        return temp;
    }

    //ToDo Only for testing purposes
    private float generateRandomFloat() {
        Random r = new Random();
        float wynik;
        float rnd = 20.0f + r.nextFloat() * (120.0f - 20.0f);
        if (rnd <= 30.0f) {
            wynik = -999.9f;
        } else {
            wynik = rnd;
        }
        return wynik;
    }
}
