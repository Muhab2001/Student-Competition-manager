package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Student;
import models.Team;
import utils.EmailComposer;

import java.io.IOException;

public class EmailSlot {

    private Team currentTeam;
    private String compName;
    private EmailDialog currentController;

    @FXML
    private Button emailBtn;

    @FXML
    private Label rankValue;

    @FXML
    private VBox studentContainer;

    @FXML
    void sendEmail(ActionEvent event) throws Exception {

        System.out.println("Firing emails!");
        EmailComposer.sendMail(currentTeam, compName);
        emailBtn.setDisable(true);
        currentController.incrementCounter();
    }



    public void fillContent(Team team, String competitionName, EmailDialog controller) throws IOException {
        compName = competitionName;
        currentTeam = team;
        currentController = controller;
        rankValue.setText(String.valueOf(team.rank));
        for (Student student : team.students) { // Add each student card to the student container VBox
            if(student.name.length() != 0) {
                System.out.println(student.toString() + "in email slot");
                FXMLLoader studentNode = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                studentContainer.getChildren().add(studentNode.load()); // Add the student card to the student container
                StudentCard stcontroller = studentNode.getController();
                stcontroller.fillContent(student);
            };

        }
    }

}
