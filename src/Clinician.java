import java.text.SimpleDateFormat;
import java.util.Date;


public class Clinician extends Person {
    private String clinicianId;
    private String title;
    private String speciality;
    private String gmcNo;
    private String workplaceId;
    private String workplaceType;
    private String employmentStatus;
    private Date startDate;

    public Clinician(String clinicianId, String firstName, String lastName, String title, String speciality, String gmcNo, String phoneNumber, String email, String workplaceId, String workplaceType, String employmentStatus, Date startDate) {
        super(firstName, lastName, email, phoneNumber);
        this.clinicianId = clinicianId;
        this.title = title;
        this.speciality = speciality;
        this.gmcNo = gmcNo;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(String clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getGmcNo() {
        return gmcNo;
    }

    public void setGmcNo(String gmcNo) {
        this.gmcNo = gmcNo;
    }

    public String getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(String workplaceId) {
        this.workplaceId = workplaceId;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void viewAssignedPatients(){}

    public void updateSpeciality(String newSpeciality){
       this.speciality = newSpeciality;
    }

//    public void updateMedicalRecords(Patient p, String details){
//
//    }
//
//    public String viewAssignedAppointments(String clinicianId){
//
//    }
//
//    public void acceptReferral(String referralId){
//
//    }

    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return clinicianId + "," + getFirstName() + "," + getLastName() + "," + title + "," + speciality + "," + gmcNo + "," +
                getPhoneNumber() + "," + getEmail() + "," + workplaceId + "," + workplaceType + "," + employmentStatus + "," + sdf.format(startDate);
    }

    public static Clinician fromCSV(String csvLine){
        try {
            String[] parts = csvLine.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Date startDate = sdf.parse(parts[11]);

            return new Clinician(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], startDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Clinician{" +
                "clinicianId='" + clinicianId + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", title='" + title + '\'' +
                ", speciality='" + speciality + '\'' +
                ", gmcNo='" + gmcNo + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", workplaceId='" + workplaceId + '\'' +
                ", workplaceType='" + workplaceType + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", startDate=" + sdf.format(startDate) +
                '}';
    }
}
