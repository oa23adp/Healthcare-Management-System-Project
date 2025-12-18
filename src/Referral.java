import java.text.SimpleDateFormat;
import java.util.Date;

public class Referral {
    private String referralId;
    private String patientID;
    private String referredFrom;
    private String referredTo;
    private String referredFromFacility;
    private String referredToFacility;
    private Date referredDate;
    private String urgencyLevel;
    private String reason;
    private String clinicalSummary;
    private String requestedInvenstigations;
    private String status;
    private String appointmentID;
    private String notes;
    private Date dateCreated;
    private Date dateLastUpdated;

    public Referral(String referralId, String patientID, String referredFrom, String referredTo, String referredFromFacility,
                    String referredToFacility, Date referredDate, String urgencyLevel, String reason, String clinicalSummary,
                    String requestedInvenstigations, String status, String appointmentID, String notes, Date dateCreated, Date dateLastUpdated) {
        this.referralId = referralId;
        this.patientID = patientID;
        this.referredFrom = referredFrom;
        this.referredTo = referredTo;
        this.referredFromFacility = referredFromFacility;
        this.referredToFacility = referredToFacility;
        this.referredDate = referredDate;
        this.urgencyLevel = urgencyLevel;
        this.reason = reason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvenstigations = requestedInvenstigations;
        this.status = status;
        this.appointmentID = appointmentID;
        this.notes = notes;
        this.dateCreated = dateCreated;
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getReferredFrom() {
        return referredFrom;
    }

    public void setReferredFrom(String referredFrom) {
        this.referredFrom = referredFrom;
    }

    public String getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(String referredTo) {
        this.referredTo = referredTo;
    }

    public String getReferredFromFacility() {
        return referredFromFacility;
    }

    public void setReferredFromFacility(String referredFromFacility) {
        this.referredFromFacility = referredFromFacility;
    }

    public String getReferredToFacility() {
        return referredToFacility;
    }

    public void setReferredToFacility(String referredToFacility) {
        this.referredToFacility = referredToFacility;
    }

    public Date getReferredDate() {
        return referredDate;
    }

    public void setReferredDate(Date referredDate) {
        this.referredDate = referredDate;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public void setClinicalSummary(String clinicalSummary) {
        this.clinicalSummary = clinicalSummary;
    }

    public String getRequestedInvenstigations() {
        return requestedInvenstigations;
    }

    public void setRequestedInvenstigations(String requestedInvenstigations) {
        this.requestedInvenstigations = requestedInvenstigations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
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

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }


    //    public String viewReferralInfo() {
//
//    }
//
//    public void updateReferralStatus(String newReferralStatus) {
//
//    }
//
//    public void updateUrgencyLevel(String newUrgencyLevel) {
//
//    }
//
//    public void updateReason(String newReason) {
//
//    }
//
//    public String viewReferralSummary() {
//        return null;
//    }
//
//    public String communicateWithGP(String fromClinicianId, String toClinicianId, String message) {
//        return null;
//    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return referralId + "," + patientID + "," + referredFrom + "," + referredTo + "," + referredFromFacility + "," + referredToFacility + "," +
                sdf.format(referredDate) + "," + urgencyLevel + "," + reason + "," + clinicalSummary + "," + requestedInvenstigations + "," + status + ","
                + appointmentID + "," + notes + "," + sdf.format(dateCreated) + "," + sdf.format(dateLastUpdated) + "\n";
    }

    public static Referral fromCSV(String csv) {
        try {
            String[] parts = csv.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


            Date referredDate = sdf.parse(parts[6]);
            Date dateCreated = sdf.parse(parts[14]);
            Date dateLastUpdated = sdf.parse(parts[15]);

            return new Referral(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], referredDate, parts[7],
                    parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], dateCreated, dateLastUpdated);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        return "Referral{" +
                "referralId='" + referralId + '\'' +
                ", patientID='" + patientID + '\'' +
                ", referredFrom='" + referredFrom + '\'' +
                ", referredTo='" + referredTo + '\'' +
                ", referredFromFacility='" + referredFromFacility + '\'' +
                ", referredToFacility='" + referredToFacility + '\'' +
                ", referredDate=" + sdf.format(referredDate) +
                ", urgencyLevel='" + urgencyLevel + '\'' +
                ", reason='" + reason + '\'' +
                ", clinicalSummary='" + clinicalSummary + '\'' +
                ", requestedInvenstigations='" + requestedInvenstigations + '\'' +
                ", status='" + status + '\'' +
                ", appointmentID='" + appointmentID + '\'' +
                ", notes='" + notes + '\'' +
                ", dateCreated=" + sdf.format(dateCreated) +
                ", dateLastUpdated=" + sdf.format(dateLastUpdated) +
                '}';
    }
}