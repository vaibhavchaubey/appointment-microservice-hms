package com.hms.appointment.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.entity.Medicine;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {

    List<Medicine> findByPrescription_Id(Long prescriptionId);

}
