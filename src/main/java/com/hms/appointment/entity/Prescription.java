package com.hms.appointment.entity;

import java.time.LocalDate;

import com.hms.appointment.dto.PrescriptionDTO;

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
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDate prescriptionDate;
    private String notes;

    public Prescription(Long id) {
        this.id = id;
    }

    public PrescriptionDTO toDTO() {
        return new PrescriptionDTO(
                this.id,
                this.patientId,
                this.doctorId,
                this.appointment != null ? this.appointment.getId() : null,
                this.prescriptionDate,
                this.notes,
                null);
    }

}
