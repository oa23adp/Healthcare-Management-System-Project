package Model;

import java.util.Date;

public class Specialist extends Clinician {

    public Specialist(String firstName, String lastName, String email, String phoneNumber, String clinicianId, String title, String speciality, String gmcNo, String workplaceId, String workplaceType, String employmentStatus, Date startDate) {
        super(firstName, lastName, email, phoneNumber, clinicianId, title, speciality, gmcNo, workplaceId, workplaceType, employmentStatus, startDate);
    }
}
