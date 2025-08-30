package com.hms.appointment.entity;

import com.hms.appointment.dto.MedicineDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long medicineId;
    private String dosage; // e.g., 500mg, 10ml
    private String frequency; // 1-1-1 1-0-1 etc.
    private Integer duration; // in days
    private String route; // oral, intravenous etc.
    private String type; // tablet, syrup, injection etc.
    private String instructions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;


    public MedicineDTO toDTO() {
        return new MedicineDTO(
                this.id,
                this.name,
                this.medicineId,
                this.dosage,
                this.frequency,
                this.duration,
                this.route,
                this.type,
                this.instructions,
                this.prescription != null ? this.prescription.getId() : null);
    }

}
