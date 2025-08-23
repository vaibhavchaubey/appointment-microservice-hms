package com.hms.appointment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.entity.Appointment;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    @Query("SELECT new com.hms.appointment.dto.AppointmentDetails(a.id, a.patientId, null, null, null, a.doctorId, null, null, null, a.appointmentTime, a.status, a.reason, a.notes)  FROM Appointment a WHERE a.patientId = ?1")
    List<AppointmentDetails> findAllByPatientId(Long patientId);

}
