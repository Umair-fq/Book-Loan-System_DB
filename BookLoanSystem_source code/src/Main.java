import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void activities(String username){
        Scanner sc = new Scanner(System.in);
        System.out.println(" Choose your activity:\n" +
                "A. Loan a normal book\n" +
                "B. Loan an acoustic book\n" +
                "C. Return a normal book\n" +
                "D. Return an acoustic book\n" +
                "E. Check loan records\n" +
                "F. Donate a book\n" +
                "G. Exit");
        Character choice = sc.next().charAt(0);

        if(choice=='A'){
            UserLoan normalbookloan = new UserLoan();
            normalbookloan.loanactivities('A',username);
        }
        else if(choice=='B'){
            UserLoan acousticbookloan = new UserLoan();
            acousticbookloan.loanactivities('B',username);
        }
        else if(choice=='C'){
            Book book = new Normalbook();
            book.returnbook(username);
        }
        else if(choice=='D'){
            Book book = new Acousticbook();
            book.returnbook(username);
        }
        else if(choice=='E'){
            LoanRecord.checkrecords(username);
        }
        else if(choice=='F'){
            databaseHandler.donatebook(username);
        }
        else if(choice=='G'){
            System.exit(0);
        }
        else {
            System.out.println("Choose the right option please.");
        }
    }
    public static void main(String[] args) {

       databaseHandler dbhandler = new databaseHandler();
       if(databaseHandler.booksinserted==false) {
           dbhandler.insertbookintodb();
       }
        Scanner sc = new Scanner(System.in);
       System.out.println(" Sign up a new user: \n");
       String username = sc.next();
       while(true){
       activities(username);
       }

    }
}
