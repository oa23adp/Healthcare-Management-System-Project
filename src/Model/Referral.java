package Model;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import CSVPackage.*;

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



    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private static String fmt(Date d) {
        return d == null ? "" : SDF.format(d);
    }

    private static Date parseDate(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;

        String[] patterns = {
                "yyyy-MM-dd",
                "yyyy/MM/dd"
        };

        for (String p : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(p);
                sdf.setLenient(false);
                return sdf.parse(s);
            } catch (Exception ignored) {}
        }

        return null;
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }


    public static String csvHeader() {
        return CSVHandler.toLine(List.of(
                "referral_id","patient_id","referred_from","referred_to",
                "referred_from_facility","referred_to_facility","referred_date",
                "urgency_level","reason","clinical_summary","requested_investigations",
                "status","appointment_id","notes","date_created","date_last_updated"
        ));
    }

    public String toCSV() {

        List<String> fields = new ArrayList<>();
        fields.add(safe(referralId));
        fields.add(safe(patientID));
        fields.add(safe(referredFrom));
        fields.add(safe(referredTo));
        fields.add(safe(referredFromFacility));
        fields.add(safe(referredToFacility));
        fields.add(fmt(referredDate));
        fields.add(safe(urgencyLevel));
        fields.add(safe(reason));
        fields.add(safe(clinicalSummary));
        fields.add(safe(requestedInvenstigations));
        fields.add(safe(status));
        fields.add(safe(appointmentID));
        fields.add(safe(notes));
        fields.add(fmt(dateCreated));
        fields.add(fmt(dateLastUpdated));

        return CSVHandler.toLine(fields);
    }

    public static Referral fromCSV(String line) {
        try {
            List<String> parts = CSVHandler.parseLine(line);

            if (parts.size() < 16) return null;

            Date referredDate = parseDate(parts.get(6));
            Date dateCreated = parseDate(parts.get(14));
            Date dateLastUpdated = parseDate(parts.get(15));

            return new Referral(
                    parts.get(0),  // referralId
                    parts.get(1),  // patientID
                    parts.get(2),  // referredFrom
                    parts.get(3),  // referredTo
                    parts.get(4),  // referredFromFacility
                    parts.get(5),  // referredToFacility
                    referredDate,
                    parts.get(7),  // urgencyLevel
                    parts.get(8),  // reason
                    parts.get(9),  // clinicalSummary
                    parts.get(10), // requestedInvestigations
                    parts.get(11), // status
                    parts.get(12), // appointmentID
                    parts.get(13), // notes
                    dateCreated,
                    dateLastUpdated
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


}}