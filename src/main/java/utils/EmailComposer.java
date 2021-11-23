package utils;

import models.Student;
import java.awt.*;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailComposer {

    public static void main(String[] args) throws Exception {
        sendMail("mohababubakir2001@gmail.com");
    }

    public static void sendMail(Student[] students){
        // loop over the collection of students
    }

    private static void sendMail(String recepient) throws Exception {
        Desktop desktop;
        if (Desktop.isDesktopSupported()
                && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            URI mailto = new URI("mailto:john@example.com?subject=Hello%20World&body=congrats!");
            desktop.mail(mailto);
        } else {
            // TODO fallback to some Runtime.exec(..) voodoo?
            throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }

    private static String prepareMessage(String recepient) {
       return "";
    }
}
