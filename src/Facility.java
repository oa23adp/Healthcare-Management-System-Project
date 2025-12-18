import java.text.SimpleDateFormat;

public class Facility {
    private String facilityID;
    private String name;
    private String type;
    private String address;
    private String postCode;
    private String phone;
    private String email;
    private String openingHours;
    private String managerHours;
    private int capacity;
    private String specialitiesOffered;

    public Facility(String facilityID, String name, String type, String address, String postCode, String phone,
                    String email, String openingHours, String managerHours, int capacity, String specialitiesOffered)
    {
        this.facilityID = facilityID;
        this.name = name;
        this.type = type;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.email = email;
        this.openingHours = openingHours;
        this.managerHours = managerHours;
        this.capacity = capacity;
        this.specialitiesOffered = specialitiesOffered;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getManagerHours() {
        return managerHours;
    }

    public void setManagerHours(String managerHours) {
        this.managerHours = managerHours;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSpecialitiesOffered() {
        return specialitiesOffered;
    }

    public void setSpecialitiesOffered(String specialitiesOffered) {
        this.specialitiesOffered = specialitiesOffered;
    }

//
//    public void updateCapacity(int newCapacity) {
//
//    }
//
//    public void addSpeciality(String oldSpeciality, String newSpeciality) {
//
//    }
//
//    public String displayFacilityInfo() {
//
//    }

    public String toCSV() { return facilityID + "," + name + "," + type + "," + address + "," + postCode + "," + phone + "," +
            email + "," + openingHours + "," + managerHours + "," + capacity + "," + specialitiesOffered; }

    public static Facility fromCSV(String csvLine){
        String[] parts = csvLine.split(",");
        return new Facility(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6],
                parts[7],parts[8],Integer.parseInt(parts[9]),parts[10]);
    }

    @Override
    public String toString() {
        return "Facility{" +
                "facilityID='" + facilityID + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", managerHours='" + managerHours + '\'' +
                ", capacity=" + capacity +
                ", specialitiesOffered='" + specialitiesOffered + '\'' +
                '}';
    }
}
