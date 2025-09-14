package com.hms.appointment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.dto.DoctorDTO;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.AppointmentRecordRepository;
import com.hms.appointment.utility.StringListConverter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentRecordServiceImpl implements AppointmentRecordService {

    private final AppointmentRecordRepository appointmentRecordRepository;
    private final PrescriptionService prescriptionService;
    private final ProfileClient profileClient;

    @Override
    public Long createAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HmsException {
        Optional<AppointmentRecord> existingRecord = appointmentRecordRepository
                .findByAppointment_Id(appointmentRecordDTO.getAppointmentId());

        if (existingRecord.isPresent()) {
            throw new HmsException("APPOINTMENT_RECORD_ALREADY_EXISTS");
        }
        appointmentRecordDTO.setCreatedAt(LocalDateTime.now());
        Long id = appointmentRecordRepository.save(appointmentRecordDTO.toEntity()).getId();

        if (appointmentRecordDTO.getPrescription() != null) {
            appointmentRecordDTO.getPrescription().setAppointmentId(appointmentRecordDTO.getAppointmentId());
            prescriptionService.savePrescription(appointmentRecordDTO.getPrescription());
        }

        return id;
    }

    @Override
    public void updateAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HmsException {
        AppointmentRecord existing = appointmentRecordRepository
                .findById(appointmentRecordDTO.getId())
                .orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"));

        existing.setNotes(appointmentRecordDTO.getNotes());
        existing.setDiagnosis(appointmentRecordDTO.getDiagnosis());
        existing.setFollowUpDate(appointmentRecordDTO.getFollowUpDate());
        existing.setSymptoms(StringListConverter.convertListToString(appointmentRecordDTO.getSymptoms()));
        existing.setTests(StringListConverter.convertListToString(appointmentRecordDTO.getTests()));

        appointmentRecordRepository.save(existing);

    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordByAppointmentId(Long appointmentId) throws HmsException {
        return appointmentRecordRepository
                .findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"))
                .toDTO();

    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HmsException {
        AppointmentRecordDTO record = appointmentRecordRepository
                .findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"))
                .toDTO();

        record.setPrescription(prescriptionService.getPrescriptionByAppointmentId(appointmentId));

        return record;
    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordById(Long recordId) throws HmsException {
        return appointmentRecordRepository
                .findById(recordId)
                .orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"))
                .toDTO();
    }

    @Override
    public List<RecordDetails> getRecordsByPatientId(Long patientId) throws HmsException {
        List<AppointmentRecord> records = appointmentRecordRepository.findByPatientId(patientId);
        if (records.isEmpty()) {
            throw new HmsException("NO_RECORDS_FOUND_FOR_PATIENT");
        }

        List<RecordDetails> recordDetails = records.stream().map(AppointmentRecord::toRecordDetails).toList();

        List<Long> doctorIds = recordDetails.stream()
                .map(RecordDetails::getDoctorId)
                .distinct()
                .toList();

        List<DoctorName> doctors = profileClient.getDoctorsById(doctorIds);

        Map<Long, String> doctorMap = doctors.stream()
                .collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));

        recordDetails.forEach(record -> {
            String doctorName = doctorMap.get(record.getDoctorId());

            if (doctorName != null) {
                record.setDoctorName(doctorName);
            } else {
                record.setDoctorName("Unknown Doctor");
            }
        });

        return recordDetails;
    }

    @Override
    public Boolean isRecordExists(Long appointmentId) throws HmsException {
        return appointmentRecordRepository.existsByAppointment_Id(appointmentId);
    }

    

}
