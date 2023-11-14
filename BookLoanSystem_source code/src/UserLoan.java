public class UserLoan {
    public void loanactivities(Character character,String username){
        if(character=='A'){
            Book book = new Normalbook();
            book.loanbook(username);
        }
        else if(character=='B'){
            Book book = new Acousticbook();
            book.loanbook(username);
        }

    }
}
