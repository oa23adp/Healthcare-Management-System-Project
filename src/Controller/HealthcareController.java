package Controller;
import View.*;
import Model.*;

import java.util.Date;
import java.util.ArrayList;

public class HealthcareController {
    private HealthcareModel model;
    private HealthcareView view;

    public HealthcareController(HealthcareModel model, HealthcareView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);

        initializeView();
        setupEventListeners();
    }

    private void initializeView() {

    }

    private void setupEventListeners() {
        view.setAddPatientListener(new PatientListener() {

            public void onAddPatient(
                    String firstName,
                    String lastName,
                    Date dateOfBirth,
                    String nhsNumber,
                    String gender,
                    String phoneNumber,
                    String email,
                    String address,
                    String postcode,
                    String emergencyContactName,
                    String emergencyContactNo,
                    Date dateRegistered,
                    String gpId
            ) {
                handleAddPatient(firstName, lastName, dateOfBirth, nhsNumber, gender,
                        phoneNumber, email, address, postcode,
                        emergencyContactName, emergencyContactNo,
                        dateRegistered, gpId);
            }
        });

        view.setOnCloseListener(new Runnable() {
            @Override
            public void run() {
                handleSaveData();
            }
        });

        view.setUpdateLastNameListener((patientId, newLastName) -> {
            boolean ok = model.updatePatientLastName(patientId, newLastName);
            if (ok) {
                view.showSuccessMessage("Last name updated.");
                refreshPatientsTable();
            } else {
                view.showErrorMessage("Model.Patient not found.");
            }

        });

        view.setUpdateContactInfoListener((patientId, phone, email, address, postcode) -> {
            boolean ok = model.updatePatientContactInfo(patientId, phone, email, address, postcode);
            if (ok) {
                view.showSuccessMessage("Contact info updated.");
                refreshPatientsTable();
            } else {
                view.showErrorMessage("Model.Patient not found.");
            }

        });

        view.setDeletePatientListener(patientId -> {
            boolean ok = model.deletePatient(patientId);
            if (ok) {
                view.showSuccessMessage("Model.Patient deleted.");
                refreshPatientsTable();
            } else {
                view.showErrorMessage("Model.Patient not found.");
            }

        });

        view.setAddClinicianListener((id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate) -> {
            model.addClinician(new Clinician(id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate));
            view.showSuccessMessage("Model.Clinician added successfully!");
            view.reloadCliniciansData();
        });

        view.setUpdateClinicianListener((id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate) -> {
            boolean ok = model.updateClinician(id, new Clinician(id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate));
            if (ok) {
                view.showSuccessMessage("Model.Clinician updated.");
                view.reloadCliniciansData();
            } else {
                view.showErrorMessage("Model.Clinician not found.");
            }
        });

        view.setDeleteClinicianListener(id -> {
            boolean ok = model.deleteClinician(id);
            if (ok) {
                view.showSuccessMessage("Model.Clinician deleted.");
                view.reloadCliniciansData();
            } else {
                view.showErrorMessage("Model.Clinician not found.");
            }
        });


        view.setAddPrescriptionListener((id, patientId, clinicianId, appointmentId, prescriptionDate,
                                         medicationName, dosage, frequency, durationDays, quantity,
                                         instructions, pharmacies, status, dateIssued, collectionDate) -> {

            model.addPrescription(new Prescription(
                    id, patientId, clinicianId, appointmentId, prescriptionDate,
                    medicationName, dosage, frequency, durationDays, quantity,
                    instructions, pharmacies, status, dateIssued, collectionDate
            ));

            view.showSuccessMessage("Model.Prescription added successfully!");
            view.reloadPrescriptionsData();
        });

        view.setUpdatePrescriptionListener((id, patientId, clinicianId, appointmentId, prescriptionDate,
                                            medicationName, dosage, frequency, durationDays, quantity,
                                            instructions, pharmacies, status, dateIssued, collectionDate) -> {

            boolean ok = model.updatePrescription(id, new Prescription(
                    id, patientId, clinicianId, appointmentId, prescriptionDate,
                    medicationName, dosage, frequency, durationDays, quantity,
                    instructions, pharmacies, status, dateIssued, collectionDate
            ));

            if (ok) {
                view.showSuccessMessage("Model.Prescription updated.");
                view.reloadPrescriptionsData();
            } else {
                view.showErrorMessage("Model.Prescription not found.");
            }
        });

        view.setDeletePrescriptionListener(id -> {
            boolean ok = model.deletePrescription(id);
            if (ok) {
                view.showSuccessMessage("Model.Prescription deleted.");
                view.reloadPrescriptionsData();
            } else {
                view.showErrorMessage("Model.Prescription not found.");
            }
        });

        view.setAddAppointmentListener((id, patientId, clinicianId, facilityId,
                                        appointmentDate, appointmentTime, durationMinutes,
                                        appointmentType, status, reason, notes,
                                        dateCreated, lastModified) -> {

            model.addAppointment(new Appointment(
                    id, patientId, clinicianId, facilityId,
                    appointmentDate, appointmentTime, durationMinutes,
                    appointmentType, status, reason, notes,
                    dateCreated, lastModified
            ));

            view.showSuccessMessage("Model.Appointment added successfully!");
            view.reloadAppointmentsData();
        });

        view.setUpdateAppointmentListener((id, patientId, clinicianId, facilityId,
                                           appointmentDate, appointmentTime, durationMinutes,
                                           appointmentType, status, reason, notes,
                                           dateCreated, lastModified) -> {

            boolean ok = model.updateAppointment(id, new Appointment(
                    id, patientId, clinicianId, facilityId,
                    appointmentDate, appointmentTime, durationMinutes,
                    appointmentType, status, reason, notes,
                    dateCreated, lastModified
            ));

            if (ok) {
                view.showSuccessMessage("Model.Appointment updated.");
                view.reloadAppointmentsData();
            } else {
                view.showErrorMessage("Model.Appointment not found.");
            }
        });

        view.setDeleteAppointmentListener(id -> {
            boolean ok = model.deleteAppointment(id);
            if (ok) {
                view.showSuccessMessage("Model.Appointment deleted.");
                view.reloadAppointmentsData();
            } else {
                view.showErrorMessage("Model.Appointment not found.");
            }
        });


        view.setAddReferralListener((patientId, fromClinician, toClinician,
                                     fromFacility, toFacility, referredDate,
                                     urgency, reason, summary, investigations,
                                     status, appointmentId, notes) -> {

            Referral r = new Referral(
                    null,
                    patientId,
                    fromClinician,
                    toClinician,
                    fromFacility,
                    toFacility,
                    referredDate,
                    urgency,
                    reason,
                    summary,
                    investigations,
                    status,
                    appointmentId,
                    notes,
                    new Date(),           // dateCreated
                    new Date()            // dateLastUpdated
            );

            model.addReferral(r);
            view.showSuccessMessage("Referral added.");
            view.reloadReferralsData();
        });

        view.setUpdateReferralStatusListener((referralId, newStatus) -> {
            boolean ok = model.updateReferralStatus(referralId, newStatus);
            if (ok) {
                view.showSuccessMessage("Referral status updated.");
                view.reloadReferralsData();
            } else {
                view.showErrorMessage("Referral not found.");
            }
        });

        view.setDeleteReferralListener(referralId -> {
            boolean ok = model.deleteReferral(referralId);
            if (ok) {
                view.showSuccessMessage("Referral deleted.");
                view.reloadReferralsData();
            } else {
                view.showErrorMessage("Referral not found.");
            }
        });

        view.setGenerateReferralEmailListener(referralId -> {
            model.generateReferralEmailText(referralId);
            view.showSuccessMessage("Referral email text generated in output/referrals.");
        });



    }


    //=============== Model.Patient Handlers =============

    private void handleAddPatient(String firstName, String lastName, Date dateOfBirth, String nhsNumber, String gender,
                                  String phoneNumber, String email, String address, String postCode, String emergencyContactName, String emergencyContactNo, Date dateRegistered, String gpId
    ) {

        String patientId = model.generatePatientId();

        Patient patient = new Patient(patientId, firstName, lastName, dateOfBirth, nhsNumber, gender,
                phoneNumber, email, address, postCode, emergencyContactName, emergencyContactNo, dateRegistered, gpId
        );

        model.addPatient(patient);
        view.showSuccessMessage("Model.Patient added successfully!");
        refreshPatientsTable();

    }

    private void refreshPatientsTable() {
        javax.swing.SwingUtilities.invokeLater(() -> view.reloadPatientsData());
    }

    private void refreshCliniciansTable() {
        javax.swing.SwingUtilities.invokeLater(() -> view.reloadCliniciansData());
    }

    private void refreshAppointmentsTable() {
        javax.swing.SwingUtilities.invokeLater(() -> view.reloadAppointmentsData());
    }


    // ========== Data Persistence ==========

    private void handleSaveData() {
        model.saveAllData();
    }

    // ========== Data Access Methods for View ==========

    public ArrayList<Patient> getAllPatients() {
        return model.getAllPatients();
    }

    public ArrayList<Clinician> getAllClinicians() {
        return model.getAllClinicians();
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return model.getAllPrescriptions();
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        return model.getPrescriptionById(prescriptionId);
    }

    public ArrayList<Appointment> getAllAppointments() {
        return model.getAllAppointments();
    }

    public ArrayList<Referral> getAllReferrals() {
        return model.getAllReferrals();
    }


}





