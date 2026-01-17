package com.hms.appointment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.dto.PatientName;
import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.entity.Prescription;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineService medicineService;
    private final ProfileClient profileClient;

    @Override
    public Long savePrescription(PrescriptionDTO prescriptionDTO) throws HmsException {
        prescriptionDTO.setPrescriptionDate(LocalDate.now());
        Long prescriptionId = prescriptionRepository.save(prescriptionDTO.toEntity()).getId();

        prescriptionDTO.getMedicines().forEach(medicine -> medicine.setPrescriptionId(prescriptionId));
        medicineService.saveAllMedicines(prescriptionDTO.getMedicines());
        return prescriptionId;
    }

    @Override
    public PrescriptionDTO getPrescriptionByAppointmentId(Long appointmentId) throws HmsException {
        PrescriptionDTO prescriptionDTO = prescriptionRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new HmsException("PRESCRIPTION_NOT_FOUND")).toDTO();

        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));

        return prescriptionDTO;
    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) throws HmsException {
        PrescriptionDTO prescriptionDTO = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new HmsException("PRESCRIPTION_NOT_FOUND")).toDTO();

        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;
    }

    @Override
    public List<PrescriptionDetails> getPrescriptionsByPatientId(Long patientId) throws HmsException {
        List<Prescription> prescriptions = prescriptionRepository.findAllByPatientId(patientId);

        if (prescriptions.isEmpty()) {
            throw new HmsException("NO_PRESCRIPTIONS_FOUND_FOR_PATIENT");
        }

        List<PrescriptionDetails> prescriptionDetails = prescriptions.stream()
                .map(Prescription::toDetails)
                .toList();

        for (PrescriptionDetails prescription : prescriptionDetails) {
            prescription.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescription.getId()));
        }

        List<Long> doctorIds = prescriptionDetails.stream()
                .map(PrescriptionDetails::getDoctorId)
                .distinct()
                .toList();

        List<DoctorName> doctors = profileClient.getDoctorsById(doctorIds);

        Map<Long, String> doctorMap = doctors.stream()
                .collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));

        prescriptionDetails.forEach(prescription -> {
            String doctorName = doctorMap.get(prescription.getDoctorId());
            if (doctorName != null) {
                prescription.setDoctorName(doctorName);
            } else {
                prescription.setDoctorName("Unknown Doctor");
            }
        });

        return prescriptionDetails;

    }

    @Override
    public List<PrescriptionDetails> getAllPrescriptions() throws HmsException {
        List<Prescription> prescriptions = (List<Prescription>) prescriptionRepository.findAll();

        if (prescriptions.isEmpty()) {
            throw new HmsException("NO_PRESCRIPTIONS_FOUND_FOR_PATIENT");
        }

        List<PrescriptionDetails> prescriptionDetails = prescriptions.stream()
                .map(Prescription::toDetails)
                .toList();

        List<Long> doctorIds = prescriptionDetails.stream()
                .map(PrescriptionDetails::getDoctorId)
                .distinct()
                .toList();

        List<Long> patientIds = prescriptionDetails.stream()
                .map(PrescriptionDetails::getPatientId)
                .distinct()
                .toList();

        List<DoctorName> doctors = profileClient.getDoctorsById(doctorIds);

        List<PatientName> patients = profileClient.getPatientsById(patientIds);

        Map<Long, String> doctorMap = doctors.stream()
                .collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));

        Map<Long, String> patientMap = patients.stream()
                .collect(Collectors.toMap(PatientName::getId, PatientName::getName));

        prescriptionDetails.forEach(prescription -> {
            String doctorName = doctorMap.get(prescription.getDoctorId());
            if (doctorName != null) {
                prescription.setDoctorName(doctorName);
            } else {
                prescription.setDoctorName("Unknown Doctor");
            }
            String patientName = patientMap.get(prescription.getPatientId());
            if (patientName != null) {
                prescription.setPatientName(patientName);
            } else {
                prescription.setPatientName("Unknown Patient");
            }
        });

        return prescriptionDetails;
    }

    @Override
    public List<MedicineDTO> getMedicinesByPatientId(Long patientId) throws HmsException {
        List<Long> prescriptionIds = prescriptionRepository.findAllPrescriptionIdsByPatientId(patientId);

        return medicineService.getAllMedicinesByPrescriptionIds(prescriptionIds);
    }

}
