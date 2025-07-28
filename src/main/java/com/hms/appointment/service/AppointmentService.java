package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.exception.HmsException;

public interface AppointmentService {

    Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HmsException;

    void cancelAppointment(Long appointmentId) throws HmsException;

    void completeAppointment(Long appointmentId) throws HmsException;

    void rescheduleAppointment(Long appointmentId, String newDateTime) throws HmsException;

    AppointmentDTO getAppointmentDetails(Long appointmentId) throws HmsException;

}
