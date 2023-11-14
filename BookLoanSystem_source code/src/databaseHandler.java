import java.sql.*;
import java.util.Scanner;

public class databaseHandler {
    static Connection dbconnection;
    static Statement dbstatement;
    static Boolean booksinserted = false;
    public databaseHandler(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdb","root","root");
            dbconnection = connection;
            Statement statement = connection.createStatement();
            dbstatement = statement;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertbookintodb(){
        // we add 3 normal and three acoustic books as a sample
        //first we populate the normal books
        //we give some books zero copies to see if the error message occurs while loaning the book

        try {
            int resultSet = dbstatement.executeUpdate("INSERT INTO `bookdb`.`normal`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`year`,\n" +
                    "`language`,\n" +
                    "`noofhardcopies`,\n" +
                    "`loadperioddays`)\n" +
                    "VALUES\n" +
                    "('Harry potter',\n" +
                    "'J k Rowling',\n" +
                    "2000,\n" +
                    "'English',\n" +
                    "0,\n" +
                    "30);\n");
            int resultSet2 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`normal`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`year`,\n" +
                    "`language`,\n" +
                    "`noofhardcopies`,\n" +
                    "`loadperioddays`)\n" +
                    "VALUES\n" +
                    "('Death and its mysteriousness',\n" +
                    "'Kent Charles',\n" +
                    "2003,\n" +
                    "'English',\n" +
                    "110,\n" +
                    "30);\n");
            int result = dbstatement.executeUpdate("INSERT INTO `bookdb`.`normal`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`year`,\n" +
                    "`language`,\n" +
                    "`noofhardcopies`,\n" +
                    "`loadperioddays`)\n" +
                    "VALUES\n" +
                    "('-',\n" +
                    "'-',\n" +
                    "'0',\n" +
                    "'-',\n" +
                    "'0',\n" +
                    "'0');");
            int resultSet3 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`normal`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`year`,\n" +
                    "`language`,\n" +
                    "`noofhardcopies`,\n" +
                    "`loadperioddays`)\n" +
                    "VALUES\n" +
                    "('Dirty Politix',\n" +
                    "'James Mclaren',\n" +
                    "2005,\n" +
                    "'English',\n" +
                    "1,\n" +
                    "30);\n");


//here are the three acoustic books

            int resultSet4 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`acoustic`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`language`,\n" +
                    "`freetraiperioddays`,\n" +
                    "`subscription`)\n" +
                    "VALUES\n" +
                    "('Rich dad poor dad',\n" +
                    "'Robert Kayosaki',\n" +
                    "'English',\n" +
                    "7,\n" +
                    "5);\n");

            int resultSet5 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`acoustic`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`language`,\n" +
                    "`freetraiperioddays`,\n" +
                    "`subscription`)\n" +
                    "VALUES\n" +
                    "('Road to 100mil',\n" +
                    "'Dale shek',\n" +
                    "'English',\n" +
                    "0,\n" +
                    "5);\n");

            int resultset44 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`acoustic`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`language`,\n" +
                    "`freetraiperioddays`,\n" +
                    "`subscription`)\n" +
                    "VALUES\n" +
                    "('-',\n" +
                    "'-',\n" +
                    "'-',\n" +
                    "'0',\n" +
                    "'0');");

            int resultSet6 = dbstatement.executeUpdate("INSERT INTO `bookdb`.`acoustic`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`language`,\n" +
                    "`freetraiperioddays`,\n" +
                    "`subscription`)\n" +
                    "VALUES\n" +
                    "('How to survie an atomic bomb',\n" +
                    "'Dr. Dale Carnage',\n" +
                    "'English',\n" +
                    "0,\n" +
                    "5);\n");
            booksinserted = true;
        }
        catch (SQLException throwables){
          //  throwables.printStackTrace();
        }



    }

public static void donatebook(String username) {
    System.out.println("What type of book you want to donate: ");
    System.out.println("1.  Normal book.");
    System.out.println("2.  Acoustic book.");
    Scanner sc = new Scanner(System.in);
    int choice = sc.nextInt();
    if(choice == 1 ){
        System.out.println("Whats it's title");
        String id = sc.next();
        System.out.println("Whos it's authour");
        String author = sc.next();
        System.out.println("When was it written (Year)");
        String year = sc.next();
        System.out.println("Whats it's language");
        String language = sc.next();
        System.out.println("Whats it's number of copies");
        String noofcopies  = sc.next();
        try {
            int resultSet = dbstatement.executeUpdate("INSERT INTO `bookdb`.`normal`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`year`,\n" +
                    "`language`,\n" +
                    "`noofhardcopies`,\n" +
                    "`loadperioddays`)\n" +
                    "VALUES\n" +
                    "('"+id+"',\n" +
                    "'"+author+"',\n" +
                    " "+year+",\n" +
                    "'"+language+"',\n" +
                    noofcopies+",\n" +
                    "30);\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    else if(choice==2){
        System.out.println("Whats it's title");
        String id = sc.next();
        System.out.println("Whos it's authour");
        String author = sc.next();
        System.out.println("Whats it's language");
        String language = sc.next();
        try {
            int resultSet = dbstatement.executeUpdate("INSERT INTO `bookdb`.`acoustic`\n" +
                    "(`id`,\n" +
                    "`author`,\n" +
                    "`language`,\n" +
                    "`freetraiperioddays`,\n" +
                    "`subscription`)\n" +
                    "VALUES\n" +
                    "('"+id+"',\n" +
                    " '"+author+"',\n" +
                    "''"+language+"',\n" +
                    "7,\n" +
                    "5);\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    else{
        System.out.println("Please choose a right option.");
        Main.activities(username);
    }

}
}
