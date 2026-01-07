
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

    //Clinician panel components
    private JTable cliniciansTable;
    private DefaultTableModel cliniciansTableModel;

    // Prescription panel components
    private JTable prescriptionsTable;
    private DefaultTableModel prescriptionsTableModel;










    //Listener References
    private PatientListener addPatientListener;
    private Runnable onCloseListener;
    private UpdateLastNameListener updateLastNameListener;
    private UpdateContactInfoListener updateContactInfoListener;
    private DeletePatientListener deletePatientListener;
    private ClinicianListener addClinicianListener;
    private ClinicianUpdateListener updateClinicianListener;
    private ClinicianDeleteListener deleteClinicianListener;
    private AddPrescriptionListener addPrescriptionListener;
    private UpdatePrescriptionListener updatePrescriptionListener;
    private DeletePrescriptionListener deletePrescriptionListener;



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
        reloadCliniciansData();
        reloadPrescriptionsData();

    }


        public void reloadCliniciansData() {
            if (controller == null) return;

            cliniciansTableModel.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            for (Clinician c : controller.getAllClinicians()) {
                cliniciansTableModel.addRow(new Object[]{
                        c.getClinicianId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getTitle(),
                        c.getSpeciality(),
                        c.getGmcNo(),
                        c.getPhoneNumber(),
                        c.getEmail(),
                        c.getWorkplaceId(),
                        c.getWorkplaceType(),
                        c.getEmploymentStatus(),
                        sdf.format(c.getStartDate())
                    });
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
        public void reloadPrescriptionsData() {
            if (controller == null) return;

            prescriptionsTableModel.setRowCount(0);

            for (Prescription p : controller.getAllPrescriptions()) {

                prescriptionsTableModel.addRow(new Object[]{
                        p.getPrescriptionID(),
                        p.getPatientID(),
                        p.getClinicianID(),
                        p.getAppointmentID(),
                        fmtDate(p.getPrescriptionDate()), // SAFE
                        p.getMedicationName(),
                        p.getDosage(),
                        p.getFrequency(),
                        p.getDurationDays(),
                        p.getQuantity(),
                        p.getInstructions(),
                        p.getPharmacies(),
                        p.getStatus(),
                        fmtDate(p.getDateIssued()),        // SAFE
                        fmtDate(p.getCollectionDate())     // SAFE
                });
            }
        }







    private void initComponents() {
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patients",createPatientsPanel());
        tabbedPane.addTab("Clinicians", createCliniciansPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());



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


        //================= Clinincians Panel =======================

        private JPanel createCliniciansPanel() {
            JPanel panel = new JPanel(new BorderLayout());

            cliniciansTableModel = new DefaultTableModel(
                    new String[]{"Clinician ID", "First Name", "Last Name", "Title", "Speciality", "GMC No",
                            "Phone", "Email", "Workplace ID", "Workplace Type", "Employment Status", "Start Date"}, 0
            );

            cliniciansTable = new JTable(cliniciansTableModel);
            panel.add(new JScrollPane(cliniciansTable), BorderLayout.CENTER);

            JPanel buttons = new JPanel();

            JButton addBtn = new JButton("Add Clinician");
            JButton editBtn = new JButton("Edit Clinician");
            JButton delBtn = new JButton("Delete Clinician");

            addBtn.addActionListener(e -> showAddClinicianDialog());
            editBtn.addActionListener(e -> showEditClinicianDialog());
            delBtn.addActionListener(e -> handleDeleteClinician());

            buttons.add(addBtn);
            buttons.add(editBtn);
            buttons.add(delBtn);

            panel.add(buttons, BorderLayout.SOUTH);
            return panel;
        }
        private void showAddClinicianDialog() {
            JDialog dialog = new JDialog(this, "Add Clinician", true);
            dialog.pack();
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField idField = new JTextField(15);
            JTextField firstField = new JTextField(15);
            JTextField lastField = new JTextField(15);
            JTextField titleField = new JTextField(15);
            JTextField specField = new JTextField(15);
            JTextField gmcField = new JTextField(15);
            JTextField phoneField = new JTextField(15);
            JTextField emailField = new JTextField(15);
            JTextField workplaceIdField = new JTextField(15);
            JTextField workplaceTypeField = new JTextField(15);
            JTextField statusField = new JTextField(15);
            JTextField startDateField = new JTextField(15);

            panel.add(new JLabel("Clinician ID:")); panel.add(idField);
            panel.add(new JLabel("First Name:")); panel.add(firstField);
            panel.add(new JLabel("Last Name:")); panel.add(lastField);
            panel.add(new JLabel("Title:")); panel.add(titleField);
            panel.add(new JLabel("Speciality:")); panel.add(specField);
            panel.add(new JLabel("GMC No:")); panel.add(gmcField);
            panel.add(new JLabel("Phone Number:")); panel.add(phoneField);
            panel.add(new JLabel("Email:")); panel.add(emailField);
            panel.add(new JLabel("Workplace ID:")); panel.add(workplaceIdField);
            panel.add(new JLabel("Workplace Type:")); panel.add(workplaceTypeField);
            panel.add(new JLabel("Employment Status:")); panel.add(statusField);
            panel.add(new JLabel("Start Date (yyyy/MM/dd):")); panel.add(startDateField);

            JButton save = new JButton("Save");
            JButton cancel = new JButton("Cancel");

            save.addActionListener(e -> {
                String id = idField.getText().trim();
                if (id.isEmpty()) { showErrorMessage("Clinician ID is required"); return; }

                String first = firstField.getText().trim();
                if (first.isEmpty()) { showErrorMessage("First Name is required"); return; }

                String last = lastField.getText().trim();
                if (last.isEmpty()) { showErrorMessage("Last Name is required"); return; }

                String title = titleField.getText().trim();
                if (title.isEmpty()) { showErrorMessage("Title is required"); return; }

                String spec = specField.getText().trim();
                if (spec.isEmpty()) { showErrorMessage("Speciality is required"); return; }

                String gmc = gmcField.getText().trim();
                if (gmc.isEmpty()) { showErrorMessage("GMC No is required"); return; }

                String phone = phoneField.getText().trim();
                if (phone.isEmpty()) { showErrorMessage("Phone Number is required"); return; }

                String email = emailField.getText().trim();
                if (email.isEmpty()) { showErrorMessage("Email is required"); return; }

                String wId = workplaceIdField.getText().trim();
                if (wId.isEmpty()) { showErrorMessage("Workplace ID is required"); return; }

                String wType = workplaceTypeField.getText().trim();
                if (wType.isEmpty()) { showErrorMessage("Workplace Type is required"); return; }

                String status = statusField.getText().trim();
                if (status.isEmpty()) { showErrorMessage("Employment Status is required"); return; }

                Date startDate;
                try {
                    startDate = new SimpleDateFormat("yyyy/MM/dd").parse(startDateField.getText().trim());
                } catch (Exception ex) {
                    showErrorMessage("Invalid Start Date format (yyyy/MM/dd)");
                    return;
                }

                if (addClinicianListener != null) {
                    addClinicianListener.onAddClinician(
                            id, first, last, title, spec, gmc, phone, email, wId, wType, status, startDate
                    );
                }

                dialog.dispose();
            });

            cancel.addActionListener(e -> dialog.dispose());

            panel.add(save);
            panel.add(cancel);

            dialog.setContentPane(new JScrollPane(panel));
            dialog.pack();
            dialog.setMinimumSize(new Dimension(560, 420));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
        private void handleDeleteClinician() {
            int row = cliniciansTable.getSelectedRow();
            if (row == -1) {
                showErrorMessage("Select a clinician first.");
                return;
            }

            String clinicianId = cliniciansTableModel.getValueAt(row, 0).toString();

            if (deleteClinicianListener != null) {
                deleteClinicianListener.onDeleteClinician(clinicianId);
            }
        }
        private void showEditClinicianDialog() {
            int row = cliniciansTable.getSelectedRow();
            if (row == -1) {
                showErrorMessage("Select a clinician first.");
                return;
            }

            String clinicianId = cliniciansTableModel.getValueAt(row, 0).toString();

            // Prefill from table
            JTextField firstField = new JTextField(cliniciansTableModel.getValueAt(row, 1).toString(), 15);
            JTextField lastField = new JTextField(cliniciansTableModel.getValueAt(row, 2).toString(), 15);
            JTextField titleField = new JTextField(cliniciansTableModel.getValueAt(row, 3).toString(), 15);
            JTextField specField = new JTextField(cliniciansTableModel.getValueAt(row, 4).toString(), 15);
            JTextField gmcField = new JTextField(cliniciansTableModel.getValueAt(row, 5).toString(), 15);
            JTextField phoneField = new JTextField(cliniciansTableModel.getValueAt(row, 6).toString(), 15);
            JTextField emailField = new JTextField(cliniciansTableModel.getValueAt(row, 7).toString(), 15);
            JTextField wIdField = new JTextField(cliniciansTableModel.getValueAt(row, 8).toString(), 15);
            JTextField wTypeField = new JTextField(cliniciansTableModel.getValueAt(row, 9).toString(), 15);
            JTextField statusField = new JTextField(cliniciansTableModel.getValueAt(row, 10).toString(), 15);
            JTextField startDateField = new JTextField(cliniciansTableModel.getValueAt(row, 11).toString(), 15);

            Object[] fields = {
                    "First Name:", firstField,
                    "Last Name:", lastField,
                    "Title:", titleField,
                    "Speciality:", specField,
                    "GMC No:", gmcField,
                    "Phone:", phoneField,
                    "Email:", emailField,
                    "Workplace ID:", wIdField,
                    "Workplace Type:", wTypeField,
                    "Employment Status:", statusField,
                    "Start Date (yyyy/MM/dd):", startDateField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Modify Clinician " + clinicianId,
                    JOptionPane.OK_CANCEL_OPTION);

            if (option != JOptionPane.OK_OPTION) return;

            Date startDate;
            try {
                startDate = new SimpleDateFormat("yyyy/MM/dd").parse(startDateField.getText().trim());
            } catch (Exception ex) {
                showErrorMessage("Invalid Start Date format (yyyy/MM/dd)");
                return;
            }

            if (updateClinicianListener != null) {
                updateClinicianListener.onUpdateClinician(
                        clinicianId,
                        firstField.getText().trim(),
                        lastField.getText().trim(),
                        titleField.getText().trim(),
                        specField.getText().trim(),
                        gmcField.getText().trim(),
                        phoneField.getText().trim(),
                        emailField.getText().trim(),
                        wIdField.getText().trim(),
                        wTypeField.getText().trim(),
                        statusField.getText().trim(),
                        startDate
                );
            }
        }





    // ================ Prescription Panel =================
        private JPanel createPrescriptionsPanel() {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            prescriptionsTableModel = new DefaultTableModel(new String[]{
                    "Prescription ID", "Patient ID", "Clinician ID", "Appointment ID",
                    "Prescription Date", "Medication", "Dosage", "Frequency",
                    "Duration (Days)", "Quantity", "Instructions", "Pharmacies",
                    "Status", "Date Issued", "Collection Date"
            }, 0) {
                public boolean isCellEditable(int row, int column) { return false; }
            };

            prescriptionsTable = new JTable(prescriptionsTableModel);
            prescriptionsTable.setFillsViewportHeight(true);
            prescriptionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            JScrollPane scroll = new JScrollPane(prescriptionsTable);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            panel.add(scroll, BorderLayout.CENTER);

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton addBtn = new JButton("Add Prescription");
            JButton editBtn = new JButton("Modify Prescription");
            JButton delBtn = new JButton("Delete Prescription");
            JButton docBtn = new JButton("Generate Document");



            addBtn.addActionListener(e -> showAddPrescriptionDialog());
            editBtn.addActionListener(e -> showModifyPrescriptionDialog());
            delBtn.addActionListener(e -> handleDeletePrescription());
            docBtn.addActionListener(e -> handleGeneratePrescriptionDocument());


            buttons.add(addBtn);
            buttons.add(editBtn);
            buttons.add(delBtn);
            buttons.add(docBtn);

            panel.add(buttons, BorderLayout.SOUTH);
            return panel;
        }
        private void showAddPrescriptionDialog() {
            JDialog dialog = new JDialog(this, "Add Prescription", true);

            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField idField = new JTextField(15);
            JTextField patientIdField = new JTextField(15);
            JTextField clinicianIdField = new JTextField(15);
            JTextField appointmentIdField = new JTextField(15);
            JTextField prescriptionDateField = new JTextField(15);
            JTextField medicationField = new JTextField(15);
            JTextField dosageField = new JTextField(15);
            JTextField frequencyField = new JTextField(15);
            JTextField durationDaysField = new JTextField(15);
            JTextField quantityField = new JTextField(15);
            JTextField instructionsField = new JTextField(15);
            JTextField pharmaciesField = new JTextField(15);
            JTextField statusField = new JTextField(15);
            JTextField dateIssuedField = new JTextField(15);
            JTextField collectionDateField = new JTextField(15);

            panel.add(new JLabel("Prescription ID:")); panel.add(idField);
            panel.add(new JLabel("Patient ID:")); panel.add(patientIdField);
            panel.add(new JLabel("Clinician ID:")); panel.add(clinicianIdField);
            panel.add(new JLabel("Appointment ID:")); panel.add(appointmentIdField);
            panel.add(new JLabel("Prescription Date (yyyy-MM-dd):")); panel.add(prescriptionDateField);
            panel.add(new JLabel("Medication Name:")); panel.add(medicationField);
            panel.add(new JLabel("Dosage:")); panel.add(dosageField);
            panel.add(new JLabel("Frequency:")); panel.add(frequencyField);
            panel.add(new JLabel("Duration Days:")); panel.add(durationDaysField);
            panel.add(new JLabel("Quantity:")); panel.add(quantityField);
            panel.add(new JLabel("Instructions:")); panel.add(instructionsField);
            panel.add(new JLabel("Pharmacies:")); panel.add(pharmaciesField);
            panel.add(new JLabel("Status:")); panel.add(statusField);
            panel.add(new JLabel("Date Issued (yyyy-MM-dd):")); panel.add(dateIssuedField);
            panel.add(new JLabel("Collection Date (yyyy-MM-dd):")); panel.add(collectionDateField);

            JButton save = new JButton("Save");
            JButton cancel = new JButton("Cancel");

            save.addActionListener(e -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);

                String id = idField.getText().trim();
                if (id.isEmpty()) { showErrorMessage("Prescription ID is required"); return; }

                String patientId = patientIdField.getText().trim();
                if (patientId.isEmpty()) { showErrorMessage("Patient ID is required"); return; }

                String clinicianId = clinicianIdField.getText().trim();
                if (clinicianId.isEmpty()) { showErrorMessage("Clinician ID is required"); return; }

                String appointmentId = appointmentIdField.getText().trim();
                if (appointmentId.isEmpty()) { showErrorMessage("Appointment ID is required"); return; }

                Date prescriptionDate;
                Date dateIssued;
                Date collectionDate;
                try {
                    prescriptionDate = sdf.parse(prescriptionDateField.getText().trim());
                    dateIssued = sdf.parse(dateIssuedField.getText().trim());
                    collectionDate = sdf.parse(collectionDateField.getText().trim());
                } catch (Exception ex) {
                    showErrorMessage("Invalid date format (use yyyy-MM-dd)");
                    return;
                }

                String medication = medicationField.getText().trim();
                if (medication.isEmpty()) { showErrorMessage("Medication Name is required"); return; }

                String dosage = dosageField.getText().trim();
                if (dosage.isEmpty()) { showErrorMessage("Dosage is required"); return; }

                String frequency = frequencyField.getText().trim();
                if (frequency.isEmpty()) { showErrorMessage("Frequency is required"); return; }

                int durationDays;
                try {
                    durationDays = Integer.parseInt(durationDaysField.getText().trim());
                } catch (Exception ex) {
                    showErrorMessage("Duration Days must be a number");
                    return;
                }

                String quantity = quantityField.getText().trim();
                if (quantity.isEmpty()) { showErrorMessage("Quantity is required"); return; }

                String instructions = instructionsField.getText().trim();
                if (instructions.isEmpty()) { showErrorMessage("Instructions is required"); return; }

                String pharmacies = pharmaciesField.getText().trim();
                if (pharmacies.isEmpty()) { showErrorMessage("Pharmacies is required"); return; }

                String status = statusField.getText().trim();
                if (status.isEmpty()) { showErrorMessage("Status is required"); return; }

                if (addPrescriptionListener != null) {
                    addPrescriptionListener.onAddPrescription(
                            id, patientId, clinicianId, appointmentId, prescriptionDate,
                            medication, dosage, frequency, durationDays, quantity,
                            instructions, pharmacies, status, dateIssued, collectionDate
                    );
                }

                dialog.dispose();
            });

            cancel.addActionListener(e -> dialog.dispose());

            panel.add(save);
            panel.add(cancel);

            dialog.setContentPane(new JScrollPane(panel));
            dialog.pack();
            dialog.setMinimumSize(new Dimension(620, 520));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
        private void showModifyPrescriptionDialog() {
            int row = prescriptionsTable.getSelectedRow();
            if (row == -1) { showErrorMessage("Select a prescription first."); return; }

            String id = prescriptionsTableModel.getValueAt(row, 0).toString();

            JTextField patientIdField = new JTextField(prescriptionsTableModel.getValueAt(row, 1).toString(), 15);
            JTextField clinicianIdField = new JTextField(prescriptionsTableModel.getValueAt(row, 2).toString(), 15);
            JTextField appointmentIdField = new JTextField(prescriptionsTableModel.getValueAt(row, 3).toString(), 15);
            JTextField prescriptionDateField = new JTextField(prescriptionsTableModel.getValueAt(row, 4).toString(), 15);
            JTextField medicationField = new JTextField(prescriptionsTableModel.getValueAt(row, 5).toString(), 15);
            JTextField dosageField = new JTextField(prescriptionsTableModel.getValueAt(row, 6).toString(), 15);
            JTextField frequencyField = new JTextField(prescriptionsTableModel.getValueAt(row, 7).toString(), 15);
            JTextField durationDaysField = new JTextField(prescriptionsTableModel.getValueAt(row, 8).toString(), 15);
            JTextField quantityField = new JTextField(prescriptionsTableModel.getValueAt(row, 9).toString(), 15);
            JTextField instructionsField = new JTextField(prescriptionsTableModel.getValueAt(row, 10).toString(), 15);
            JTextField pharmaciesField = new JTextField(prescriptionsTableModel.getValueAt(row, 11).toString(), 15);
            JTextField statusField = new JTextField(prescriptionsTableModel.getValueAt(row, 12).toString(), 15);
            JTextField dateIssuedField = new JTextField(prescriptionsTableModel.getValueAt(row, 13).toString(), 15);
            JTextField collectionDateField = new JTextField(prescriptionsTableModel.getValueAt(row, 14).toString(), 15);

            Object[] fields = {
                    "Patient ID:", patientIdField,
                    "Clinician ID:", clinicianIdField,
                    "Appointment ID:", appointmentIdField,
                    "Prescription Date (yyyy-MM-dd):", prescriptionDateField,
                    "Medication Name:", medicationField,
                    "Dosage:", dosageField,
                    "Frequency:", frequencyField,
                    "Duration Days:", durationDaysField,
                    "Quantity:", quantityField,
                    "Instructions:", instructionsField,
                    "Pharmacies:", pharmaciesField,
                    "Status:", statusField,
                    "Date Issued (yyyy-MM-dd):", dateIssuedField,
                    "Collection Date (yyyy-MM-dd):", collectionDateField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Modify Prescription " + id, JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) return;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            Date prescriptionDate, dateIssued, collectionDate;
            try {
                prescriptionDate = sdf.parse(prescriptionDateField.getText().trim());
                dateIssued = sdf.parse(dateIssuedField.getText().trim());
                collectionDate = sdf.parse(collectionDateField.getText().trim());
            } catch (Exception ex) {
                showErrorMessage("Invalid date format (use yyyy-MM-dd)");
                return;
            }

            int durationDays;
            try {
                durationDays = Integer.parseInt(durationDaysField.getText().trim());
            } catch (Exception ex) {
                showErrorMessage("Duration Days must be a number");
                return;
            }

            if (updatePrescriptionListener != null) {
                updatePrescriptionListener.onUpdatePrescription(
                        id,
                        patientIdField.getText().trim(),
                        clinicianIdField.getText().trim(),
                        appointmentIdField.getText().trim(),
                        prescriptionDate,
                        medicationField.getText().trim(),
                        dosageField.getText().trim(),
                        frequencyField.getText().trim(),
                        durationDays,
                        quantityField.getText().trim(),
                        instructionsField.getText().trim(),
                        pharmaciesField.getText().trim(),
                        statusField.getText().trim(),
                        dateIssued,
                        collectionDate
                );
            }
        }
        private void handleDeletePrescription() {
            int row = prescriptionsTable.getSelectedRow();
            if (row == -1) { showErrorMessage("Select a prescription first."); return; }

            String id = prescriptionsTableModel.getValueAt(row, 0).toString();

            if (deletePrescriptionListener != null) {
                deletePrescriptionListener.onDeletePrescription(id);
            }
        }
        private void handleGeneratePrescriptionDocument() {
            int row = prescriptionsTable.getSelectedRow();
            if (row == -1) {
                showErrorMessage("Select a prescription first.");
                return;
            }

            String prescriptionId = prescriptionsTableModel.getValueAt(row, 0).toString();

            Prescription p = controller.getPrescriptionById(prescriptionId);
            if (p == null) {
                showErrorMessage("Prescription not found.");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Prescription Document");
            chooser.setSelectedFile(new java.io.File("Prescription_" + prescriptionId + ".txt"));

            int choice = chooser.showSaveDialog(this);
            if (choice != JFileChooser.APPROVE_OPTION) return;

            java.io.File file = chooser.getSelectedFile();

            try {
                String content = buildPrescriptionDocumentText(p);

                try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
                    fw.write(content);
                }

                showSuccessMessage("Document saved: " + file.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorMessage("Failed to generate document.");
            }
        }
        private String buildPrescriptionDocumentText(Prescription p) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

            String prescDate = (p.getPrescriptionDate() == null) ? "" : sdf.format(p.getPrescriptionDate());
            String issuedDate = (p.getDateIssued() == null) ? "" : sdf.format(p.getDateIssued());
            String collectionDate = (p.getCollectionDate() == null) ? "" : sdf.format(p.getCollectionDate());

            // Optional: pull names from model if you want (patient/clinician lookup)
            // For now this uses IDs and the data inside the prescription.

            return ""
                    + "========================================\n"
                    + "            PRESCRIPTION DOCUMENT        \n"
                    + "========================================\n\n"
                    + "Prescription ID: " + p.getPrescriptionID() + "\n"
                    + "Patient ID:      " + p.getPatientID() + "\n"
                    + "Clinician ID:    " + p.getClinicianID() + "\n"
                    + "Appointment ID:  " + p.getAppointmentID() + "\n\n"
                    + "Prescription Date: " + prescDate + "\n"
                    + "Date Issued:       " + issuedDate + "\n"
                    + "Collection Date:   " + collectionDate + "\n\n"
                    + "Medication:      " + p.getMedicationName() + "\n"
                    + "Dosage:          " + p.getDosage() + "\n"
                    + "Frequency:       " + p.getFrequency() + "\n"
                    + "Duration (days): " + p.getDurationDays() + "\n"
                    + "Quantity:        " + p.getQuantity() + "\n\n"
                    + "Instructions:\n"
                    + p.getInstructions() + "\n\n"
                    + "Pharmacy/Pharmacies:\n"
                    + p.getPharmacies() + "\n\n"
                    + "Status: " + p.getStatus() + "\n\n"
                    + "----------------------------------------\n"
                    + "Signature (Clinician): __________________\n"
                    + "Date: _________________________________\n"
                    + "========================================\n";
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

        public void setAddClinicianListener(ClinicianListener listener) {
            this.addClinicianListener = listener;
        }

        public void setUpdateClinicianListener(ClinicianUpdateListener listener) {
            this.updateClinicianListener = listener;
        }

        public void setDeleteClinicianListener(ClinicianDeleteListener listener) {
            this.deleteClinicianListener = listener;
        }

        public void setAddPrescriptionListener(AddPrescriptionListener l) { this.addPrescriptionListener = l; }
        public void setUpdatePrescriptionListener(UpdatePrescriptionListener l) { this.updatePrescriptionListener = l; }
        public void setDeletePrescriptionListener(DeletePrescriptionListener l) { this.deletePrescriptionListener = l; }


    // ========== Utility Methods ==========

        public void showSuccessMessage(String message) {
            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        public void showErrorMessage(String message) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }



    // Helper to safely format dates (prevents null crashes)
    private String fmtDate(Date d) {
        if (d == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

}