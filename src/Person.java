public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Person(String firstName, String lastName, String email, String phoneNumber) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public String viewPersonalDetails() {
        return getFirstName() + " /n" + getLastName();
    }

    public void updateLastName(String newLastName){
        this.setLastName(newLastName);
    }

    public void updateContactInfo(String newEmail, String newPhoneNumber){
        this.setEmail(newEmail);
        this.setPhoneNumber(newPhoneNumber);
    }

//    public Appointment viewPatientAppointment(String patientId){
//
//    }
//
//    public MedicalRecord viewPatientMedicalRecord(){
//        return null;
//    }
//
//    public Referral viewPatientReferral(){
//        return null;
//    }


}
