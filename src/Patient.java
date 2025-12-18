import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Patient extends Person {

    private String patientId;
    private Date dateOfBirth;
    private String nhsNumber;
    private String gender;
    private String address;
    private String postCode;
    private String emergencyContactName;
    private String emergencyContactNo;
    private Date dateRegistered;
    private String gpId;

    public Patient(String patientId, String firstName, String lastName, Date dateOfBirth, String nhsNumber, String gender, String phoneNumber,
                   String email, String address, String postCode, String emergencyContactName, String emergencyContactNo, Date dateRegistered, String gpId) {
        super(firstName, lastName, email, phoneNumber);
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.address = address;
        this.postCode = postCode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNo = emergencyContactNo;
        this.dateRegistered = dateRegistered;
        this.gpId = gpId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
    }


//    public void updateAddress(String newAddress, String newPostCode) {
//        this.address = newAddress;
//        this.postCode = newPostCode;
//    }
//
//    public void updateEmergencyContact(String newName, String newContactNo) {
//        this.emergencyContactName = newName;
//        this.emergencyContactNo = newContactNo;
//    }
//
//    public List<Prescription> viewPrescriptions(Prescription prescriptionID) {
//        return new ArrayList<>();
//    }
//
//    public void requestAppointment(Date appointmentDate, String time, String reason) {
//
//    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return patientId + "," + getFirstName() + "," + getLastName() + "," + sdf.format(dateOfBirth) + "," + nhsNumber + "," + gender + "," + getPhoneNumber() + "," + getEmail() + "," + address + "," + postCode
                + "," + emergencyContactName + "," + emergencyContactNo + "," + sdf.format(dateRegistered) + "," + gpId;
    }

    public static Patient fromCSV(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Date dateOfBirth = sdf.parse(parts[3]);
            Date dateRegistered = sdf.parse(parts[12]);

            return new Patient(
                    parts[0],
                    parts[1],
                    parts[2],
                    dateOfBirth,
                    parts[4],
                    parts[5],
                    parts[6],
                    parts[7],
                    parts[8],
                    parts[9],
                    parts[10],
                    parts[11],
                    dateRegistered,
                    parts[13]
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", dateOfBirth=" + sdf.format(dateOfBirth) +
                ", nhsNumber='" + nhsNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactNo='" + emergencyContactNo + '\'' +
                ", dateRegistered=" + sdf.format(dateRegistered) +
                ", gpId='" + gpId + '\'' +
                '}';
    }

}
