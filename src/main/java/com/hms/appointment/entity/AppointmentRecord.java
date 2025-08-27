package com.hms.appointment.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.utility.StringListConverter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppointmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private String symptoms;
    private String diagnosis;
    private String tests;
    private String notes;
    private String referral;
    private LocalDate followUpDate;
    private LocalDateTime createdAt;

    public AppointmentRecordDTO toDTO() {
        return new AppointmentRecordDTO(
                this.id,
                this.patientId,
                this.doctorId,
                this.appointment != null ? this.appointment.getId() : null,
                StringListConverter.convertStringToList(this.symptoms),
                this.diagnosis,
                StringListConverter.convertStringToList(this.tests),
                this.notes,
                this.referral,
                this.followUpDate,
                this.createdAt);
    }

}
