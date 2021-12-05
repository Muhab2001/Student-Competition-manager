package utils;

import models.Student;
import models.Team;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailComposer {

    public static void main(String[] args) throws Exception {
        String[] names = {"Muhab", "Ahmed", "Basel", "Mubarak", "Abdulaziz", "yo"};
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        Team team1 = new Team(0, 6);
        team1.rank = 23;
        for(int i = 0; i < team1.teamSize; i++){
            team1.students.add(new Student( i, "20194557" + i, names[i], "SWE/CS"));
        }
        sendMail(team1, "test1");
    }

    public static void sendMail(Team team, String CompetitionName) throws Exception {
        String[] emails = new String[team.students.size()];
        String[] names = new String[team.students.size()];
        for(int i = 0; i < team.students.size(); i++){
            emails[i] = team.students.get(i).id;
            names[i] = team.students.get(i).name;
        }

        String URIstring = prepareMessage(emails,names,  CompetitionName, team.rank);
        sendMail(URIstring);
    }

    private static void sendMail(String URIstring) throws Exception {
        Desktop desktop;
        if (Desktop.isDesktopSupported()
                && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            URI mailto = new URI(URIstring);
            desktop.mail(mailto);
        } else {
            throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }

    private static String prepareMessage(String[] emails, String[] names, String CompetitionName, int rank) throws FileNotFoundException {

        String email = "";
        String namesStr = "";
        for(int i = 0; i < emails.length - 1; i++){
            email += "s" + emails[i] + "@kfupm.edu.sa;";
            namesStr += ", " + names[i] ;
        }
        namesStr += ", " + names[names.length - 1];
        email += "s" +  emails[emails.length - 1] + "@kfupm.edu.sa";
        String suffix = "";
        String rankStr = Integer.toString(rank);
        if(rankStr.length() > 1){
            if(rankStr.charAt(rankStr.length() -2) == '1'){
                suffix = "th"; // [11-19]
            }else{
                switch (rankStr.charAt(rankStr.length() - 1)){
                    case '1' -> suffix = "st";
                    case '2' -> suffix = "nd";
                    case '3' -> suffix = "rd";

                }
            }
        } else { //[1 - 9]
            switch (rankStr.charAt(rankStr.length() - 1)){
                case '1' -> suffix = "st";
                case '2' -> suffix = "nd";
                case '3' -> suffix = "rd";

            }
        }
        String subject = "Congratulation on achieving " + rank + suffix + " place in " + CompetitionName;
        String body = "";

            Scanner scanner = new Scanner(new File("EmailBodyTemplate.txt"));
            while(scanner.hasNext()){
                body += scanner.nextLine() + "%0D%0A"; // mailto URI schema newline character
            }
           body = body.replace("[Student name/Team name]", namesStr);
           body = body.replace("[Competition name]", CompetitionName);
           body = body.replace(" ", "%20"); // mailto URI schema whitespace character
           subject = subject.replace(" ", "%20");
       return ("mailto:" + email + "?subject=" + subject + "&body=" + body.strip()).strip();
    }
}
