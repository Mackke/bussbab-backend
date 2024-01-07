package com.mk.busdemo.controller;

import com.mk.busdemo.dto.BusListResponse;
import com.mk.busdemo.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("app/v1/busdata")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BusController {

    private final BusService busService;


    @GetMapping
    public ResponseEntity<BusListResponse> getBusData() {
        return new ResponseEntity<>(new BusListResponse(busService.getBusData()), HttpStatus.OK);
    }
}
