import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ADMIN extends DatabaseConnect {
    private static final String url = "jdbc:mysql://localhost:3306/healthcare";
    private static final String user_name = "root";
    private static final String pswrd = "root";
    final String name = "varsha jat";
    final private long id = 12341l;
    final private String password = "v@123";
    Scanner S = new Scanner(System.in);

    public void login() {
        // ADMIN obj= new ADMIN();
        // Scanner S= new Scanner(System.in);
        System.out.println("welcome admin  please enter your detail: ");
        System.out.print("enter name: ");
        String name1 = S.nextLine();
        if (name.equals(name1)) {
            System.out.print("enter id : ");
            long id1 = S.nextLong();
            if (id == id1) {
                System.out.print("enter password: ");
                String password1 = S.next();
                if (password.equals(password1)) {
                    System.out.println("\t\t\twelocome to HEALTH CARE Admin: " + name.toUpperCase());
                    choice();
                } else {
                    System.out.println("wrong password!");
                }
            } else {
                System.out.println("no such id!");
            }
        } else {
            System.out.println("no user exits!");
        }
    }

    public void choice() {
        System.out.println("enter 1 for view doctors list");
        System.out.println("enter 2 for view patient list");
        System.out.println("enter 3 for see appointments");
        System.out.println("enter 4 for remove doctor");
        System.out.println("enter 5 for see attended list");
        System.out.println("enter 6 for log out!");
        switch (S.nextInt()) {
            case 1:
                viewListDoctor();
                break;
            case 2:
                viewListPatient();
                break;
            case 3:
                viewAppointment();
                break;
            case 4:
                removeDoctor();
                break;
            case 5:
                attend();
                break;
            case 6:
                System.out.println("you logout from admin!!");
                int n2 = 1;
                if (n2 == 1) {
                    break;
                }
                break;
            default:
                System.out.println("invalid choice!!");
                choice();
                break;
        }
    }

    public void viewListDoctor() {
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM DOCTOR";
            System.out.println(
                    "_________________________________________________________________________________________________________");
                    System.out.println();
                    System.out.println("DOCTOR LIST :");
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println();
                // Display values
                System.out.print("s_no: " + rs.getInt("s_no"));
                System.out.print(",\t name: " + rs.getString("name"));
                System.out.print(",\t Age: " + rs.getInt("age"));
                System.out.print(",\t address: " + rs.getString("address"));
                System.out.print(",\t phone no: " + rs.getString("phone_no"));
                System.out.print(",\t specialization: " + rs.getString("specialization"));
                System.out.println();
            }
            System.out.println(
                    "_________________________________________________________________________________________________________");
            System.out.println();
            choice();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewListPatient() {
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            System.out.println(
                    "_________________________________________________________________________________________________________");
            System.out.println();
            System.out.println("PATIENT LIST : ");
            String query = "SELECT * FROM patient";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println();
                // Display values
                System.out.print("s_no: " + rs.getInt("s_no"));
                System.out.print(",\t name: " + rs.getString("name"));
                System.out.print(",\t Age: " + rs.getInt("age"));
                System.out.print(",\t address: " + rs.getString("address"));
                // System.out.print(", phone no: " + rs.getString("phone_no"));
                // System.out.print(", specialization: " + rs.getString("specialization"));
                System.out.println();
            }
            System.out.println(
                    "_________________________________________________________________________________________________________");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        choice();
    }

    public void viewAppointment() {
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            System.out.println(
                    "_________________________________________________________________________________________________________");
                    System.out.println();
                    System.out.println("APPOINTMENT DETAILS : ");
                    String query = "SELECT * FROM appoint";
            ResultSet rs2 = stmt.executeQuery(query);

            while (rs2.next()) {
                System.out.println();
                System.out.println("s. no.: " + rs2.getInt("s_no"));
                System.out.println("patient name: " + rs2.getString("patient_name"));
                System.out.println("doctor name: " + rs2.getString("doctor_name"));
                System.out.println("disease: " + rs2.getString("disease"));
                System.out.println("date : " + rs2.getString("date_of_choose"));
                System.out.println("appointment date: " + rs2.getString("appointment_date"));
            }
            System.out.println();
            System.out.println(
                    "_________________________________________________________________________________________________________");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        choice();
    }

    public void removeDoctor() {
        System.out.println();
        System.out.println("REMOVE DOCTOR : ");
        System.out.println("enter s_no of doctor to remove!");
        int s_no = S.nextInt();
        System.out.println("are you sure you want to delete(y/n): " + s_no);
        char ch = S.next().charAt(0);
        if (ch == 'y' || ch == 'Y') {
            try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                    Statement stmt = conn.createStatement()) {
                String query = "DELETE FROM DOCTOR WHERE s_no='" + s_no + "'";
                int row = stmt.executeUpdate(query);
                if (row > 0) {
                    System.out.println("doctor deleted!!");
                } else {
                    System.out.println("row isn't deleted!!");
                }
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        choice();
    }

    public void attend() {
        System.out.println();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            System.out.println(
                    "_________________________________________________________________________________________________________");
                    System.out.println("ATTEND DETAILS : ");
                    System.out.println();
            String query = "SELECT * FROM attend";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println();
                System.out.println("s no: " + rs.getInt("s_no"));
                System.out.println("patient name: " + rs.getString("patient_name"));
                System.out.println("doctor name: " + rs.getString("doctor_name"));
                System.out.println("attented: " + rs.getString("attent"));
                System.out.println("report: " + rs.getString("report"));
                System.out.println("fee: " + rs.getInt("fee"));
                System.out.println("feedback: " + rs.getString("feedback"));
                 System.out.println("fee:Varsha jat is a good girl ");
                System.out.println();
            }
            System.out.println(
                    "_________________________________________________________________________________________________________");
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        choice();
    }
}
