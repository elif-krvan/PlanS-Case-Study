package com.plans.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plans.core.request.QAddClient;
import com.plans.core.request.QAddDevice;
import com.plans.core.request.QUpdateDevice;
import com.plans.core.request.QUpdateUser;
import com.plans.core.response.RDevice;
import com.plans.core.response.RRegisterClient;
import com.plans.core.response.RUser;
import com.plans.core.response.Response;
import com.plans.core.service.ClientService;
import com.plans.core.service.GroundStationService;
import com.plans.core.service.IoTDeviceService;
import com.plans.core.model.User;

import jakarta.validation.Valid;
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
