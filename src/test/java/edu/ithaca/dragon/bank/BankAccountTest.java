package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());

        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());

        bankAccount = new BankAccount("a@b.com", 300);
        bankAccount.withdraw(300);
        assertEquals(0, bankAccount.getBalance());

        assertThrows(InsufficientFundsException.class, ()-> new BankAccount("a@b.com", 100).withdraw(101));

        bankAccount = new BankAccount("a@b.com", 300);
        bankAccount.withdraw(-1);
        assertEquals(300, bankAccount.getBalance());

        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(-300);
        assertEquals(200, bankAccount.getBalance());

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("jo#sh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("josh @gmail.com"));

        assertTrue(BankAccount.isEmailValid("josh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("joshgmail.com"));
        assertFalse(BankAccount.isEmailValid("josh@gm@il.com"));

        assertTrue(BankAccount.isEmailValid("name@place.com"));
        assertFalse(BankAccount.isEmailValid("name@placecom"));
        assertFalse(BankAccount.isEmailValid("name@plac.e.com"));

        assertTrue(BankAccount.isEmailValid("name@place.co"));
        assertFalse(BankAccount.isEmailValid("name@place.c"));

        assertTrue(BankAccount.isEmailValid("j.o.s.h@gmail.com"));
        assertFalse(BankAccount.isEmailValid("j.o..s.h@gmail.com"));
        assertFalse(BankAccount.isEmailValid(".jo.sh@gmail.com"));
        assertFalse(BankAccount.isEmailValid("jo.sh.@gmail.com"));

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

}