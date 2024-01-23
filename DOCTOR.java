import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DOCTOR extends DatabaseConnect {
    private static final String url = "jdbc:mysql://localhost:3306/healthcare";
    private static final String user_name = "root";
    private static final String pswrd = "root";
    private String password;
    String name;
    int age;
    int s_no;
    int fee;
    String phone_no;
    String email;
    String gender;
    String specialization;
    String consult;
    String address;
    Scanner S = new Scanner(System.in);

    public void signup() {
        // DOCTOR oDoctor= new DOCTOR();
        System.out.print("enter name: ");
        name = S.nextLine();
        System.out.print("enter age: ");
        age = S.nextInt();
        // System.out.println();
        int t = 0;
        while (t == 0) {
            System.out.print("enter phone no: ");
            phone_no = S.next();
            if (phone_no.length() == 10) {
                t++;
            } else {
                System.out.println("enter valid mobile number!");
            }
        }
        // System.out.print("enter email: ");
        // email=S.next();
        int n = 0;
        while (n == 0) {
            System.out.print("enter email: ");
            email = S.next();
            if (email.endsWith("@gmail.com")) {
                n++;
            } else {
                System.out.println("enter valid email id!!");
            }
        }
        System.out.print("enter address: ");
        address = S.next();
        System.out.print("enter specialization: ");
        specialization = S.next();
        System.out.print("enter gender: ");
        gender = S.next();
        System.out.print("enter fee: ");
        fee = S.nextInt();
        n = 0;
        while (n == 0) {
            System.out.print("set password: ");
            String pass = S.next();
            System.out.print("confirm password: ");
            String pass2 = S.next();
            if (pass.equals(pass2)) {
                password = pass;
                break;
            } else {
                System.out.println("not similar!");
            }
        }
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String query = String.format(
                    "INSERT INTO doctor(s_no,name,age,address,phone_no,specialization,password,consault_fee,email_id,gender) VALUES(%o,'%s',%o,'%s','%s','%s','%s',%o,'%s','%s')",
                    s_no, name, age, address, phone_no, specialization, password, fee, email, gender);
            int row = stmt.executeUpdate(query);
            if (row > 0) {
                System.out.println("sign up successfull!");
                System.out.println("\t\tWELCOME TO HEALTHCARE DOCTOR " + name.toUpperCase());
                login();
            } else {
                System.out.println("faild!!");
                System.exit(0);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void login() {
        // DOCTOR doctorObj= new DOCTOR();
        // PATIENT patientObj= new PATIENT();
        char ch = 'y';
        System.out.print("enter name: ");
        name = S.nextLine();
        // S.nextLine();
        System.out.print("enter password: ");
        password = S.next();
        S.nextLine();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM doctor WHERE name = '" + name + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("login successfull!!");
                System.out.println("\t\tWELCOME BACK TO HEALTHCARE DOCTOR " + name.toUpperCase());
                choice();
                ch = 'n';

            } else {
                System.out.println("Login failed!");
                System.out.println("are you forget user name/ password(y/n)?");
                ch = S.next().charAt(0);
                int n = 0;
                if (ch == 'y' || ch == 'Y') {
                    while (n == 0) {
                        System.out.print("enter id: ");
                        s_no = S.nextInt();
                        System.out.print("enter email id: ");
                        email = S.next();
                        query = "SELECT * FROM doctor WHERE s_no = '" + s_no + "' AND email_id= '" + email + "'";
                        ResultSet rs2 = stmt.executeQuery(query);
                        if (rs2.next()) {
                            System.out.println("your user name: " + rs2.getString("name"));
                            System.out.println("your password: " + rs2.getString("password"));
                        } else {
                            System.out.println("enter correct data!");
                        }
                    }
                } else {
                    System.out.println("do you want to signup(y/n)");
                    char ch2 = S.next().charAt(0);
                    S.nextLine();
                    if (ch2 == 'y' || ch2 == 'Y') {
                        signup();
                    } else {
                        System.out.println("do you want to continue as doctor(y/n): ");
                        ch = S.next().charAt(0);
                        S.nextLine();
                        while (ch == 'y' || ch == 'Y') {
                            if (ch == 'y' || ch == 'Y') {
                                login();
                                ch = 'n';
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void choice() {
        System.out.println("enter 1 for view profile");
        System.out.println("enter 2 for see appoinment");
        // System.out.println("enter 3 for create account!");
        System.out.println("enter 3 for logout!");
        // System.out.println("enter 2 for view doctor list");
        // System.out.println("enter 3 for choose doctor ");
        // System.out.println(Integer.parseInt(S.nextLine()));
        // Integer.parseInt(S.nextLine()); name,password
        int n = S.nextInt();
        switch (n) {
            case 1:
                viewProfile(name);
                break;
            case 2:
                // System.out.println(name);
                viewAppointment(name);
                break;
            case 3:
                System.out.println("you logout !");
                int n2 = 1;
                if (n2 == 1) {
                    break;
                }
                break;

            default:
                System.out.println("invalid choice!");
                choice();
                break;
        }
    }

    public void viewProfile(String name) {
        System.out.println(name);
        System.out.println(password);
        // DOCTOR doctorObj= new DOCTOR();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM doctor WHERE name = '" + name + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(
                    "_______________________________________________________________________________________________________________");
            if (rs.next()) {
                // System.out.println("login successfull!!");
                // System.out.println("\t\tWELCOME BACK TO HEALTHCARE DEAR " +
                // name.toUpperCase());

                // Display doctor information
                System.out.println("Doctor details:");
                System.out.println();
                System.out.println("s_no: " + rs.getInt("s_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("address: " + rs.getString("address"));
                System.out.println("email id: " + rs.getString("email_id"));
                System.out.println("phone no.: " + rs.getString("phone_no"));
                System.out.println("gender: " + rs.getString("gender"));
                System.out.println("specialization: " + rs.getString("specialization"));
                System.out.println();
                System.out.println(
                        "_______________________________________________________________________________________________________________");
                // // Add other fields here

                System.out.println("enter 1 to change password");
                System.out.println("enter 2 to change user name");
                System.out.println("enter 3 to change address");
                System.out.println("enter 4 to change email id");
                System.out.println("enter 5 to change specialization");
                System.out.println("enter 6 for change phone no");
                int n1 = S.nextInt();
                switch (n1) {
                    case 1:
                        int n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            // System.out.println("password: " + rs.getString("password"));
                            if (currentPassword.equals(rs.getString("password"))) {
                                changePassword(currentPassword, name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    case 2:
                        n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs.getString("password"))) {
                                changeName(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    case 3:
                        n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs.getString("password"))) {
                                changeAddress(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    case 4:
                        n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs.getString("password"))) {
                                changeEmail(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    case 5:
                        n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs.getString("password"))) {
                                changeSpecialization(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    case 6:
                        n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs.getString("password"))) {
                                changePhoneNo(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    default:
                        System.out.println("invalid choice!!");
                        break;
                }
            } else {
                System.out.println("Login failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePassword(String currentPassword, String name) {
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                System.out.print("enter new password: ");
                password = S.next();
                if (!currentPassword.equals(password)) {
                    String query1 = "UPDATE doctor set password= '" + password + "' WHERE name='" + name + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("password changed!!");
                    } else {
                        System.out.println("not change!");
                    }
                    n++;
                } else {
                    System.out.println("password is similar to previous password!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changeName(String name2) {
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                System.out.print("enter new user name: ");
                name = S.next();
                if (!name.equals(name2)) {
                    String query1 = "UPDATE doctor set name= '" + name + "' WHERE name='" + name2 + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("user name changed!");
                    } else {
                        System.out.println("user name is not changed!");
                    }
                    // int rowsUpdated = stmt.executeUpdate();
                    n++;
                } else {
                    System.out.println("user name is similar to previous password!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changeAddress(String name) {
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                System.out.print("enter new address: ");
                String add = S.next();
                if (!add.equals(address)) {
                    String query1 = "UPDATE doctor set address= '" + add + "' WHERE name='" + name + "' AND password='"
                            + password + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("address changed!");
                    } else {
                        System.out.println("adress is not changed!");
                    }
                    address = add;
                    // int rowsUpdated = stmt.executeUpdate();
                    n++;
                } else {
                    System.out.println("address is similar to previous password!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changeEmail(String name) {
        String email2 = "";
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                int t = 0;
                while (t == 0) {
                    System.out.print("enter email: ");
                    email2 = S.next();
                    if (email2.endsWith("@gmail.com")) {
                        t++;
                    } else {
                        System.out.println("enter valid email id!!");
                    }
                }
                // System.out.print("enter new Email address: ");
                // String email2= S.next();
                if (!email2.equals(email)) {
                    String query1 = "UPDATE doctor set email_id= '" + email2 + "' WHERE name='" + name
                            + "' AND password='" + password + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("email address changed!");
                    } else {
                        System.out.println("email address is not changed!");
                    }
                    email = email2;
                    // int rowsUpdated = stmt.executeUpdate();
                    n++;
                } else {
                    System.out.println("email address is similar to previous password!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changeSpecialization(String name) {
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                System.out.print("enter specialization: ");
                String spec = S.next();
                if (!spec.equals(specialization)) {
                    String query1 = "UPDATE doctor set specialization= '" + spec + "' WHERE name='" + name
                            + "' AND password='" + password + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("specialization changed!");
                    } else {
                        System.out.println("specialization is not changed!");
                    }
                    specialization = spec;
                    // int rowsUpdated = stmt.executeUpdate();
                    n++;
                } else {
                    System.out.println("specialization is similar to previous password!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changePhoneNo(String name) {
        String phone = "";
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                int t = 0;
                while (t == 0) {
                    System.out.print("enter phone no: ");
                    phone = S.next();
                    if (phone.length() == 10) {
                        t++;
                    } else {
                        System.out.println("enter valid mobile number!");
                    }
                }
                // System.out.println("enter new phone no: ");
                // String phone= S.next();
                if (!phone.equals(phone_no)) {
                    String query1 = "UPDATE doctor set phone_no= '" + phone + "' WHERE name='" + name
                            + "' AND password='" + password + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("phone no changed!");
                    } else {
                        System.out.println("phone no is not changed!");
                    }
                    phone_no = phone;
                    // int rowsUpdated = stmt.executeUpdate();
                    n++;
                } else {
                    System.out.println("email address is similar to previous password!");
                }
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        choice();
    }

    // public void viewListDoctor(String name1) {
    // //PATIENT obj = new PATIENT();
    // try {
    // Connection conn = DriverManager.getConnection(url, user_name, pswrd);
    // Statement stmt = conn.createStatement();
    // String query = "SELECT * FROM DOCTOR";
    // ResultSet rs = stmt.executeQuery(query);
    // while (rs.next()) {
    // // Display values
    // System.out.print("s_no: " + rs.getInt("s_no"));
    // System.out.print(", \tname: " + rs.getString("name"));
    // // System.out.print(", Age: " + rs.getInt("age"));
    // System.out.print(",\t address: " + rs.getString("address"));
    // // System.out.print(", phone no: " + rs.getString("phone_no"));
    // System.out.print(", \tspecialization: " + rs.getString("specialization"));
    // System.out.println();
    // }
    // System.out.println("do you want to choose doctor(y/n): ");
    // char ch3 = S.next().charAt(0);
    // if (ch3 == 'y') {
    // chooseDoctor(name1);
    // }
    // } catch (SQLException e) {
    // System.out.println(e.getMessage());
    // }
    // choice();
    // }

    public void viewAppointment(String name1) {
        int fees = 0;
        S.nextLine();
        System.out.println("enter patient name: ");
        String p_name = S.nextLine();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM appoint WHERE doctor_name = '" + name1 + "' AND patient_name= '" + p_name
                    + "'";
            ResultSet rs2 = stmt.executeQuery(query);
            if (rs2.next()) {
                System.out.println("patient name: " + rs2.getString("patient_name"));
                String p = rs2.getString("patient_name");
                System.out.println("disease: " + rs2.getString("disease"));
                System.out.println("date : " + rs2.getString("date_of_choose"));
                System.out.println("appointment date: " + rs2.getString("appointment_date"));
                // String query3 = "SELECT attent FROM attend WHERE doctor_name = '" + name1 +
                // "' AND patient_name= '"+ p_name+"'";
                // ResultSet rs4 = stmt.executeQuery(query3);
                // if(rs4.next()) {
                // String flag=rs4.getString("attent");
                // if (!flag.equals("yes")) {
                String query3 = "SELECT consault_fee FROM doctor WHERE name= '" + name1 + "'";
                ResultSet rs4 = stmt.executeQuery(query3);
                System.out.println();
                if (rs4.next()) {
                    System.out.print("have you attended the patient: " + p + " (yes/no)");
                    String atten = S.next();
                    System.out.println("you can't make changes if you attended");
                    S.nextLine();
                    if (atten.equals("yes")) {
                        System.out.println("descibe the desiease: ");
                        consult = S.nextLine();
                        fees = rs4.getInt("consault_fee");
                        System.out.print("do you want to charge any extra fee: ");
                        char ch = S.next().charAt(0);
                        if (ch == 'y' || ch == 'Y') {
                            System.out.print("enter fees: ");
                            fees = fees + S.nextInt();
                        }
                        String query2 = String.format(
                                "INSERT INTO ATTEND(s_no, doctor_name, patient_name, attent,report,fee) VALUES(%o, '%s', '%s', '%s','%s',%o)",
                                s_no, name1, p, atten, consult, fees);
                        int row = stmt.executeUpdate(query2);
                        if (row > 0) {
                            System.out.println("thank you!");
                        } else {
                            System.out.println("failed!");
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        choice();
    }
}