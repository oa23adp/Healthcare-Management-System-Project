
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;


public class HealthcareView extends JFrame {
    private HealthcareController controller;
    private JTabbedPane tabbedPane;

    //Patient panel components
    private JTable patientsTable;
    private DefaultTableModel patientsTableModel;





    //Listener References
    private PatientListener addPatientListener;
    private Runnable onCloseListener;
    private UpdateLastNameListener updateLastNameListener;
    private UpdateContactInfoListener updateContactInfoListener;
    private DeletePatientListener deletePatientListener;



    public HealthcareView() {
        setTitle("Healthcare Management System - MVC Architecture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        pack();
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
    }

    public void setController(HealthcareController controller) {
        this.controller = controller;
        reloadPatientsData();
    }


    private void loadPatientsData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");


        //Load patients data
        ArrayList <Patient> patients = controller.getAllPatients();
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    sdf.format(patient.getDateOfBirth()),
                    patient.getNhsNumber(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getPostCode(),
                    patient.getEmergencyContactName(),
                    patient.getEmergencyContactNo(),
                    sdf.format(patient.getDateRegistered()),
                    patient.getGpId()
            };
            patientsTableModel.addRow(row);
        }
    }

    public void reloadPatientsData() {
        if (controller == null) return;


        patientsTableModel.setRowCount(0);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Patient> patients = controller.getAllPatients();
        for (Patient patient : patients) {
            Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    sdf.format(patient.getDateOfBirth()),
                    patient.getNhsNumber(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getPostCode(),
                    patient.getEmergencyContactName(),
                    patient.getEmergencyContactNo(),
                    sdf.format(patient.getDateRegistered()),
                    patient.getGpId()
            };
            patientsTableModel.addRow(row);
        }
    }




    private void initComponents() {
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patients",createPatientsPanel());

        add(tabbedPane,BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) {
                    onCloseListener.run();
                }
            }
        });
    }

    //=================== Patient Panel ========================
    private JPanel createPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Patients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Patient ID", "First Name", "Last Name", "Date of Birth","NHS Number","Gender","Phone Number",
                            "Email","Address","Postcode","Emergency contact name","Emergency contact no","Date Registered","GP ID"};
        patientsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientsTable = new JTable(patientsTableModel);
        patientsTable.setFillsViewportHeight(true);
        patientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(patientsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Patient");
        addButton.addActionListener(e -> showAddPatientDialog());

        JButton updateLastNameButton = new JButton("Update Last Name");
        updateLastNameButton.addActionListener(e -> showUpdateLastNameDialog());

        JButton updateContactButton = new JButton("Update Contact Info");
        updateContactButton.addActionListener(e -> showUpdateContactInfoDialog());

        JButton deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(e -> handleDeletePatient());

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddPatientDialog();
            }
        });

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateLastNameButton);
        buttonsPanel.add(updateContactButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }
    private void showAddPatientDialog() {
        JDialog dialog = new JDialog(this, "Add Patient", true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField(15);
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField(15);
        panel.add(lastNameField);

        panel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        JTextField dobField = new JTextField(15);
        panel.add(dobField);

        panel.add(new JLabel("NHS Number:"));
        JTextField nhsNoField = new JTextField(15);
        panel.add(nhsNoField);

        panel.add(new JLabel("Gender:"));
        JTextField genderField = new JTextField(15);
        panel.add(genderField);

        panel.add(new JLabel("Phone Number:"));
        JTextField phoneNumberField = new JTextField(15);
        panel.add(phoneNumberField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(15);
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(15);
        panel.add(addressField);

        panel.add(new JLabel("Postcode:"));
        JTextField postcodeField = new JTextField(15);
        panel.add(postcodeField);

        panel.add(new JLabel("Emergency Contact Name:"));
        JTextField ecNameField = new JTextField(15);
        panel.add(ecNameField);

        panel.add(new JLabel("Emergency Contact Number:"));
        JTextField ecNoField = new JTextField(15);
        panel.add(ecNoField);

        panel.add(new JLabel("Date Registered:"));
        JTextField dateRegField = new JTextField(15);
        panel.add(dateRegField);


        panel.add(new JLabel("GP Id:"));
        JTextField gpIdField = new JTextField(15);
        panel.add(gpIdField);


        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String firstName = firstNameField.getText().trim();
                if (firstName.isEmpty()) {
                    showErrorMessage("First Name is required");
                    return;
                }

                String lastName = lastNameField.getText().trim();
                if (lastName.isEmpty()) {
                    showErrorMessage("Last Name is required");
                    return;
                }


                Date dob;
                try {
                    dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobField.getText().trim());
                } catch (Exception ex) {
                    showErrorMessage("Invalid Date of Birth format (yyyy-MM-dd)");
                    return;
                }


                String nhsNumber = nhsNoField.getText().trim();
                if (nhsNumber.isEmpty()) {
                    showErrorMessage("NHS Number is required");
                    return;
                }

                String gender = genderField.getText().trim();
                if (gender.isEmpty()) {
                    showErrorMessage("Gender is required");
                    return;
                }

                String phoneNumber = phoneNumberField.getText().trim();
                if (phoneNumber.isEmpty()) {
                    showErrorMessage("Phone Number is required");
                    return;
                }

                String email = emailField.getText().trim();
                if (email.isEmpty()) {
                    showErrorMessage("Email is required");
                    return;
                }

                String address = addressField.getText().trim();
                if (address.isEmpty()) {
                    showErrorMessage("Address is required");
                    return;
                }

                String postcode = postcodeField.getText().trim();
                if (postcode.isEmpty()) {
                    showErrorMessage("Postcode is required");
                    return;
                }

                String ecName = ecNameField.getText().trim();
                if (ecName.isEmpty()) {
                    showErrorMessage("Emergency Contact Name is required");
                    return;
                }

                String ecNumber = ecNoField.getText().trim();
                if (ecNumber.isEmpty()) {
                    showErrorMessage("Emergency Contact Number is required");
                    return;
                }

                Date dateRegistered;
                try {
                    dateRegistered = new SimpleDateFormat("yyyy-MM-dd").parse(dateRegField.getText().trim());
                } catch (Exception ex) {
                    showErrorMessage("Invalid Date Registered format (yyyy-MM-dd)");
                    return;
                }



                String gpId = gpIdField.getText().trim();
                if (gpId.isEmpty()) {
                    showErrorMessage("GP ID is required");
                    return;
                }

                if (addPatientListener != null) {
                    addPatientListener.onAddPatient(
                            firstName, lastName, dob, nhsNumber, gender,
                            phoneNumber, email, address, postcode,
                            ecName, ecNumber, dateRegistered, gpId
                    );

                }


                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.setContentPane(new JScrollPane(panel));
        dialog.pack();
        dialog.setMinimumSize(new Dimension(520, 420)); // stops tiny dialog
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void showUpdateLastNameDialog() {
        int row = patientsTable.getSelectedRow();
        if (row == -1) {
            showErrorMessage("Select a patient row.");
            return;
        }

        String patientId = patientsTableModel.getValueAt(row, 0).toString();

        String newLastName = JOptionPane.showInputDialog(this, "Enter new Last Name:");
        if (newLastName == null) return; // cancelled
        newLastName = newLastName.trim();

        if (newLastName.isEmpty()) {
            showErrorMessage("Last Name cannot be empty.");
            return;
        }

        if (updateLastNameListener != null) {
            updateLastNameListener.onUpdateLastName(patientId, newLastName);
        }


    }
    private void showUpdateContactInfoDialog() {
        int row = patientsTable.getSelectedRow();
        if (row == -1) {
            showErrorMessage("Select a patient row first.");
            return;
        }

        String patientId = patientsTableModel.getValueAt(row, 0).toString();

        JTextField phoneField = new JTextField(patientsTableModel.getValueAt(row, 6).toString(), 15);
        JTextField emailField = new JTextField(patientsTableModel.getValueAt(row, 7).toString(), 15);
        JTextField addressField = new JTextField(patientsTableModel.getValueAt(row, 8).toString(), 15);
        JTextField postcodeField = new JTextField(patientsTableModel.getValueAt(row, 9).toString(), 15);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Phone:"));   panel.add(phoneField);
        panel.add(new JLabel("Email:"));   panel.add(emailField);
        panel.add(new JLabel("Address:")); panel.add(addressField);
        panel.add(new JLabel("Postcode:"));panel.add(postcodeField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Update Contact Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return;

        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String postcode = postcodeField.getText().trim();

        if (phone.isEmpty() || email.isEmpty() || address.isEmpty() || postcode.isEmpty()) {
            showErrorMessage("All contact fields are required.");
            return;
        }

        if (updateContactInfoListener != null) {
            updateContactInfoListener.onUpdateContactInfo(patientId, phone, email, address, postcode);
        }



    }
    private void handleDeletePatient() {
        int row = patientsTable.getSelectedRow();
        if (row == -1) {
            showErrorMessage("Select a patient row first.");
            return;
        }

        String patientId = patientsTableModel.getValueAt(row, 0).toString();
        String firstName = patientsTableModel.getValueAt(row, 1).toString();
        String lastName  = patientsTableModel.getValueAt(row, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete patient " + patientId + " (" + firstName + " " + lastName + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        if (deletePatientListener != null) {
            deletePatientListener.onDeletePatient(patientId);

        }
    }




    // =================Listener Setters ===================

    public void setAddPatientListener(PatientListener listener) {
        this.addPatientListener = listener;
    }

    public void setOnCloseListener(Runnable listener) {
        this.onCloseListener = listener;
    }

    public void setUpdateLastNameListener(UpdateLastNameListener listener) {
        this.updateLastNameListener = listener;
    }

    public void setUpdateContactInfoListener(UpdateContactInfoListener listener) {
        this.updateContactInfoListener = listener;
    }

    public void setDeletePatientListener(DeletePatientListener listener) {
        this.deletePatientListener = listener;
    }



    // ========== Utility Methods ==========

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}