package com.hms.appointment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.AppointmentRecordRepository;
import com.hms.appointment.utility.StringListConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentRecordServiceImpl implements AppointmentRecordService {

    private final AppointmentRecordRepository appointmentRecordRepository;

    @Override
    public Long createAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HmsException {
        Optional<AppointmentRecord> existingRecord = appointmentRecordRepository
                .findByAppointment_Id(appointmentRecordDTO.getAppointmentId());

        if (existingRecord.isPresent()) {
            throw new HmsException("APPOINTMENT_RECORD_ALREADY_EXISTS");
        }

        return appointmentRecordRepository.save(appointmentRecordDTO.toEntity()).getId();

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
    public AppointmentRecordDTO getAppointmentRecordById(Long recordId) throws HmsException {
        return appointmentRecordRepository
                .findById(recordId)
                .orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"))
                .toDTO();
    }

}
