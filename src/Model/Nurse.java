package Model;

import java.util.ArrayList;
import java.util.Date;

public class Nurse extends Clinician {
    ArrayList<String> administeredDrugs = new ArrayList<String>();

    public Nurse(String firstName, String lastName, String email, String phoneNumber, String clinicianId, String title, String speciality, String gmcNo, String workplaceId, String workplaceType, String employmentStatus, Date startDate, ArrayList<String> administeredDrugs) {
        super(firstName, lastName, email, phoneNumber, clinicianId, title, speciality, gmcNo, workplaceId, workplaceType, employmentStatus, startDate);
        this.administeredDrugs = administeredDrugs;
    }

    public ArrayList<String> getAdministeredDrugs() {
        return administeredDrugs;
    }

    public void setAdministeredDrugs(ArrayList<String> administeredDrugs) {
        this.administeredDrugs = administeredDrugs;
    }

    public void recordAdministeredDrugs(String administeredDrug) {
        administeredDrugs.add(administeredDrug);
    }
}
