package com.example.myapplication;

public class AccountUser {
    public String Name;
    public String Password;
    public String Cauhoibaomat1;
    public String Cauhoibaomat2;
    public String Cauhoibaomat3;


    public AccountUser() {
    }

    public AccountUser(String name, String password) {
        Name = name;
        Password = password;
    }

    public AccountUser(String name, String password, String cauhoibaomat1, String cauhoibaomat2, String cauhoibaomat3) {
        Name = name;
        Password = password;
        Cauhoibaomat1 = cauhoibaomat1;
        Cauhoibaomat2 = cauhoibaomat2;
        Cauhoibaomat3 = cauhoibaomat3;
    }
}
