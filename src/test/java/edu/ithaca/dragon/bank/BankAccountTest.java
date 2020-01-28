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
        bankAccount = new BankAccount("a@b.com", 300);
        bankAccount.withdraw(-1);
        assertEquals(300, bankAccount.getBalance());
        //Amount is negative
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(-300);
        assertEquals(200, bankAccount.getBalance());

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
        assertFalse(BankAccount.isAmountValid(120.040)); //Decided that even if the points over two decimal points are 0 to return false
    }

}