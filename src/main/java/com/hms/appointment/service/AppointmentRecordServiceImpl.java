package com.hms.appointment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentRecordDTO;
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

}
