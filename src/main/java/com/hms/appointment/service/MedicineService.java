package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.exception.HmsException;

public interface MedicineService {

    public Long saveMedicine(MedicineDTO medicineDTO) throws HmsException;

    public List<MedicineDTO> saveAllMedicines(List<MedicineDTO> medicineDTO) throws HmsException;

    public List<MedicineDTO> getAllMedicinesByPrescriptionId(Long prescriptionId) throws HmsException;

}
