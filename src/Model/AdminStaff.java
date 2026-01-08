package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import CSVPackage.*;

public class AdminStaff extends Person {
    private String staffId;
    private String role;
    private String department;
    private String facilityID;
    private String employmentStatus;
    private Date startDate;
    private String lineManager;
    private String accessLevel;

    public AdminStaff(String staffId, String firstName, String lastName, String role,
                      String department, String facilityID, String phoneNumber, String email,
                      String employmentStatus, Date startDate, String lineManager, String accessLevel) {
        super(firstName, lastName, email, phoneNumber);
        this.staffId = staffId;
        this.role = role;
        this.department = department;
        this.facilityID = facilityID;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
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

    public String getLineManager() {
        return lineManager;
    }

    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }


    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return staffId + "," + getFirstName() + "," + getLastName() + "," + role + department + "," + facilityID + "," +
                getPhoneNumber() + "," + getEmail() + "," + employmentStatus + "," + sdf.format(startDate) + "," + lineManager + "," + accessLevel + ",";

    }

    public static AdminStaff fromCSV(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Date startDate = sdf.parse(parts[9]);

            return new AdminStaff(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], startDate, parts[10], parts[11]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return "Model.AdminStaff{" +
                "staffId='" + staffId + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", facilityID='" + facilityID + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", startDate=" + sdf.format(startDate) +
                ", lineManager='" + lineManager + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }
}
