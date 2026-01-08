package Controller;

import java.util.Date;

public interface ClinicianListener {
    void onAddClinician(String clinicianId, String firstName, String lastName, String title,
                        String speciality, String gmcNo, String phoneNumber, String email,
                        String workplaceId, String workplaceType, String employmentStatus, Date startDate);
}
