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




