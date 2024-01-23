
// E-HealthCare-Management-System is a console based application which is built using java.
// This application helps in management of Patients, doctors, admin in a easy and comfortable way.using 
// this Application patients can quickly Sign up, Login, view his/her profile, view doctors, book Appointment,
//  view Report, choose doctor, view Appointments, give feedback, pay online and logout. Admin can add Doctors,
//  view patients list, view Doctors list,remove doctors, see feedback given by patients,view reports,logout.
//  Doctor can login, view profile, viewAppointments, Attend Patients and logout.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PATIENT extends DatabaseConnect {
    private static final String url = "jdbc:mysql://localhost:3306/healthcare";
    private static final String user_name = "root";
    private static final String pswrd = "root";
    int s_no;
    String name;
    String phoneno;
    String email;
    String gender;
    String address;
    String doctor_name;
    String disease;
    private String password;

    public void setpassword(String password) {
        this.password = password;
    }

    public String getpassword() {
        return password;
    }

    Scanner S = new Scanner(System.in);

    public void signup() {
        // Scanner S = new Scanner(System.in);
        System.out.print("enter name: ");
        String name = S.nextLine();
        int t = 0;
        while (t == 0) {
            System.out.print("enter phone no: ");
            phoneno = S.next();
            if (phoneno.length() == 10) {
                t++;
            } else {
                System.out.println("enter valid mobile number!");
            }
        }
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
        System.out.print("enter age: ");
        int age = S.nextInt();
        // System.out.println();
        System.out.print("enter address: ");
        address = S.next();
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
        System.out.println("enter gender: ");
        gender = S.next();
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String checkQuery = "SELECT * FROM patient WHERE name = '" + name + "' AND password= '" + password + "'";
            ResultSet checkResult = stmt.executeQuery(checkQuery);
            if (checkResult.next()) {
                System.out.println("User already exists! Please log in instead.");
                S.nextLine();
                login();
                return;
            }
            // String query=String.format("INSERT INTO
            // patient(s_no,name,age,address,password,email_id,phone_no)
            // VALUES(%o,'%s',%o,'%s','%s','%s',%o)",s_no,name,age,address,password,email,phoneno);
            String query = String.format(
                    "INSERT INTO patient(s_no,name,age,address,password,email_id,phone_no,gender)VALUES(%o,'%s',%o,'%s','%s','%s','%s','%s')",
                    s_no, name, age, address, password, email, phoneno, gender);
            int row = stmt.executeUpdate(query);
            if (row > 0) {
                System.out.println("\t\tWELCOME TO HEALTHCARE " + name.toUpperCase());
                S.nextLine();
                System.out.println("login to continue app!");
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
        // PATIENT obj2 = new PATIENT();
        char ch = 'y';

        System.out.print("enter name: ");
        name = S.nextLine();
        // S.nextLine();
        System.out.print("enter password: ");
        password = S.next();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM patient WHERE name = '" + name + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                name = rs.getString("name");
                System.out.println("login successfull!!");
                System.out.println("\t\tWELCOME BACK TO HEALTHCARE DEAR " + name.toUpperCase());
                // System.out.println(name);
                choice();
                ch = 'n';
            } else {
                System.out.println("Login failed!");

                // forget password!!

                System.out.println("forget username/ password(y/n)?");
                ch = S.next().charAt(0);
                int n = 0;
                if (ch == 'y' || ch == 'Y') {
                    while (n == 0) {
                        System.out.print("enter id: ");
                        s_no = S.nextInt();

                        System.out.print("enter email id: ");
                        email = S.next();
                        query = "SELECT * FROM patient WHERE s_no = '" + s_no + "' AND email_id= '" + email + "'";
                        ResultSet rs2 = stmt.executeQuery(query);
                        if (rs2.next()) {
                            n++;
                            // name=rs2.getString("name");
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
                        System.out.println("do you want to continue as patient(y/n): ");
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

                // obj2.login();
                // "' AND email_id= '" + email +
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void choice() {
        // System.out.println(name);
        // PATIENT obj2 = new PATIENT();
        System.out.println("enter 1 for view profile");
        System.out.println("enter 2 for view doctor list");
        System.out.println("enter 3 to view appointment: ");
        System.out.println("enter 4 for report");
        // System.out.println("enter 5 for account login/sign up!");
        System.out.println("enter 5 for log out!");
        // System.out.println("enter 3 for choose doctor ");
        int n = S.nextInt();
        switch (n) {
            case 1:
                viewProfile(name, password);
                break;
            case 2:
                viewListDoctor(name);
                break;
            case 3:
                // System.out.println(rs.getString("name"));
                viewAppointment(name);
                break;
            case 4:
                // System.out.println("this name: "+ name);
                // System.out.println("name: "+ name);
                report();
                break;
            case 5:
                System.out.println("-----------------------you log out as ------------------------");
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

    public void viewProfile(String name, String password) {
        // PATIENT obj2 = new PATIENT();
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query2 = "SELECT * FROM patient WHERE name = '" + name + "' AND password = '" + password + "'";
            ResultSet rs2 = stmt.executeQuery(query2);
            System.out
                    .println("______________________________________________________________________________________");
            if (rs2.next()) {
                System.out.println();
                System.out.println("s_no: " + rs2.getInt("s_no"));
                System.out.println("Name: " + rs2.getString("name"));
                System.out.println("Age: " + rs2.getInt("age"));
                System.out.println("address: " + rs2.getString("address"));
                System.out.println("email id: " + rs2.getString("email_id"));
                System.out.println("phone no.: " + rs2.getString("phone_no"));
                System.out.println("gender: " + rs2.getString("gender"));
                System.out.println();
                // // Add other fields here "' AND password = '" + password +
                System.out.println(
                        "______________________________________________________________________________________");
                System.out.println("enter 1 to change password");
                System.out.println("enter 2 to change user name");
                System.out.println("enter 3 to change address");
                System.out.println("enter 4 to change email id");
                System.out.println("enter 5 for change phone no");
                System.out.println("enter 6 for no changes!");
                int n1 = S.nextInt();
                switch (n1) {
                    case 1:
                        int n = 0;
                        while (n == 0) {
                            System.out.print("enter current password: ");
                            String currentPassword = S.next();
                            if (currentPassword.equals(rs2.getString("password"))) {
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
                            if (currentPassword.equals(rs2.getString("password"))) {
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
                            if (currentPassword.equals(rs2.getString("password"))) {
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
                            if (currentPassword.equals(rs2.getString("password"))) {
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
                            if (currentPassword.equals(rs2.getString("password"))) {
                                changePhoneNo(name);
                                n++;
                            } else {
                                System.out.println("incorrect password!");
                            }
                        }
                        break;
                    default:
                        
                        break;
                }
            } else {
                System.out.println("Login failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void changePassword(String currentPassword, String name) {
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            while (n == 0) {
                System.out.println("enter new password: ");
                password = S.next();
                if (!currentPassword.equals(password)) {
                    String query1 = "UPDATE patient set password= '" + password + "' WHERE name='" + name + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("password changed!");
                    } else {
                        System.out.println("password is not changed!");
                    }
                    // int rowsUpdated = stmt.executeUpdate();
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
                System.out.println("enter new user name: ");
                name = S.next();
                if (!name.equals(name2)) {
                    String query1 = "UPDATE patient set name= '" + name + "' WHERE name='" + name2 + "'";
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
                System.out.println("enter new address: ");
                String add = S.next();
                if (!add.equals(address)) {
                    String query1 = "UPDATE patient set address= '" + add + "' WHERE name='" + name + "' AND password='"
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
        // System.out.println("hello "+ name+ " password: "+password);
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            int n = 0;
            String email2 = "";
            while (n == 0) {
                int t = 0;
                while (t == 0) {
                    System.out.print("enter email: ");
                    email2 = S.next();
                    if (email.endsWith("@gmail.com")) {
                        t++;
                    } else {
                        System.out.println("enter valid email id!!");
                    }
                }
                // System.out.println("enter new Email address: ");
                // String email2= S.next();
                if (!email2.equals(email)) {
                    String query1 = "UPDATE patient set email_id= '" + email2 + "' WHERE name='" + name
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
                if (!phone.equals(phoneno)) {
                    String query1 = "UPDATE patient set phone_no= '" + phone + "' WHERE name='" + name
                            + "' AND password='" + password + "'";
                    int row = stmt.executeUpdate(query1);
                    if (row > 0) {
                        System.out.println("phone no changed!");
                    } else {
                        System.out.println("phone no is not changed!");
                    }
                    phoneno = phone;
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

    public void viewListDoctor(String name1) {
        // PATIENT obj = new PATIENT();
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM DOCTOR";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Display values
                System.out.print("s_no: " + rs.getInt("s_no"));
                System.out.print(", \tname: " + rs.getString("name"));
                // System.out.print(", Age: " + rs.getInt("age"));
                System.out.print(",\t address: " + rs.getString("address"));
                // System.out.print(", phone no: " + rs.getString("phone_no"));
                System.out.print(", \tspecialization: " + rs.getString("specialization"));
                System.out.print("\tfees : "+ rs.getInt("consault_fee"));
                System.out.println();
            }
            System.out.println("do you want to choose doctor(y/n): ");
            char ch3 = S.next().charAt(0);
            if (ch3 == 'y') {
                chooseDoctor(name1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void chooseDoctor(String name1) {
        // PATIENT obj = new PATIENT();
        S.nextLine();
        System.out.print("enter disease: ");
        disease = S.nextLine();
        
        System.out.print("enter specialist for sorting : ");
        String spec = S.nextLine();
        try {
            Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM DOCTOR WHERE specialization= '" + spec + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Display values
                System.out.print("s_no: " + rs.getInt("s_no"));
                System.out.print(", \tname: " + rs.getString("name"));
                // System.out.print(", Age: " + rs.getInt("age"));
                System.out.print(",\t address: " + rs.getString("address"));
                // System.out.print(", phone no: " + rs.getString("phone_no"));
                System.out.print(", \tspecialization: " + rs.getString("specialization"));
                System.out.print("\tfees : "+ rs.getInt("consault_fee"));
                System.out.println();
            }
            String docName;
            System.out.print("choose doctor by s. no: ");
            int choose_S_No = S.nextInt();
            query = "SELECT * FROM DOCTOR WHERE s_no= '" + choose_S_No + "'";
            ResultSet rs1 = stmt.executeQuery(query);
            while (rs1.next()) {
                docName = rs1.getString("name");
                System.out.print("name: " + rs1.getString("name"));
                System.out.print(",\t| address: " + rs1.getString("address"));
                System.out.print(",\t| phone no: " + rs1.getString("phone_no"));
                System.out.print(",\t| specialization: " + rs1.getString("specialization"));
                System.out.print("\t| fee: "+ rs1.getInt("consault_fee"));
                S.nextLine();
                System.out.println();
                String time = "";
                int t = 0;
                while (t == 0) {
                    System.out.print("Enter date for appointment (in the format yyyy-MM-dd HH:mm:ss): ");
                    time = S.nextLine();

                    // Define the date format you expect
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {

                        // Parse the user input into a Date object
                        Date parsedDate = dateFormat.parse(time);
                        t++;
                        // Print the parsed date
                        System.out.println("your appointment  Date: " + parsedDate);
                    } catch (ParseException e) {
                        System.out.println("Error parsing date. Please enter the date in the correct format.");
                        System.out.println(e.getMessage());

                    }
                }
                // String query3=String.format("INSERT INTO
                // appoint(s_no,patient_name,disease,doctor_name,appointment_date)
                // VALUES(%o,'%s','%s','%s','%s')",s_no,name1,
                // disease,rs1.getString("name"),"SELECT FORMAT(GETDATE(), 'dd MMM yyyy
                // HH:mm:ss')");
                String query3 = String.format(
                        "INSERT INTO appoint(s_no, patient_name, disease, doctor_name, date_of_choose, appointment_date) VALUES(%d, '%s', '%s', '%s', NOW(),'%s')",
                        s_no, name1, disease, docName, time);

                int row = stmt.executeUpdate(query3);
                if (row > 0) {
                    System.out.println("appointment done!");
                } else {
                    System.out.println("failed!!");
                }

                System.out.println();
                System.out.println("do you want to see appointment(y/n): ");
                char ch = S.next().charAt(0);
                if (ch == 'y' || ch == 'Y') {
                    viewAppointment(name1);
                }
            }
            // "INSERT INTO
            // patient(s_no,name,age,address,password,email_id,phone_no)VALUES(%o,'%s',%o,'%s','%s','%s','%s')",
            // s_no, name, age, address, password, email, phoneno);
            choice();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void viewAppointment(String name1) {
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            System.out.println();
            System.out.println("YOU APPOINTMENTS:");
            // only one doctor appoiment list is visible!
            String query = "SELECT * FROM appoint WHERE patient_name= '" + name1 + "'";
            ResultSet rs2 = stmt.executeQuery(query);
            while (rs2.next()) {
                System.out.println("____________________________________________________________________________________________________________________");
                System.out.println("s. no.: " + rs2.getInt("s_no"));
                System.out.println("doctor name: " + rs2.getString("doctor_name"));
                doctor_name=rs2.getString("doctor_name");
                System.out.println("disease: " + rs2.getString("disease"));
                //System.out.print("\tfees : "+ rs2.getInt("consault_fee"));
                System.out.println("date : " + rs2.getString("date_of_choose"));
                System.out.println("appointment date: " + rs2.getString("appointment_date"));
                System.out.println("____________________________________________________________________________________________________________________");
 
            }
            String query2 = "SELECT * FROM attend WHERE patient_name= '" + name1 + "'";
                ResultSet rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    System.out.println("your report number with "+ rs.getString("doctor_name")+" is: " + rs.getString("s_no"));
                }
                rs.close();
                System.out.println();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        choice();
    }

    public void report() {
        int no=0;
        String fees_paid="";
        // System.out.println(name1);
        String doctor_name = "";
        int fee = 0;
        System.out.println("do you remenber you appointment no(y/n): ");
        char ch = S.next().charAt(0);
        if (ch == 'y' || ch == 'Y') {

            System.out.println("enter report no: ");
            no = S.nextInt();
        } else {
            System.out.println("view report no from appointment");
        }

        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM  attend WHERE s_no= '" + no + "' AND patient_name='" + name + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("-----------------------YOUR REPORT IS READY---------------------------");
                System.out.println();
                System.out.println("S_no.: " + rs.getInt("s_no"));
                System.out.println();
                System.out.println("patient name: " + rs.getString("patient_name"));
                name = rs.getString("patient_name");
                System.out.println();
                System.out.println("doctor name: " + rs.getString("doctor_name"));
                doctor_name = rs.getString("doctor_name");
                System.out.println();
                System.out.println("description: " + rs.getString("report"));
                System.out.println();
                //System.out.println("fees to pay:  " + rs.getInt("fee"));
                fee = rs.getInt("fee");
                fees_paid=rs.getString("fees_paid");
                System.out.println(fees_paid);
                System.out.println();
                String query2 = "SELECT * FROM appoint WHERE patient_name= '" + name + "'";
                rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    System.out.println("appointment date: " + rs.getString("appointment_date"));
                    System.out.println();
                    System.out.println("disease: " + rs.getString("disease"));
                    System.out.println();
            //         String checkQuery = "SELECT * FROM patient WHERE name = '" + name + "' AND password= '" + password + "'";


            // ResultSet checkResult = stmt.executeQuery(checkQuery);
            // if (checkResult.next()) {
            //     System.out.println("User already exists! Please log in instead.");
            //     S.nextLine();

                    
                    rs.close();
                    int n = 0;
                    String query3 = "SELECT * FROM patient WHERE name = '" + name + "' AND password = '" + password
                            + "'";
                    ResultSet rs2 = stmt.executeQuery(query3);
                    /*Connection conn = DriverManager.getConnection(url, user_name, pswrd);
            Statement stmt = conn.createStatement();
            String checkQuery = "SELECT * FROM patient WHERE name = '" + name + "' AND password= '" + password + "'";
            ResultSet checkResult = stmt.executeQuery(checkQuery);
            if (checkResult.next()) {
                System.out.println("User already exists! Please log in instead.");
                S.nextLine();
                login();
                return;
            } */
                    if(!(fees_paid.equals("yes"))){
                        System.out.println("pay fees: ");
                        if (rs2.next()) {
                            while (n == 0) {
                                System.out.print("enter current password: ");
                                String currentPassword = S.next();
                                if (currentPassword.equals(rs2.getString("password"))) {
                                    payOnline(fee, doctor_name);
                                    n++;
                                } else {
                                    System.out.println("incorrect password!");
                                }
                            }
                            rs2.close();
                            
                        }
                    }
                    System.out.println("do you want to give feedback(y/n)");
                            ch = S.next().charAt(0);
                            if (ch == 'y' || ch == 'Y') {
                                feedback(name, doctor_name);
                            }
                }
            } else {
                System.out.println("report is not available!\n please try after some time!!!");
            }

            System.out.println();

            System.out.println("---------------THANK YOU FOR VISITING HEALTHCARE -----------------");
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //choice();
    }

    public void payOnline(int fee, String doctor_name) {
        String pay = "";
        System.out.print("do you want to pay(yes/no):");
        pay = S.next();
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            if (pay.equals("yes")) {
                String query = "UPDATE attend set fees_paid='" + pay + "' WHERE doctor_name='" + doctor_name
                        + "' AND patient_name='" + name + "'";
                int row = stmt.executeUpdate(query);
                if (row > 0) {
                    System.out.println("thank you!");
                } else {
                    System.out.println("not updated");
                }
            } else {
                System.out.println("you didn't pay the fees!\n please pay to proceed");
                payOnline(fee, doctor_name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // choice();
    }

    public void feedback(String name, String doctor_name) {
        String feed = "";
        System.out.println("1 '*' : hate it");
        System.out.println("2 '**': not good");
        System.out.println("3 '***' : good");
        System.out.println("4 '****' : like it");
        System.out.println("5 '*****': love it");
        int i = 0;
        while (i == 0) {
            System.out.println("give feedback!");
            feed = S.next();
            if (feed.equals("*") || feed.equals("**") || feed.equals("***") || feed.equals("****")
                    || feed.equals("*****")) {
                i++;
            } else {
                System.out.println("feedback is incorrect!");
            }
        }
        try (Connection conn = DriverManager.getConnection(url, user_name, pswrd);
                Statement stmt = conn.createStatement()) {
            // update attend set feedback='*' where doctor_name='anirudh tiwari'; +"' AND
            // patient_name= "+ name
            String query = "UPDATE attend set feedback='" + feed + "' WHERE doctor_name='" + doctor_name + "'";
            int row = stmt.executeUpdate(query);
            if (row > 0) {
                System.out.println("thank you for your valuable time!");
            } else {
                System.out.println("feedback is not updated");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // choice();
    }

}
