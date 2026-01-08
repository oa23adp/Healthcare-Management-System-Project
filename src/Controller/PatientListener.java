package Controller;

import java.util.Date;

public interface PatientListener {
    void onAddPatient(String firstName, String lastName, Date dateOfBirth, String nhsNumber, String gender,
                      String phoneNumber, String email, String address, String postcode, String emergencyContactName, String emergencyContactNo,
                      Date dateRegistered, String gpId
    );
}
