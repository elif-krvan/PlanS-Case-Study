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
import com.plans.core.service.IoTDeviceService;
import com.plans.core.model.User;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("admin")
public class AdminController {
    private final ClientService clientService;
    private final IoTDeviceService ioTDeviceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "client")
    public ResponseEntity<Object> createClient(@Valid @RequestBody QAddClient newClient) throws Exception {
        RRegisterClient createdClient = clientService.createClient(newClient);

        return Response.create("New client is creaetd", HttpStatus.OK, createdClient);      
    }

    @GetMapping(path = "client/{username}")
    public ResponseEntity<Object> getClient(@PathVariable String username) throws Exception {
        User client = clientService.getClientByUsername(username);

        return Response.create("OK", HttpStatus.OK, client);      
    }
    
    @GetMapping(path = "client")
    public ResponseEntity<Object> getAllClients() throws Exception {
        List<RUser> clients = clientService.getClients();

        return Response.create("OK", HttpStatus.OK, clients);      
    }

    @DeleteMapping(path = "client/{username}")
    public ResponseEntity<Object> removeClient(@PathVariable String username) throws Exception {
        clientService.removeClient(username);

        return Response.create("Client is removed successfuly", HttpStatus.OK);      
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "client")
    public ResponseEntity<Object> createClient(@Valid @RequestBody QUpdateUser client) throws Exception {
        RUser updatedClient = clientService.updateClient(client);

        return Response.create("Client data is updated", HttpStatus.OK, updatedClient);      
    }    

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "device")
    public ResponseEntity<Object> createDevice(@Valid @RequestBody QAddDevice newDevice) throws Exception {
        RDevice createdDevice = ioTDeviceService.createDevice(newDevice);

        return Response.create("New device is creaetd", HttpStatus.OK, createdDevice);      
    }

    @GetMapping(path = "device/{deviceId}")
    public ResponseEntity<Object> getDeviceById(@PathVariable UUID deviceId) throws Exception {
        RDevice device = ioTDeviceService.getDeviceById(deviceId);

        return Response.create("OK", HttpStatus.OK, device);      
    }
    
    @GetMapping(path = "client/{username}/device")
    public ResponseEntity<Object> getClientDevices(@PathVariable String username) throws Exception {
        List<RDevice> devices = ioTDeviceService.getDeviceByClient(username);

        return Response.create("OK", HttpStatus.OK, devices);      
    }
    
    @GetMapping(path = "device")
    public ResponseEntity<Object> getAllDevices() throws Exception {
        List<RDevice> devices = ioTDeviceService.getAllDevices();

        return Response.create("OK", HttpStatus.OK, devices);      
    }

    @DeleteMapping(path = "device/{deviceId}")
    public ResponseEntity<Object> removeDevice(@PathVariable UUID deviceId) throws Exception {
        ioTDeviceService.removeDevice(deviceId);

        return Response.create("Device is removed successfuly", HttpStatus.OK);      
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "device")
    public ResponseEntity<Object> createClient(@Valid @RequestBody QUpdateDevice device) throws Exception {
        RDevice updatedDevice = ioTDeviceService.updateDevice(device);

        return Response.create("Client data is updated", HttpStatus.OK, updatedDevice);      
    }    
}
