
// E-HealthCare-Management-System is a console based application which is built using java.
// This application helps in management of Patients, doctors, admin in a easy and comfortable way.using 
// this Application patients can quickly Sign up, Login, view his/her profile, view doctors, book Appointment,
//  view Report, choose doctor, view Appointments, give feedback, pay online and logout. Admin can add Doctors,
//  view patients list, view Doctors list,remove doctors, see feedback given by patients,view reports,logout.
//  Doctor can login, view profile, viewAppointments, Attend Patients and logout.
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        char ch = 'y';
        while (ch == 'y' || ch == 'Y') {
            System.out.println("enter choice: ");
            System.out.println("1 PATIENT ");
            System.out.println("2 DOCTOR ");
            System.out.println("3 ADMIN ");
            Scanner S = new Scanner(System.in);
            int choice = S.nextInt();
            switch (choice) {
                case 1:
                    PATIENT p = new PATIENT();
                    System.out.println("enter 1 for signup ");
                    System.out.println("enter 2 for login");
                    int n = S.nextInt();
                    switch (n) {
                        case 1:
                            p.signup();
                            break;
                        case 2:
                            p.login();
                            break;
                        default:
                            System.out.println("-------------------------INVALID CHOICE-----------------------");
                            break;
                    }
                    break;
                case 2:
                    DOCTOR d = new DOCTOR();
                    System.out.println("enter 1 for signup ");
                    System.out.println("enter 2 for login");
                    n = S.nextInt();
                    switch (n) {
                        case 1:
                            d.signup();
                            break;
                        case 2:
                            d.login();
                            break;
                        default:
                            System.out.println("-------------------------INVALID CHOICE-----------------------");
                            break;
                    }
                    break;
                case 3:
                    ADMIN a = new ADMIN();
                    a.login();
                    break;

                default:
                    System.out.println("-------------------------INVALID CHOICE-----------------------");
                    break;
            }
            System.out.println("do you want to continue the app(y/n)?");
            ch = S.next().charAt(0);
            if (ch == 'y' || ch == 'Y') {
                continue;
            } else {
                System.out.println("-------------------------------------THANK YOU FOR VISITING HEALTHCARE -------------------------------------");
                break;
            }
        }
    }
}

