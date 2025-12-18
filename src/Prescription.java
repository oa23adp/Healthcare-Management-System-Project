import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String clinicianID;
    private String appointmentID;
    private Date prescriptionDate;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private String quantity;
    private String instructions;
    private String pharmacies;
    private String status;
    private Date dateIssued;
    private Date collectionDate;

    public Prescription(String prescriptionID, String patientID, String clinicianID, String appointmentID, Date prescriptionDate, String medicationName, String dosage, String frequency, int durationDays, String quantity,
                        String instructions, String pharmacies, String status, Date dateIssued, Date collectionDate) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.appointmentID = appointmentID;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instructions = instructions;
        this.pharmacies = pharmacies;
        this.status = status;
        this.dateIssued = dateIssued;
        this.collectionDate = collectionDate;
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getClinicianID() {
        return clinicianID;
    }

    public void setClinicianID(String clinicianID) {
        this.clinicianID = clinicianID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(String pharmacies) {
        this.pharmacies = pharmacies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

//
//    public void calculateEndDate(){
//
//    }
//
//    public void updateStatus(String newStatus){
//
//    }
//
//    public String viewPrescription(){
//
//    }

    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return prescriptionID +  "," + patientID + "," + clinicianID + "," + appointmentID + "," + sdf.format(prescriptionDate)
                + "," + medicationName + "," + dosage + "," + frequency + "," + durationDays + "," + quantity + ","
                + instructions + "," + pharmacies + "," + status + "," + sdf.format(dateIssued) + "," + sdf.format(collectionDate);
    }

    public static Prescription fromCSV(String csvLine){
        try {
            String[] parts = csvLine.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Date prescriptionDate = sdf.parse(parts[4]);
            Date dateIssued = sdf.parse(parts[13]);
            Date collectionDate = sdf.parse(parts[14]);

            return new Prescription(parts[0], parts[1],parts[2],parts[3],prescriptionDate,parts[5],parts[6],parts[7],Integer.parseInt(parts[8]),parts[9],parts[10],parts[11],parts[12],dateIssued,collectionDate);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Prescription{" +
                "prescriptionID='" + prescriptionID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", clinicianID='" + clinicianID + '\'' +
                ", appointmentID='" + appointmentID + '\'' +
                ", prescriptionDate=" + sdf.format(prescriptionDate) +
                ", medicationName='" + medicationName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", frequency='" + frequency + '\'' +
                ", durationDays=" + durationDays +
                ", quantity='" + quantity + '\'' +
                ", instructions='" + instructions + '\'' +
                ", pharmacies='" + pharmacies + '\'' +
                ", status='" + status + '\'' +
                ", dateIssued=" + sdf.format(dateIssued) +
                ", collectionDate=" + sdf.format(collectionDate) +
                '}';
    }
}
