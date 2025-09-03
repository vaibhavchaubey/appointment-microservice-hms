package com.hms.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hms.appointment.entity.Appointment;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.utility.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRecordDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private List<String> symptoms;
    private String diagnosis;
    private List<String> tests;
    private String notes;
    private String referral;
    private PrescriptionDTO prescription;
    private LocalDate followUpDate;
    private LocalDateTime createdAt;

    public AppointmentRecord toEntity() {
        return new AppointmentRecord(
                this.id,
                this.patientId,
                this.doctorId,
                new Appointment(this.appointmentId),
                StringListConverter.convertListToString(this.symptoms),
                this.diagnosis,
                StringListConverter.convertListToString(this.tests),
                this.notes,
                this.referral,
                this.followUpDate,
                this.createdAt);
    }

}
