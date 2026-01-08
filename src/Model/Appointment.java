package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import CSVPackage.*;


public class Appointment {
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String facilityId;
    private Date appointmentDate;
    private String appointmentTime;
    private int durationMinutes;
    private String appointmentType;
    private String status;
    private String reason;
    private String notes;
    private Date dateCreated;
    private Date lastModified;


    public Appointment(String appointmentId, String patientId, String clinicianId, String facilityId, Date appointmentDate, String appointmentTime, int durationMinutes, String appointmentType, String status, String reason, String notes, Date dateCreated, Date lastModified) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.facilityId = facilityId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reason = reason;
        this.notes = notes;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(String clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }



    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return String.join(",",
                appointmentId,
                patientId,
                clinicianId,
                facilityId,
                appointmentDate == null ? "" : sdf.format(appointmentDate),
                appointmentTime,
                String.valueOf(durationMinutes),
                appointmentType,
                status,
                reason,
                notes == null ? "" : notes,
                dateCreated == null ? "" : sdf.format(dateCreated),
                lastModified == null ? "" : sdf.format(lastModified)
        );
    }



    public static Appointment fromCSV(String csvLine) {
        try {
            if (csvLine == null || csvLine.trim().isEmpty()) return null;

            // Skip header row
            if (csvLine.toLowerCase().contains("appointment_date")) return null;

            String[] parts = csvLine.split(",", -1); // keep empty fields

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            Date appointmentDate = parts[4].isEmpty() ? null : sdf.parse(parts[4]);
            Date dateCreated     = parts[11].isEmpty() ? null : sdf.parse(parts[11]);
            Date lastModified    = parts[12].isEmpty() ? null : sdf.parse(parts[12]);

            return new Appointment(
                    parts[0],                     // appointmentId
                    parts[1],                     // patientId
                    parts[2],                     // clinicianId
                    parts[3],                     // facilityId
                    appointmentDate,              // appointmentDate
                    parts[5],                     // appointmentTime
                    Integer.parseInt(parts[6]),    // durationMinutes
                    parts[7],                     // appointmentType
                    parts[8],                     // status
                    parts[9],                     // reason
                    parts[10],                    // notes
                    dateCreated,                  // dateCreated
                    lastModified                  // lastModified
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Model.Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", clinicianId='" + clinicianId + '\'' +
                ", facilityId='" + facilityId + '\'' +
                ", appointmentDate=" + sdf.format(appointmentDate) +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", appointmentType='" + appointmentType + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", notes='" + notes + '\'' +
                ", dateCreated=" + sdf.format(dateCreated) +
                ", lastModified=" + sdf.format(lastModified) +
                '}';
    }
}