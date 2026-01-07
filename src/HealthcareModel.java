import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class HealthcareModel {
    private HashMap<String, Patient> patients;
    private HashMap<String, Clinician> clinicians;
    private HashMap<String, Facility> facilities;
    private HashMap<String, Appointment> appointments;
    private HashMap<String,Prescription> prescriptions;





    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";



    public HealthcareModel() {
        patients = new HashMap<String, Patient>();
        clinicians = new HashMap<String, Clinician>();
        facilities = new HashMap<String, Facility>();
        appointments = new HashMap<String, Appointment>();
        prescriptions = new HashMap<String, Prescription>();


        loadAllData();
    }


    public void loadAllData() {
        loadClinicians();
        loadPatients();
        loadPrescriptions();

    }

    public void saveAllData() {
        saveClinicians();
        savePatients();
        savePrescriptions();
    }


    //  =============== Patient Management =================
    public void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Patient patient = Patient.fromCSV(lines.get(i));
            if (patient != null) {
                patients.put(patient.getPatientId(), patient);
            }
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

    public boolean updatePatientLastName(String patientId, String newLastName) {
        Patient p = patients.get(patientId);
        if (p == null) return false;
        p.setLastName(newLastName);
        return true;
    }

    public boolean updatePatientContactInfo(String patientId, String phone, String email, String address, String postcode) {
        Patient p = patients.get(patientId);
        if (p == null) return false;

        p.setPhoneNumber(phone);
        p.setEmail(email);
        p.setAddress(address);
        p.setPostCode(postcode);
        return true;
    }

    public boolean deletePatient(String patientId) {
        Patient removed = patients.remove(patientId);
        if (removed != null) {
            savePatients(); // persist deletion immediately (optional but recommended)
            return true;
        }
        return false;
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



    // ============== Clinician Management =================

    public void loadClinicians() {
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Clinician clinician = Clinician.fromCSV(lines.get(i));
            if (clinician != null) {
                clinicians.put(clinician.getClinicianId(), clinician);
            }
        }
    }

    public void saveClinicians() {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Clinician> clinicianList = new ArrayList<Clinician>(clinicians.values());

        for (int i = 0; i < clinicianList.size(); i++) {
            lines.add(clinicianList.get(i).toCSV());
        }

        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public void addClinician(Clinician clinician) {
        clinicians.put(clinician.getClinicianId(), clinician);
        saveClinicians();
    }

    public boolean deleteClinician(String clinicianId) {
        Clinician removed = clinicians.remove(clinicianId);
        if (removed != null) {
            saveClinicians();
            return true;
        }
        return false;
    }

    public boolean updateClinician(String clinicianId, Clinician updated) {
        if (!clinicians.containsKey(clinicianId)) return false;
        clinicians.put(clinicianId, updated);
        saveClinicians();
        return true;
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians.values());
    }


    // ============== Prescription Management =================

    public void loadPrescriptions() {
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Prescription p = Prescription.fromCSV(lines.get(i));
            if (p != null) {
                prescriptions.put(p.getPrescriptionID(), p);
            }
        }
    }

    public void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Prescription> list = new ArrayList<>(prescriptions.values());

        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toCSV());
        }
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public void addPrescription(Prescription p) {
        prescriptions.put(p.getPrescriptionID(), p);
        savePrescriptions();
    }

    public boolean updatePrescription(String prescriptionId, Prescription updated) {
        if (!prescriptions.containsKey(prescriptionId)) return false;
        prescriptions.put(prescriptionId, updated);
        savePrescriptions();
        return true;
    }

    public boolean deletePrescription(String prescriptionId) {
        Prescription removed = prescriptions.remove(prescriptionId);
        if (removed != null) {
            savePrescriptions();
            return true;
        }
        return false;
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions.values());
    }

}
