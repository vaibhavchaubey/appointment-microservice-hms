package com.hms.appointment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.appointment.service.AppointmentRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/appointment")
@Validated
@CrossOrigin
public class AppointmentRecordAPI {

    @Autowired
    private AppointmentRecordService appointmentRecordService;


    
    

}
