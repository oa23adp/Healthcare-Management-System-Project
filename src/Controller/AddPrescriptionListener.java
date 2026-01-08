package Controller;

import java.util.Date;

public interface AddPrescriptionListener {
    void onAddPrescription(String id, String patientId, String clinicianId, String appointmentId,
                           Date prescriptionDate, String medicationName, String dosage, String frequency,
                           int durationDays, String quantity, String instructions, String pharmacies,
                           String status, Date dateIssued, Date collectionDate);
}
