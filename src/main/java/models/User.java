package models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * a class to identify user credentials throughout the app lifetime
 */
public class User {

    public String password;
    public String username;
    public String email;

    public User(){
        password = "";
        username = "";
        email = "";
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }




    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
