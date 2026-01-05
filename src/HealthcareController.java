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
    }


    //=============== Patient Handlers =============

    private void handleAddPatient(
            String firstName,
            String lastName,
            Date dateOfBirth,
            String nhsNumber,
            String gender,
            String phoneNumber,
            String email,
            String address,
            String postCode,
            String emergencyContactName,
            String emergencyContactNo,
            Date dateRegistered,
            String gpId
    ) {

        String patientId = model.generatePatientId(); // e.g. P001

        Patient patient = new Patient(
                patientId,
                firstName,
                lastName,
                dateOfBirth,
                nhsNumber,
                gender,
                phoneNumber,
                email,
                address,
                postCode,
                emergencyContactName,
                emergencyContactNo,
                dateRegistered,
                gpId
        );

        model.addPatient(patient);
        view.showSuccessMessage("Patient added successfully!");
    }

    // ========== Data Persistence ==========

    private void handleSaveData() {
        model.saveAllData();
    }

    // ========== Data Access Methods for View ==========

    public ArrayList<Patient> getAllPatients() {
        return model.getAllPatients();
    }
}


    interface PatientListener {
        void onAddPatient(
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
        );
    }

