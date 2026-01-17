package com.hms.appointment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.dto.MonthlyVisitDTO;
import com.hms.appointment.dto.ReasonCountDTO;
import com.hms.appointment.entity.Appointment;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    @Query("SELECT new com.hms.appointment.dto.AppointmentDetails(a.id, a.patientId, null, null, null, a.doctorId, null, null, null, a.appointmentTime, a.status, a.reason, a.notes)  FROM Appointment a WHERE a.patientId = ?1")
    List<AppointmentDetails> findAllByPatientId(Long patientId);

    @Query("SELECT new com.hms.appointment.dto.AppointmentDetails(a.id, a.patientId, null, null, null, a.doctorId, null, null, null, a.appointmentTime, a.status, a.reason, a.notes)  FROM Appointment a WHERE a.doctorId = ?1")
    List<AppointmentDetails> findAllByDoctorId(Long doctorId);

    @Query("SELECT new com.hms.appointment.dto.MonthlyVisitDTO(CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String), COUNT(a)) FROM Appointment a WHERE a.patientId = ?1 AND YEAR(a.appointmentTime) = YEAR(CURRENT_DATE) GROUP BY FUNCTION('MONTH', a.appointmentTime), CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String) ORDER BY FUNCTION('MONTH', a.appointmentTime)")
    List<MonthlyVisitDTO> countCurrentYearVisitsByPatient(Long patientId);

    @Query("SELECT new com.hms.appointment.dto.MonthlyVisitDTO(CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String), COUNT(a)) FROM Appointment a WHERE a.doctorId = ?1 AND YEAR(a.appointmentTime) = YEAR(CURRENT_DATE) GROUP BY FUNCTION('MONTH', a.appointmentTime), CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String) ORDER BY FUNCTION('MONTH', a.appointmentTime)")
    List<MonthlyVisitDTO> countCurrentYearVisitsByDoctor(Long doctorId);

    @Query("SELECT new com.hms.appointment.dto.MonthlyVisitDTO(CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String), COUNT(a)) FROM Appointment a WHERE YEAR(a.appointmentTime) = YEAR(CURRENT_DATE) GROUP BY FUNCTION('MONTH', a.appointmentTime), CAST(FUNCTION('MONTHNAME', a.appointmentTime) AS String) ORDER BY FUNCTION('MONTH', a.appointmentTime)")
    List<MonthlyVisitDTO> countCurrentYearVisits();

    @Query("SELECT new com.hms.appointment.dto.ReasonCountDTO(a.reason, COUNT(a)) FROM Appointment a WHERE a.patientId = ?1 GROUP BY a.reason")
    List<ReasonCountDTO> countReasonsByPatientId(Long patientId);
}
