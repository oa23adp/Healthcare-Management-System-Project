package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import CSVPackage.*;


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



    public String toCSV() {
        List<String> fields = new ArrayList<>();
        fields.add(prescriptionID);
        fields.add(patientID);
        fields.add(clinicianID);
        fields.add(appointmentID);
        fields.add(formatDateOrBlank(prescriptionDate));
        fields.add(medicationName);
        fields.add(dosage);
        fields.add(frequency);
        fields.add(String.valueOf(durationDays));
        fields.add(quantity);
        fields.add(instructions);
        fields.add(pharmacies);
        fields.add(status);
        fields.add(formatDateOrBlank(dateIssued));
        fields.add(formatDateOrBlank(collectionDate));

        return CSVHandler.toLine(fields);
    }




    public static Prescription fromCSV(String csvLine) {
        try {
            if (csvLine == null) return null;
            String line = csvLine.trim();
            if (line.isEmpty()) return null;

            // Skip header row
            String lower = line.toLowerCase();
            if (lower.startsWith("prescription") || lower.startsWith("prescription_id")) return null;

            List<String> parts = CSVHandler.parseLine(line);
            if (parts.size() < 15) return null;

            Date prescriptionDate = parseDateFlexibleOrNull(parts.get(4));
            Date dateIssued = parseDateFlexibleOrNull(parts.get(13));
            Date collectionDate = parseDateFlexibleOrNull(parts.get(14));

            int durationDays = 0;
            String durationText = parts.get(8).trim();
            if (!durationText.isEmpty()) {
                durationDays = Integer.parseInt(durationText);
            }

            return new Prescription(
                    parts.get(0).trim(),  // prescriptionID
                    parts.get(1).trim(),  // patientID
                    parts.get(2).trim(),  // clinicianID
                    parts.get(3).trim(),  // appointmentID
                    prescriptionDate,
                    parts.get(5).trim(),  // medicationName
                    parts.get(6).trim(),  // dosage
                    parts.get(7).trim(),  // frequency
                    durationDays,
                    parts.get(9).trim(),  // quantity
                    parts.get(10).trim(), // instructions
                    parts.get(11).trim(), // pharmacies
                    parts.get(12).trim(), // status
                    dateIssued,
                    collectionDate
            );
        } catch (Exception e) {
            // just skip the row
            return null;
        }
    }


    private static Date parseDateFlexibleOrNull(String s) throws Exception {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;

        SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
        a.setLenient(false);

        try {
            return a.parse(t);
        } catch (Exception ignore) {
            SimpleDateFormat b = new SimpleDateFormat("yyyy/MM/dd");
            b.setLenient(false);
            return b.parse(t);
        }
    }

    private static String formatDateOrBlank(Date d) {
        if (d == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }






    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Model.Prescription{" +
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
