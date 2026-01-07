import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class HealthcareController {
    private HealthcareModel model;
    private HealthcareView view;

    public HealthcareController(HealthcareModel model, HealthcareView view) {
        this.model = model;
        this.view = view;



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
            ){
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
                view.showErrorMessage("Patient not found.");
            }

        });

        view.setUpdateContactInfoListener((patientId, phone, email, address, postcode) -> {
            boolean ok = model.updatePatientContactInfo(patientId, phone, email, address, postcode);
            if (ok) {
                view.showSuccessMessage("Contact info updated.");
                refreshPatientsTable();
            } else {
                view.showErrorMessage("Patient not found.");
            }

        });

        view.setDeletePatientListener(patientId -> {
            boolean ok = model.deletePatient(patientId);
            if (ok) {
                view.showSuccessMessage("Patient deleted.");
                refreshPatientsTable();
            } else {
                view.showErrorMessage("Patient not found.");
            }

        });

        view.setAddClinicianListener((id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate) -> {
            model.addClinician(new Clinician(id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate));
            view.showSuccessMessage("Clinician added successfully!");
            view.reloadCliniciansData();
        });

        view.setUpdateClinicianListener((id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate) -> {
            boolean ok = model.updateClinician(id, new Clinician(id, first, last, title, spec, gmc, phone, email, wid, wtype, status, startDate));
            if (ok) {
                view.showSuccessMessage("Clinician updated.");
                view.reloadCliniciansData();
            } else {
                view.showErrorMessage("Clinician not found.");
            }
        });

        view.setDeleteClinicianListener(id -> {
            boolean ok = model.deleteClinician(id);
            if (ok) {
                view.showSuccessMessage("Clinician deleted.");
                view.reloadCliniciansData();
            } else {
                view.showErrorMessage("Clinician not found.");
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

            view.showSuccessMessage("Prescription added successfully!");
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
                view.showSuccessMessage("Prescription updated.");
                view.reloadPrescriptionsData();
            } else {
                view.showErrorMessage("Prescription not found.");
            }
        });

        view.setDeletePrescriptionListener(id -> {
            boolean ok = model.deletePrescription(id);
            if (ok) {
                view.showSuccessMessage("Prescription deleted.");
                view.reloadPrescriptionsData();
            } else {
                view.showErrorMessage("Prescription not found.");
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

            view.showSuccessMessage("Appointment added successfully!");
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
                view.showSuccessMessage("Appointment updated.");
                view.reloadAppointmentsData();
            } else {
                view.showErrorMessage("Appointment not found.");
            }
        });

        view.setDeleteAppointmentListener(id -> {
            boolean ok = model.deleteAppointment(id);
            if (ok) {
                view.showSuccessMessage("Appointment deleted.");
                view.reloadAppointmentsData();
            } else {
                view.showErrorMessage("Appointment not found.");
            }
        });




    }


    //=============== Patient Handlers =============

    private void handleAddPatient(String firstName, String lastName, Date dateOfBirth, String nhsNumber, String gender,
            String phoneNumber, String email, String address, String postCode, String emergencyContactName, String emergencyContactNo, Date dateRegistered, String gpId
    ) {

        String patientId = model.generatePatientId(); // e.g. P001

        Patient patient = new Patient(patientId, firstName, lastName, dateOfBirth, nhsNumber, gender,
                phoneNumber, email, address, postCode, emergencyContactName, emergencyContactNo, dateRegistered, gpId
        );

        model.addPatient(patient);
        view.showSuccessMessage("Patient added successfully!");
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





}


    interface PatientListener {
        void onAddPatient(String firstName, String lastName, Date dateOfBirth, String nhsNumber, String gender,
                String phoneNumber, String email, String address, String postcode, String emergencyContactName, String emergencyContactNo,
                Date dateRegistered, String gpId
        );
    }
    interface UpdateLastNameListener {
        void onUpdateLastName(String patientId, String newLastName);
    }

    interface UpdateContactInfoListener {
        void onUpdateContactInfo(String patientId, String phone, String email, String address, String postcode);
    }

    interface DeletePatientListener {
        void onDeletePatient(String patientId);
    }


    interface ClinicianListener {
        void onAddClinician(String clinicianId, String firstName, String lastName, String title,
                            String speciality, String gmcNo, String phoneNumber, String email,
                            String workplaceId, String workplaceType, String employmentStatus, Date startDate);
    }

    interface ClinicianUpdateListener {
        void onUpdateClinician(String clinicianId, String firstName, String lastName, String title,
                               String speciality, String gmcNo, String phoneNumber, String email,
                               String workplaceId, String workplaceType, String employmentStatus, Date startDate);
    }

    interface ClinicianDeleteListener {
        void onDeleteClinician(String clinicianId);
    }

    interface AddPrescriptionListener {
        void onAddPrescription(String id, String patientId, String clinicianId, String appointmentId,
                               Date prescriptionDate, String medicationName, String dosage, String frequency,
                               int durationDays, String quantity, String instructions, String pharmacies,
                               String status, Date dateIssued, Date collectionDate);
    }

    interface UpdatePrescriptionListener {
        void onUpdatePrescription(String id, String patientId, String clinicianId, String appointmentId,
                                  Date prescriptionDate, String medicationName, String dosage, String frequency,
                                  int durationDays, String quantity, String instructions, String pharmacies,
                                  String status, Date dateIssued, Date collectionDate);
    }

    interface DeletePrescriptionListener {
        void onDeletePrescription(String prescriptionId);
    }

    interface AddAppointmentListener {
        void onAddAppointment(String id, String patientId, String clinicianId, String facilityId,
                              Date appointmentDate, String appointmentTime, int durationMinutes,
                              String appointmentType, String status, String reason, String notes,
                              Date dateCreated, Date lastModified);
    }

    interface UpdateAppointmentListener {
        void onUpdateAppointment(String id, String patientId, String clinicianId, String facilityId,
                                 Date appointmentDate, String appointmentTime, int durationMinutes,
                                 String appointmentType, String status, String reason, String notes,
                                 Date dateCreated, Date lastModified);
    }

    interface DeleteAppointmentListener {
        void onDeleteAppointment(String id);
    }






