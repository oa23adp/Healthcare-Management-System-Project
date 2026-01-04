
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class HealthcareView extends JFrame {
    private HealthcareController controller;
    private JTabbedPane tabbedPane;

    //Patient panel components
    private JTable patientsTable;
    private DefaultTableModel patientsTableModel;




    //Listener References
    private PatientListener addPatientListener;


    public HealthcareView() {
        setTitle("Healthcare Management System - MVC Architecture");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void loadPatientsData() {

        //Load patients data
        ArrayList <Patient> patients = controller.getAllPatients();
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getDateOfBirth(),
                    patient.getNhsNumber(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getPostCode(),
                    patient.getEmergencyContactName(),
                    patient.getEmergencyContactNo(),
                    patient.getGpId()
            };
            patientsTableModel.addRow(row);
        }
    }


    private void initComponents() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Patients",createPatientsPanel());

        add(tabbedPane);

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

        JLabel titleLabel = new JLabel("Authors");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Patient ID", "First Name", "Last Name", "Date of Birth","NHS Number","Gender","Phone Number",
                            "Email","Address","Postcode","Emergency contact name","Emergency contact no","GP ID"};
        patientsTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientsTable = new JTable(patientsTableModel);
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Author");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddPatientDialog();
            }
        });

        buttonsPanel.add(addButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }
    private void showAddPatientDialog() {
        JDialog dialog = new JDialog(this, "Add Patient", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Date of Birth:"));
        JTextField dobField = new JTextField();
        panel.add(dobField);

        panel.add(new JLabel("NHS Number:"));
        JTextField nhsNoField = new JTextField();
        panel.add(nhsNoField);

        panel.add(new JLabel("Gender:"));
        JTextField genderField = new JTextField();
        panel.add(genderField);

        panel.add(new JLabel("Phone Number:"));
        JTextField phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Postcode:"));
        JTextField postcodeField = new JTextField();
        panel.add(postcodeField);

        panel.add(new JLabel("Emergency Contact Name:"));
        JTextField ecNameField = new JTextField();
        panel.add(ecNameField);

        panel.add(new JLabel("Emergency Contact Number:"));
        JTextField ecNoField = new JTextField();
        panel.add(ecNoField);

        panel.add(new JLabel("GP Id:"));
        JTextField gpIdField = new JTextField();
        panel.add(gpIdField);


        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String email = .getText();

                if (name.trim().isEmpty()) {
                    showErrorMessage("Name is required");
                    return;
                }

                if (addAuthorListener != null) {
                    addAuthorListener.onAddAuthor(name, email);
                }

                ArrayList<Author> allAuthors = controller.getAllAuthors();
                Author newAuthor = allAuthors.get(allAuthors.size() - 1);

                Object[] row = {
                        newAuthor.getAuthorId(),
                        newAuthor.getName(),
                        newAuthor.getEmail()
                };
                authorsTableModel.addRow(row);

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

        dialog.add(panel);
        dialog.setVisible(true);
    }
    }
}
