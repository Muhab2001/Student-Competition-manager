package models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class User {

    public String password;
    public String username;
    public String email;

    public User(){
        password = "";
        username = "";
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // tester for password encryption
    public static void main(String[] args) throws IOException {
        System.out.println((int) 'A');
        Gson gson = new Gson();
        // writing to json
        JsonWriter writer = new JsonWriter(new PrintWriter("PasswordContainer.json"));
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("KFUPM Press1", Crypt.crypt("Pass12#34?", "$6$" + new Random("Pass12#34?".length()).nextInt(100000) + (int) "Pass12#34?".charAt(3)), "pressemail1@kfupm.edu.sa"));
        users.add(new User("KFUPM Press2", Crypt.crypt("Pass124567?", "$6$" + new Random("Pass124567?".length()).nextInt(100000) + (int) "Pass124567?".charAt(3)), "pressemail1@kfupm.edu.sa"));
        writer.setIndent("  ");
        writer.beginArray();
        for (User user : users) {
            gson.toJson(user, User.class, writer);
        }
        writer.endArray();
        writer.close();
        // reading from json
        JsonReader reader = new JsonReader(new FileReader("PasswordContainer.json"));
        users = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            User user = gson.fromJson(reader, User.class);
            users.add(user);
        }

        reader.endArray();
        reader.close();
        users.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
