package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.dto.MonthlyVisitDTO;
import com.hms.appointment.dto.ReasonCountDTO;
import com.hms.appointment.exception.HmsException;

public interface AppointmentService {

    Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HmsException;

    void cancelAppointment(Long appointmentId) throws HmsException;

    void completeAppointment(Long appointmentId) throws HmsException;

    void rescheduleAppointment(Long appointmentId, String newDateTime) throws HmsException;

    AppointmentDTO getAppointmentDetails(Long appointmentId) throws HmsException;

    AppointmentDetails getAppointmentDetailsWithName(Long appointmentId) throws HmsException;

    List<AppointmentDetails> getAllAppointmentsByPatientId(Long patientId) throws HmsException;

    List<AppointmentDetails> getAllAppointmentsByDoctorId(Long doctorId) throws HmsException;

    List<MonthlyVisitDTO> getAppointmentCountByPatient(Long patientId) throws HmsException;

    List<MonthlyVisitDTO> getAppointmentCountByDoctor(Long doctorId) throws HmsException;

    List<MonthlyVisitDTO> getAppointmentCounts() throws HmsException;

    List<ReasonCountDTO> getReasonCountByPatient(Long patientId) throws HmsException;

    List<ReasonCountDTO> getReasonCountByDoctor(Long doctorId) throws HmsException;

    List<ReasonCountDTO> getReasonCounts() throws HmsException;

    List<AppointmentDetails> getTodaysAppointments() throws HmsException;
}
