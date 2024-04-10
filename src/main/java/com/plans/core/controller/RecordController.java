package com.plans.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plans.core.request.QFindRecordByClient;
import com.plans.core.request.QFindRecordByDevice;
import com.plans.core.response.Response;
import com.plans.core.service.RecordService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("record")
@Secured("ROLE_ADMIN")
public class RecordController {
    private final RecordService recordService;

    @GetMapping(path = "device", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchDataByDevice(@Valid @RequestBody QFindRecordByDevice searchParams) throws Exception {
        return Response.create("OK", HttpStatus.OK, 
            recordService.getRecordsByDevice(searchParams));
    }
    
    @GetMapping(path = "client", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchDataByClient(@Valid @RequestBody QFindRecordByClient searchParams) throws Exception {
        return Response.create("OK", HttpStatus.OK, 
            recordService.getRecordsByClient(searchParams));
    }
}
