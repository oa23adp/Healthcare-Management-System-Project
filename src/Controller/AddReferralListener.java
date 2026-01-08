package Controller;

import java.util.Date;

public interface AddReferralListener {
    void onAddReferral(
            String patientId,
            String referredFromClinicianId,
            String referredToClinicianId,
            String referredFromFacilityId,
            String referredToFacilityId,
            Date referredDate,
            String urgencyLevel,
            String reason,
            String clinicalSummary,
            String requestedInvestigations,
            String status,
            String appointmentId,
            String notes
    );
}
