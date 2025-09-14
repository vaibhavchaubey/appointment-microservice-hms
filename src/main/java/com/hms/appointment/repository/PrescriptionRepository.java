package com.hms.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.entity.Prescription;

@Repository

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
    Optional<Prescription> findByAppointment_Id(Long appointmentId);

    List<Prescription> findAllByPatientId(Long patientId);
}
