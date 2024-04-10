package com.plans.core.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.antlr.v4.runtime.RecognitionException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.plans.core.model.DeviceRecord;
import com.plans.core.model.IoTDevice;
import com.plans.core.repository.IDeviceRepository;
import com.plans.core.repository.IRecordRepository;
import com.plans.core.response.RDevice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GroundStationService {

    private static final int HOURS_IN_DAY = 24;    
    private static final LocalDate START_DAY = LocalDate.now().minusDays(50);
    private static final int NUM_OF_DAYS = 2;

    private final IDeviceRepository deviceRepository;
    private final IRecordRepository recordRepository;

    // Send data to queue
    public void simulateDownload() {
    }

    public void downloadData() {
        try {
            // Retrieve all devices from database
            List<IoTDevice> devices = deviceRepository.findAll();

            for (IoTDevice device : devices) {
                List<DeviceRecord> record = generateRandomRecords(device);
                System.out.println(record);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<DeviceRecord> generateRandomRecords(IoTDevice device) {
        List<DeviceRecord> records = new ArrayList<>();

        for (int i = 0; i < NUM_OF_DAYS; i++) {
            for (int j = 0; j < HOURS_IN_DAY; j++) {
                double temperature = getRandomDoubleInRange(-100, 100);
                double humidity = getRandomDoubleInRange(0, 100);
                double pressure = getRandomDoubleInRange(800, 1200);

                LocalDateTime timestamp = LocalDateTime.of(START_DAY.plusDays(i), LocalTime.of(j, 0, 0));

                // Create and add the DeviceRecord object to the list
                records.add(new DeviceRecord(device, timestamp, temperature, humidity, pressure));
            }            
        }

        return records;
    }

    private static double getRandomDoubleInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
