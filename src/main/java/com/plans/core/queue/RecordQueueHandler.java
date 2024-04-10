package com.plans.core.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.plans.core.request.QAddRecord;
import com.plans.core.service.RecordService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordQueueHandler {
    private final AmqpTemplate graundStationRecordProblemTemplate;
    private final RecordService recordService;

    @RabbitListener(queues = "${messaging.queue.station}", containerFactory = "requestQueueListener")
    public void handleMessage(QAddRecord record) {
        try {
            recordService.createRecord(record);
            log.info("Recor with device id {} is saved");
        } catch (Exception e) {
            log.error("Could not handle record queue for device {}", record.getDeviceId(), e);

            graundStationRecordProblemTemplate.convertAndSend(record);
        }
    }
}