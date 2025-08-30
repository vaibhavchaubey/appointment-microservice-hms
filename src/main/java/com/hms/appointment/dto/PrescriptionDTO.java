package com.hms.appointment.dto;

import java.time.LocalDate;
import java.util.List;

import com.hms.appointment.entity.Appointment;
import com.hms.appointment.entity.Prescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private LocalDate prescriptionDate;
    private String notes;
    private List<MedicineDTO> medicines;

    public Prescription toEntity() {
        return new Prescription(
                this.id,
                this.patientId,
                this.doctorId,
                new Appointment(this.appointmentId),
                this.prescriptionDate,
                this.notes);
    }

}
