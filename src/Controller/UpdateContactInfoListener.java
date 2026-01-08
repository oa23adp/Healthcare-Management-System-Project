package Controller;

public interface UpdateContactInfoListener {
    void onUpdateContactInfo(String patientId, String phone, String email, String address, String postcode);
}
