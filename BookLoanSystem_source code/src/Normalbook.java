import javax.swing.plaf.synth.SynthLookAndFeel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Normalbook extends Book{

    public String id;
    public String author;
    public int year ;
    public String language;
    public int noofhardcopies;
    public int loanperioddays;
    public Normalbook(String id, String author, int year, String language, int noofhardcopies, int loanperioddays) {
        this.id = id;
        this.author = author;
        this.year = year;
        this.language = language;
        this.noofhardcopies = noofhardcopies;
        this.loanperioddays = loanperioddays;
    }
    Normalbook(){

    }

    Scanner sc = new Scanner(System.in);
    ArrayList<String> allnormalbooks = new ArrayList<>();
    ArrayList<String> allnormalbooksloaned = new ArrayList<>();

    public void loanbook(String username){
            System.out.println("Loan a normal book. Below is the list of books that we have: ");
            this.showBook();
                System.out.println("Enter the number of the book you want to loan: ");
                int bookthattheuserwants = sc.nextInt();
        if(bookthattheuserwants>allnormalbooks.size()||bookthattheuserwants<0){
            System.out.println("Enter a valid number please. ");
            this.loanbook(username);
        }
                Boolean alreadyloaned = this.alreadyloaned(username,allnormalbooks.get(bookthattheuserwants-1));
                        if(alreadyloaned==true){
                            System.out.println("You cannot loan this book again because you alredy have this book.");
                            Main.activities(username);
                        }
                        else{


                                Boolean isavailable = this.ishardcopyavaiable(allnormalbooks.get(bookthattheuserwants-1));
                                    if(isavailable==true){
                                        System.out.println(allnormalbooks.get(bookthattheuserwants-1)+" is loaned for 30 days. What would you like to do next:");
                                        this.decrementthenoofhardcopies(allnormalbooks.get(bookthattheuserwants-1));
                                        //number generation to make a unique id for the load record
                                        Random random = new Random();
                                        int x = random.nextInt(10000);
                                        LoanRecord loanRecord = new LoanRecord(allnormalbooks.get(bookthattheuserwants-1),"-",x,"Normal",username);
                                        loanRecord.insertloanrecord(loanRecord);
                                        Main.activities(username);
                                    }
                                    else if(isavailable==false){
                                        System.out.println("Your book is not available.");
                                        Main.activities(username);
                                    }
                        }
    }



    public void showBook(){
                        try (ResultSet resultSet = databaseHandler.dbstatement.executeQuery("SELECT * FROM bookdb.normal;")) {
                            int i = 1;
                            while (resultSet.next()) {
                                allnormalbooks.add(resultSet.getString("id"));
                                System.out.println(i+". "+resultSet.getString("id"));
                                Normalbook normal = new Normalbook("","",0,"",0,0);
                                i++;
                            }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
    }

    public Boolean alreadyloaned(String username, String bookname){
        ArrayList<String> loaned = new ArrayList<>();
        try{
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.loanrecord where user = '"+username+"'");
            while(resultSet.next()){
                loaned.add(resultSet.getString("bookidifnormal"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(loaned.contains(bookname)){
            return true;
        }
        return false;
    }

    public boolean ishardcopyavaiable(String booktheuserwants){
        boolean isavailable = true;
        int noofcopies = 0;
        try{
        ResultSet resultSet = databaseHandler.dbstatement.executeQuery("SELECT * FROM bookdb.normal where id='"+booktheuserwants+"';");
            while (resultSet.next()) {
                noofcopies  = Integer.valueOf(resultSet.getString("noofhardcopies"));
                isavailable = true;
            }
            if(noofcopies<=0){
                isavailable = false;
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return isavailable;
    }

    public void decrementthenoofhardcopies(String bookname){
        //first we get the number of copies then update it by decrementing one from the  no of copies
        int noofcopies = 0;
        try{
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("SELECT * FROM bookdb.normal where id='"+bookname+"';");
            while (resultSet.next()) {
                noofcopies  = Integer.valueOf(resultSet.getString("noofhardcopies"));
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        int newnoofcopies = noofcopies - 1;
        try{
            int updated = databaseHandler.dbstatement.executeUpdate("UPDATE bookdb.normal set noofhardcopies ='"+newnoofcopies+"' where id='"+bookname+"';");
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }


    }
    static int loadid = 0;


    public void returnbook(String username){
        System.out.println("Return a normal book. Below is the list of books you have loan: ");
        this.getallloanedbooks(username);
        System.out.println("Enter the number of the book you want to return: ");
        int bookthattheuserwantstoreturn = sc.nextInt();
        if(bookthattheuserwantstoreturn>allnormalbooksloaned.size()||bookthattheuserwantstoreturn<0){
            System.out.println("Enter a valid number please. ");
            this.returnbook(username);
        }

            System.out.println(allnormalbooksloaned.get(bookthattheuserwantstoreturn-1)+" is returned. What would you like to do next:");
            this.incrementthenoofhardcopies(allnormalbooksloaned.get(bookthattheuserwantstoreturn-1));
            LoanRecord.deleterecord(loadid);
            Main.activities(username);
    }

    public void getallloanedbooks(String username){
        try{
            int i = 1;
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.loanrecord where user = '"+username+"' AND booktype= 'Normal'");
            while(resultSet.next()){
                allnormalbooksloaned.add(resultSet.getString("bookidifnormal"));
                loadid = Integer.valueOf(resultSet.getString("loanid"));
                System.out.println(i+". "+resultSet.getString("bookidifnormal"));
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void incrementthenoofhardcopies(String bookname) {
        //first we get the number of copies then update it by incrementing one from the  no of copies
        int noofcopies = 0;
        try {
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("SELECT * FROM bookdb.normal where id='" + bookname + "';");
            while (resultSet.next()) {
                noofcopies = Integer.valueOf(resultSet.getString("noofhardcopies"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int newnoofcopies = noofcopies + 1;
        try {
            int updated = databaseHandler.dbstatement.executeUpdate("UPDATE bookdb.normal set noofhardcopies ='" + newnoofcopies + "' where id='" + bookname + "';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
