package com.hms.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.entity.Medicine;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public Long saveMedicine(MedicineDTO medicineDTO) throws HmsException {
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public List<MedicineDTO> saveAllMedicines(List<MedicineDTO> requestList) throws HmsException {
        return ((List<Medicine>) medicineRepository.saveAll(
                requestList.stream().map(MedicineDTO::toEntity).toList())).stream().map(Medicine::toDTO).toList();

    }

    @Override
    public List<MedicineDTO> getAllMedicinesByPrescriptionId(Long prescriptionId) throws HmsException {
        return ((List<Medicine>) medicineRepository.findByPrescription_Id(prescriptionId)).stream().map(Medicine::toDTO)
                .toList();
    }

}
