package Model;

import CSVPackage.CSVHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReferralManager {
    private static ReferralManager instance;

    private final ArrayList<Referral> referrals = new ArrayList<>();
    private int nextReferralNumber = 1;

    private static final String REFERRALS_FILE = "referrals.csv";
    private static final String REFERRAL_EMAIL_OUT_DIR = "output/referrals";

    private ReferralManager() {
        loadReferrals();
        recalcNextIdFromData();
    }

    public static ReferralManager getInstance() {
        if (instance == null) instance = new ReferralManager();
        return instance;
    }

    // ===================== Persistence =====================

    public final void loadReferrals() {
        referrals.clear();

        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line == null) continue;

            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;


            if (i == 0 && looksLikeHeader(trimmed)) continue;

            Referral r = Referral.fromCSV(trimmed);
            if (r != null) {
                referrals.add(r);
            }
        }

        recalcNextIdFromData();
    }

    public void saveReferrals() {
        List<String> lines = new ArrayList<>();


        for (Referral r : referrals) {

            lines.add(r.toCSV());
        }

        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    // ===================== Access =====================

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<>(referrals);
    }

    public Referral getReferralById(String referralId) {
        if (referralId == null) return null;
        for (Referral r : referrals) {
            if (referralId.equalsIgnoreCase(r.getReferralId())) return r;
        }
        return null;
    }

    // ===================== ID generation =====================

    public String generateReferralId() {
        return String.format("R%04d", nextReferralNumber++);
    }

    private void recalcNextIdFromData() {
        int max = 0;
        for (Referral r : referrals) {
            int n = extractNumericId(r.getReferralId());
            if (n > max) max = n;
        }
        nextReferralNumber = max + 1;
        if (nextReferralNumber < 1) nextReferralNumber = 1;
    }

    private int extractNumericId(String referralId) {
        if (referralId == null) return 0;
        String digits = referralId.replaceAll("\\D+", "");
        if (digits.isEmpty()) return 0;
        try { return Integer.parseInt(digits); }
        catch (NumberFormatException e) { return 0; }
    }

    // ===================== Create / Update =====================

    public Referral addReferral(Referral referral) {
        if (referral == null) return null;

        if (referral.getReferralId() == null || referral.getReferralId().trim().isEmpty()) {
            referral.setReferralId(generateReferralId());
        } else {

            int n = extractNumericId(referral.getReferralId());
            if (n >= nextReferralNumber) nextReferralNumber = n + 1;
        }


        if (referral.getDateCreated() == null) referral.setDateCreated(new Date());
        referral.setDateLastUpdated(new Date());

        referrals.add(referral);
        saveReferrals();


        onReferralCreated(referral);

        return referral;
    }

    public boolean deleteReferral(String referralId) {
        if (referralId == null) return false;
        for (int i = 0; i < referrals.size(); i++) {
            if (referralId.equalsIgnoreCase(referrals.get(i).getReferralId())) {
                referrals.remove(i);
                saveReferrals();
                return true;
            }
        }
        return false;
    }

    public boolean updateReferralStatus(String referralId, String newStatus) {
        Referral r = getReferralById(referralId);
        if (r == null) return false;
        r.setStatus(newStatus);
        r.setDateLastUpdated(new Date());
        saveReferrals();
        return true;
    }


    public ArrayList<Referral> getPendingReferrals() {
        ArrayList<Referral> pending = new ArrayList<>();
        for (Referral r : referrals) {
            if ("PENDING".equalsIgnoreCase(safe(r.getStatus()))) pending.add(r);
        }
        return pending;
    }

    // ===================== “Email” simulation output =====================

    public void writeReferralEmailText(Referral referral) {
        if (referral == null) return;

        File dir = new File(REFERRAL_EMAIL_OUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filename = REFERRAL_EMAIL_OUT_DIR + "/" + referral.getReferralId() + "_referral.txt";

        ArrayList<String> lines = new ArrayList<>();
        lines.add("=== REFERRAL EMAIL CONTENT (SIMULATED) ===");
        lines.add("Referral ID: " + safe(referral.getReferralId()));
        lines.add("Date: " + fmt(sdf, referral.getReferredDate()));
        lines.add("");
        lines.add("Patient ID: " + safe(referral.getPatientID()));
        lines.add("Appointment ID: " + safe(referral.getAppointmentID()));
        lines.add("");
        lines.add("From Clinician ID: " + safe(referral.getReferredFrom()));
        lines.add("To Clinician ID: " + safe(referral.getReferredTo()));
        lines.add("From Facility ID: " + safe(referral.getReferredFromFacility()));
        lines.add("To Facility ID: " + safe(referral.getReferredToFacility()));
        lines.add("");
        lines.add("Urgency: " + safe(referral.getUrgencyLevel()));
        lines.add("Reason: " + safe(referral.getReason()));
        lines.add("");
        lines.add("Clinical Summary:");
        lines.add(safe(referral.getClinicalSummary()));
        lines.add("");
        lines.add("Requested Investigations:");
        lines.add(safe(referral.getRequestedInvenstigations()));
        lines.add("");
        lines.add("Notes:");
        lines.add(safe(referral.getNotes()));
        lines.add("");
        lines.add("Status: " + safe(referral.getStatus()));
        lines.add("Created: " + fmt(sdf, referral.getDateCreated()));
        lines.add("Last Updated: " + fmt(sdf, referral.getDateLastUpdated()));


        CSVHandler.writeLines(filename, lines);
    }

    private String fmt(SimpleDateFormat sdf, Date d) {
        return d == null ? "" : sdf.format(d);
    }

    private boolean looksLikeHeader(String line) {
        String l = line.toLowerCase();
        return l.contains("referral") && l.contains("patient");
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    // ===================== Spec hooks (Singleton responsibility) =====================

    private void onReferralCreated(Referral r) {

    }

}