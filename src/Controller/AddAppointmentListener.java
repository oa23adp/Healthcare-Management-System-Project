package Controller;

import java.util.Date;

public interface AddAppointmentListener {
    void onAddAppointment(String id, String patientId, String clinicianId, String facilityId,
                          Date appointmentDate, String appointmentTime, int durationMinutes,
                          String appointmentType, String status, String reason, String notes,
                          Date dateCreated, Date lastModified);
}
