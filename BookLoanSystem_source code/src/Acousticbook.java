import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Acousticbook extends Book{

    public String id;
    public String author;
    public String language;
    public int freetraiperioddays;
    public int subscription;
    Acousticbook(){

    }
    public Acousticbook(String id, String author, String language, int freetraiperioddays, int subscription) {
        this.id = id;
        this.author = author;
        this.language = language;
        this.freetraiperioddays = freetraiperioddays;
        this.subscription = subscription;
    }

    Scanner sc2 = new Scanner(System.in);
    ArrayList<String> allacousticbooks = new ArrayList<>();
    ArrayList<String> allacousticbooksloaned = new ArrayList<>();



    public void loanbook(String username){
        System.out.println("Loan an acoustic book. Below is the list of books that we have: ");
        this.showBook();
        System.out.println("Enter the number of the book you want to loan: ");
        int bookthattheuserwants = sc2.nextInt();
        if(bookthattheuserwants>allacousticbooks.size()||bookthattheuserwants<0){
            System.out.println("Enter a valid number please. ");
            this.loanbook(username);
        }
        Boolean alreadyloaned = this.alreadyloaned(username,allacousticbooks.get(bookthattheuserwants-1));
        if(alreadyloaned==true){
            System.out.println("You cannot loan this book again because you alredy have this book.");
            Main.activities(username);
        }
        else{

            System.out.println("Would you like to:");
            System.out.println("1. Try a free trail.");
            System.out.println("2. Buy a subscription.");
            int choice = sc2.nextInt();
            if(choice == 1){
                 Boolean freetrialexsists = this.hasfreetrial(allacousticbooks.get(bookthattheuserwants-1));
                 if(freetrialexsists==true){
                     System.out.println("You have got this book on free trial.");
                 }
                 else {
                     System.out.println("There exists no free trail for this book.");
                     Main.activities(username);
                 }
            }
            else if(choice==2){
                this.getsubscription(allacousticbooks.get(bookthattheuserwants-1));
                System.out.println("Pay the subscription at the maincounter and get the receipt.");
            }
            else {
                System.out.println("Choice should be between 1 and 2. Please provide the right input.");
                Acousticbook acousticbook = new Acousticbook("","","",0,0);
                this.loanbook(username);
            }
            Random random = new Random();
            int x = random.nextInt(10000);
            LoanRecord loanRecord = new LoanRecord("-",allacousticbooks.get(bookthattheuserwants-1),x,"Acoustic",username);
            loanRecord.insertloanrecord(loanRecord);
            Main.activities(username);
        }
    }

    public void showBook(){
        try (ResultSet resultSet = databaseHandler.dbstatement.executeQuery("SELECT * FROM bookdb.acoustic;")) {
            int i = 1;
            while (resultSet.next()) {
                allacousticbooks.add(resultSet.getString("id"));
                System.out.println(i+". "+resultSet.getString("id"));
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
                loaned.add(resultSet.getString("bookidifacoustic"));
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

    public Boolean hasfreetrial(String id){
        Boolean freetrailexsists = true;
        int days = 0 ;
        try{
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.acoustic where id = '"+id+"'");
            while(resultSet.next()){
                System.out.println("This book has free trail of "+resultSet.getString("freetraiperioddays")+" day/days.");
                days = Integer.valueOf(resultSet.getString("freetraiperioddays"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(days<=0){
            freetrailexsists = false;
        }
        return freetrailexsists;
    }

    public void getsubscription(String id){
        try{
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.acoustic where id = '"+id+"'");
            while(resultSet.next()){
                System.out.println("This book subscription of "+resultSet.getString("subscription")+" dollar/dollars.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
static int loadid = 0;

    public void returnbook(String username){

        System.out.println("Return an acoustic book. Below is the list of acoustic books you have loan: ");
        this.getallloanedbooks(username);
        System.out.println("Enter the number of the book you want to end subscription for: ");
        int bookthattheuserwantstoreturn = sc2.nextInt();
        if(bookthattheuserwantstoreturn>allacousticbooksloaned.size()||bookthattheuserwantstoreturn<0){
            System.out.println("Enter a valid number please. ");
            this.returnbook(username);
        }

        System.out.println(allacousticbooksloaned.get(bookthattheuserwantstoreturn-1)+" has its subscription ended. What would you like to do next:");
       // System.out.println(loadid);
        //LoanRecord loanRecord = new LoanRecord(allacousticbooksloaned.get(bookthattheuserwantstoreturn-1),"-",loadid,"Acoustic",username);
        LoanRecord.deleterecord(loadid);
        Main.activities(username);
    }

    public void getallloanedbooks(String username){
        try{
            int i = 1;
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.loanrecord where user = '"+username+"' AND booktype= 'Acoustic'");
            while(resultSet.next()){
                allacousticbooksloaned.add(resultSet.getString("bookidifacoustic"));
                loadid = Integer.valueOf(resultSet.getString("loanid"));
                System.out.println(i+". "+resultSet.getString("bookidifacoustic"));
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
