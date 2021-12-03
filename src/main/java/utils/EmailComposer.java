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
        String[] names = {"Muhab", "Ahmed", "Basel", "Mubarak", "Abdulaziz", ""};
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        Team team1 = new Team(0, 6);
        team1.rank = 1;
        for(int i = 0; i < team1.students.size(); i++){
            team1.students.add(new Student( i, "20194657" + i, names[i], "SWE/CS"));
        }
        sendMail(team1, "test1");
    }

    public static void sendMail(Team team, String CompetitionName) throws Exception {
        for(int i = 0; i < team.students.size(); i++){
            sendMail(prepareMessage(team.students.get(i).id, CompetitionName, team.rank));
        }
    }

    private static void sendMail(String message) throws Exception {
        Desktop desktop;
        if (Desktop.isDesktopSupported()
                && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            URI mailto = new URI(message);
            desktop.mail(mailto);
        } else {
            throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }
    // TODO: read the format from the Email body Template.txt, and send to all team members
    private static String prepareMessage(String recipient, String CompetitionName, int rank) throws FileNotFoundException {
        String email = "s" + recipient + "@kfupm.edu.sa";
        String subject = "Congratulation on achieving " + rank + "place in " + CompetitionName;
        String body = "";

            Scanner scanner = new Scanner(new File("EmailBodyTemplate.txt"));
            while(scanner.hasNext()){
                body += scanner.nextLine();
            }
           body = body.replace("[Student name/ Team name]", recipient);
           body = body.replace("[Competition name]", CompetitionName);
           body = body.replace(" ", "%20");
           subject = subject.replace(" ", "%20");
       return ("mailto:" + email + "?subject=" + subject + "&body=" + body.strip()).strip();
    }
}
