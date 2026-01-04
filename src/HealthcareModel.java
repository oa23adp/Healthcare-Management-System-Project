import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class HealthcareModel {
    private HashMap<String, Patient> patients;


    private static final String PATIENTS_FILE = "patients.csv";


    public HealthcareModel() {
        patients = new HashMap<String, Patient>();
    }


    public void loadAllData() {
        loadPatients();
    }

    public void saveAllData() {
        savePatients();
    }


    //  =============== Patient Management =================
    public void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Patient patient = Patient.fromCSV(lines.get(i));
            patients.put(patient.getPatientId(), patient);
        }
    }

    public void savePatients() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Patient> patientList = new ArrayList<Patient>(patients.values());

        for (int i = 0; i < patientList.size(); i++) {
            lines.add(patientList.get(i).toCSV());
        }

        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
        savePatients();
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients.values());
    }

    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }

    public String generatePatientId() {
        return String.format("P%03d", patients.size() + 1);
    }
}
