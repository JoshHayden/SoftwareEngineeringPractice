package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //Positive balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());
        //Balance is 0
        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        //Amount is less than balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        //Amount is equal to balance
        bankAccount = new BankAccount("a@b.com", 300);
        bankAccount.withdraw(300);
        assertEquals(0, bankAccount.getBalance());
        //Amount is greater than balance
        assertThrows(InsufficientFundsException.class, ()-> new BankAccount("a@b.com", 100).withdraw(101));
        //Amount is negative
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).withdraw(-30));
        //Too many decimals
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 20).withdraw(10.345));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 20).withdraw(40.168)); //Want to throw illegal argument rather than insufficientfunds when both are true

    }

    @Test
    void isEmailValidTest(){
        //checks for a basic, valid email and for empty string
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));
        //this set checks for forbidden characters, the border case being only one of the forbidden characters is present
        assertFalse(BankAccount.isEmailValid("jo#sh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("josh @gmail.com"));
        //This set checks for the proper number of @'s, the border cases being 0, 1, and 2 @'s in the address
        assertTrue(BankAccount.isEmailValid("josh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("joshgmail.com"));
        assertFalse(BankAccount.isEmailValid("josh@gm@il.com"));
        //This set checks for only 1 period after the @, the border cases being 0, 1, and 2
        assertTrue(BankAccount.isEmailValid("name@place.com"));
        assertFalse(BankAccount.isEmailValid("name@placecom"));
        assertFalse(BankAccount.isEmailValid("name@plac.e.com"));
        //This set checks for the proper number of characters after the last period, the border cases being 1 and 2
        assertTrue(BankAccount.isEmailValid("name@place.co"));
        assertFalse(BankAccount.isEmailValid("name@place.c"));
        //This set checks that each period is surrounded by 2 proper characters
        //The border cases are having 1 number/letter on each side, and only having a number/letter on one side
        assertTrue(BankAccount.isEmailValid("j.o.s.h@gmail.com"));
        assertFalse(BankAccount.isEmailValid("j.o..s.h@gmail.com"));
        assertFalse(BankAccount.isEmailValid(".jo.sh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("jo.sh.@gmail.com"));
        //This set checks that each hyphen is surrounded by 2 proper characters
        //The border cases are having 1 number/letter on each side, and only having a number/letter on one side
        assertTrue(BankAccount.isEmailValid("j-o-s-h@gmail.com"));
        assertFalse(BankAccount.isEmailValid("j-o--s-h@gmail.com"));
        assertFalse(BankAccount.isEmailValid("-jo-sh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("jo-sh-@gmail.com"));



    }

    @Test
    void constructorTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -30));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50.123));
    }

    @Test
    void isAmountValidTest() throws IllegalArgumentException{
        //Checking for negative values
        assertTrue(BankAccount.isAmountValid(300));
        assertTrue(BankAccount.isAmountValid(0));
        assertFalse(BankAccount.isAmountValid(-0.01));
        assertFalse(BankAccount.isAmountValid(-50));

        //Checking for more than two decimal points
        assertTrue(BankAccount.isAmountValid(300.4));
        assertTrue(BankAccount.isAmountValid(150.03));
        assertFalse(BankAccount.isAmountValid(204.004));
        assertTrue(BankAccount.isAmountValid(120.040)); //Decided that if the points over two decimal points are 0 to return true, easier with the toString method
    }

    @Test
    void depositTest() throws IllegalArgumentException{
        //Checking for negative values
        BankAccount bankAccount = new BankAccount("a@b.com", 0);
        bankAccount.deposit(50);
        assertEquals(50, bankAccount.getBalance());
        bankAccount.deposit(0);
        assertEquals(50, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).deposit(-0.01));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).deposit(-50));

        //Checking for decimals
        bankAccount = new BankAccount("a@b.com", 50);
        bankAccount.deposit(0.01);
        assertEquals(50.01, bankAccount.getBalance());
        bankAccount.deposit(53.46);
        assertEquals(103.47, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).deposit(53.014));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).deposit(0.001));


    }

    @Test
    void transferTest() throws IllegalArgumentException, InsufficientFundsException{
        BankAccount sender = new BankAccount("a@b.com", 1000);
        BankAccount reciever = new BankAccount("c@b.com", 0);
        //Check basic transfer
        sender.transfer(10, reciever);
        assertEquals(990, sender.getBalance());
        assertEquals(10, reciever.getBalance());
        //Check transfer of 0
        sender.transfer(0, reciever);
        assertEquals(990, sender.getBalance());
        assertEquals(10, reciever.getBalance());
        //Check transfer with decimals
        sender.transfer(50.45, reciever);
        assertEquals(939.55, sender.getBalance());
        assertEquals(60.45, reciever.getBalance());
        //Check transfer with too many decimals
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100).transfer(50.123, new BankAccount("c@d.com", 0)));
        //Check transfer with negative amount
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100).transfer(-30, new BankAccount("c@d.com", 0)));
        //Check transfer of too much money
        assertThrows(InsufficientFundsException.class, ()-> new BankAccount("a@b.com", 100).transfer(50.123, new BankAccount("c@d.com", 0)));
        //Check that having  both errors throws Illegal Argument rather than Insufficient Funds
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 50).transfer(75.123, new BankAccount("c@d.com", 0)));
    }

}