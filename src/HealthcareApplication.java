import javax.swing.*;

public class HealthcareApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeApplication();
            }
        });
    }

    private static void initializeApplication() {
        System.out.println("Bookshop Management System Starting...");

        // Create the Model
        HealthcareModel model = new HealthcareModel();
        System.out.println("Model created and data loaded.");

        // Create the View
        HealthcareView view = new HealthcareView();
        System.out.println("View created.");

        // Create the Controller and wire Model and View together
        HealthcareController controller = new HealthcareController(model, view);
        System.out.println("Controller created.");

        // Set controller in view
        view.setController(controller);
        System.out.println("MVC components wired together.");

        // Display the application
        view.setVisible(true);

        System.out.println("=====================================");
        System.out.println("Healthcare Management System started!");
        System.out.println("Architecture: MVC Pattern");
        System.out.println("Order Management: Singleton Pattern");
        System.out.println("=====================================");
    }
}
