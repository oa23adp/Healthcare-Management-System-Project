import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;



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

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> fields = new ArrayList<>();
        fields.add(clinicianId);
        fields.add(getFirstName());
        fields.add(getLastName());
        fields.add(title);
        fields.add(speciality);
        fields.add(gmcNo);
        fields.add(getPhoneNumber());
        fields.add(getEmail());
        fields.add(workplaceId);
        fields.add(workplaceType);
        fields.add(employmentStatus);
        fields.add(sdf.format(startDate));

        return CSVHandler.toLine(fields);
    }


    public static Clinician fromCSV(String csvLine) {
        try {
            if (csvLine == null) return null;
            String line = csvLine.trim();
            if (line.isEmpty()) return null;

            // Skip header row
            if (line.toLowerCase().startsWith("clinician_id,")) {
                return null;
            }

            List<String> parts = CSVHandler.parseLine(line);
            if (parts.size() < 12) return null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            Date startDate = sdf.parse(parts.get(11));

            return new Clinician(
                    parts.get(0),  // clinicianId
                    parts.get(1),  // firstName
                    parts.get(2),  // lastName
                    parts.get(3),  // title
                    parts.get(4),  // speciality
                    parts.get(5),  // gmcNo
                    parts.get(6),  // phoneNumber
                    parts.get(7),  // email
                    parts.get(8),  // workplaceId
                    parts.get(9),  // workplaceType
                    parts.get(10), // employmentStatus
                    startDate
            );
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
