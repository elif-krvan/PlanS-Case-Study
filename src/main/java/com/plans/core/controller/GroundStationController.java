package com.plans.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plans.core.response.Response;
import com.plans.core.service.GroundStationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("station")
public class GroundStationController {
    private final GroundStationService groundStation;

    @PostMapping
    public ResponseEntity<Object> simulateDownload() throws Exception {
        groundStation.downloadData();

        return Response.create("Records are downloaded", HttpStatus.OK);      
    }
}
