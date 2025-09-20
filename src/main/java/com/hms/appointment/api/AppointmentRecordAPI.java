package com.hms.appointment.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.service.AppointmentRecordService;
import com.hms.appointment.service.PrescriptionService;

@RestController
@RequestMapping("/appointment/report")
@Validated
@CrossOrigin
public class AppointmentRecordAPI {

        @Autowired
        private AppointmentRecordService appointmentRecordService;

        @Autowired
        private PrescriptionService prescriptionService;

        @PostMapping("/create")
        public ResponseEntity<Long> createAppointmentReport(@RequestBody AppointmentRecordDTO appointmentRecordDTO)
                        throws HmsException {
                return new ResponseEntity<>(appointmentRecordService.createAppointmentRecord(appointmentRecordDTO),
                                HttpStatus.CREATED);
        }

        @PutMapping("/update")
        public ResponseEntity<String> updateAppointmentReport(@RequestBody AppointmentRecordDTO appointmentRecordDTO)
                        throws HmsException {
                appointmentRecordService.updateAppointmentRecord(appointmentRecordDTO);
                return new ResponseEntity<>("Appointment Report Updated.", HttpStatus.OK);
        }

        @GetMapping("/getByAppointmentId/{appointmentId}")
        public ResponseEntity<AppointmentRecordDTO> getAppointmentReportByAppointmentId(
                        @PathVariable Long appointmentId)
                        throws HmsException {
                return new ResponseEntity<>(appointmentRecordService.getAppointmentRecordByAppointmentId(appointmentId),
                                HttpStatus.OK);
        }

        @GetMapping("/getDetailsByAppointmentId/{appointmentId}")
        public ResponseEntity<AppointmentRecordDTO> getAppointmentRecordDetailsByAppointmentId(
                        @PathVariable Long appointmentId)
                        throws HmsException {
                return new ResponseEntity<>(
                                appointmentRecordService.getAppointmentRecordDetailsByAppointmentId(appointmentId),
                                HttpStatus.OK);
        }

        @GetMapping("/getById/{recordId}")
        public ResponseEntity<AppointmentRecordDTO> getAppointmentRecordById(@PathVariable Long recordId)
                        throws HmsException {
                return new ResponseEntity<>(appointmentRecordService.getAppointmentRecordById(recordId),
                                HttpStatus.OK);
        }

        @GetMapping("/getRecordsByPatientId/{patientId}")
        public ResponseEntity<List<RecordDetails>> getRecordsByPatient(@PathVariable Long patientId)
                        throws HmsException {
                return new ResponseEntity<>(appointmentRecordService.getRecordsByPatientId(patientId),
                                HttpStatus.OK);
        }

        @GetMapping("/isRecordExists/{appointmentId}")
        public ResponseEntity<Boolean> isRecordExists(@PathVariable Long appointmentId) throws HmsException {
                return new ResponseEntity<>(appointmentRecordService.isRecordExists(appointmentId), HttpStatus.OK);
        }

        @GetMapping("/getPrescriptionsByPatientId/{patientId}")
        public ResponseEntity<List<PrescriptionDetails>> getPrescriptionsByPatientId(@PathVariable Long patientId)
                        throws HmsException {
                return new ResponseEntity<>(prescriptionService.getPrescriptionsByPatientId(patientId),
                                HttpStatus.OK);
        }
}
