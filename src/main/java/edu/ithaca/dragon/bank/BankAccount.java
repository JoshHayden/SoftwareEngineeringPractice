package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)) {
            this.email = email;
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * if amount is larger than the balance throw InsufficientFundsException
     * if amount negative make no changes to balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0) return;
        else if (amount <= balance) balance -= amount;
        else{
            throw new InsufficientFundsException("Amount must be less than or equal to account balance");
        }

    }


    public static boolean isEmailValid(String email) {
        int at = email.indexOf('@');
        if (at == -1) return false;

        //check prefix
        if (email.charAt(0) == '-' || email.charAt(at - 1) == '-') return false;

        if (email.charAt(0) == '.' || email.charAt(at - 1) == '.') return false;

        //check suffix
        String suffix = email.substring(at + 1);
        int period = suffix.indexOf('.');

        if (period == -1) return false;

        int pdCount = 0;
        for (int i = 0; i < suffix.length(); i++){
            if (suffix.charAt(i) == '@') return false;
            else if (suffix.charAt(i) == '.') pdCount++;
        }

        if (pdCount != 1) return false;

        if (suffix.substring(period).length() < 3) return false;

        //check all
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '#') return false;
            else if (email.charAt(i) == ' ') return false;
                //two periods in a row
            else if (email.charAt(i) == '.') {
                if (email.charAt(i + 1) == '.') return false;
            }
            else if (email.charAt(i) == '-') {
                if (email.charAt(i + 1) == '-') return false;
            }
        }

        //if it passes all cases
        return true;
    }

    /**
     * Returns false if amount is negative or has more than two decimal points, returns true otherwise
     */
    public static  boolean isAmountValid(double amount){
        String amountAsString = Double.toString(amount);
        System.out.println(amountAsString);
        int postDecimal = 0;
        if (amountAsString.contains(".")){
            postDecimal = amountAsString.length() - amountAsString.indexOf(".") - 1;

        }

        if (amount < 0){
            return false;
        }
        else if (postDecimal > 2){ //div[1] is the string after decimal
            return false;
        }

        else {
            return true;
        }

    }
}
