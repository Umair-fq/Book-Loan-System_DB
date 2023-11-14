import java.net.PortUnreachableException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanRecord {
    public String bookidifnormal;
    public String bookidifacoustic;
    public int loadid;
    public String booktype;
    public String user;

    public LoanRecord(String bookidifnormal, String bookidifacoustic, int loadid, String booktype,String user) {
        this.bookidifnormal = bookidifnormal;
        this.bookidifacoustic = bookidifacoustic;
        this.loadid = loadid;
        this.booktype = booktype;
        this.user = user;
    }

    public void insertloanrecord(LoanRecord loanRecord){
        try {
            int resultSet = databaseHandler.dbstatement.executeUpdate("INSERT INTO `bookdb`.`loanrecord`\n" +
                    "(`bookidifnormal`,\n" +
                    "`bookidifacoustic`,\n" +
                    "`loanid`,\n" +
                    "`booktype`,\n" +
                    "`user`)\n" +
                    "VALUES\n" +
                    "('"+loanRecord.bookidifnormal+"','" +loanRecord.bookidifacoustic+"'," +loanRecord.loadid+",'" + loanRecord.booktype+"','" +loanRecord.user+"');");
        System.out.println("Your information has been added to the loan record.");
        }
        catch (SQLException throwables){
              throwables.printStackTrace();
        }
    }

    public static void checkrecords(String username){
        try{
            ResultSet resultSet = databaseHandler.dbstatement.executeQuery("select * from bookdb.loanrecord where user = '"+username+"'");
            while(resultSet.next()){
                System.out.println(resultSet.getString("loanid")+". "+"   "+resultSet.getString("bookidifnormal")+"   "+resultSet.getString("bookidifacoustic")+"   "+resultSet.getString("booktype"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleterecord(int loanid){
        //DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
        try {
            int resultSet = databaseHandler.dbstatement.executeUpdate("DELETE FROM `bookdb`.`loanrecord`\n" +
                    "WHERE loanid ='"+loanid+"';\n");
            System.out.println("Your information has been added to the loan record.");
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

}
