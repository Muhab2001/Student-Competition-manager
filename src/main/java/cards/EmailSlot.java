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

/**
 * controller for email card slot
 */
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

    /**
     * event listener to send mails for all target team members
     * @param event
     * @throws Exception `EmailBodyTemplate.txt` file corruption or URI errors
     */
    @FXML
    void sendEmail(ActionEvent event) throws Exception {
        EmailComposer.sendMail(currentTeam, compName);
        emailBtn.setDisable(true);
        currentController.incrementCounter();
    }


    /**
     * populating with team infom passing required controllers
     * @param team target team
     * @param competitionName competition name
     * @param controller running email dialog controller
     * @throws IOException fxml file corruption
     */
    public void fillContent(Team team, String competitionName, EmailDialog controller) throws IOException {
        emailBtn.setId("teammail-" + team.index);
        compName = competitionName;
        currentTeam = team;
        currentController = controller;
        rankValue.setText(String.valueOf(team.rank));
        for (Student student : team.students) { // Add each student card to the student container VBox
            if(student.name.length() != 0) {
                FXMLLoader studentNode = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                studentContainer.getChildren().add(studentNode.load()); // Add the student card to the student container
                StudentCard stcontroller = studentNode.getController();
                stcontroller.fillContent(student);
            };

        }
    }

}
