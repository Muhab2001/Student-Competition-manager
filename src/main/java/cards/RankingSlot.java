package cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Student;
import models.Team;

import java.io.IOException;

public class RankingSlot {

    public Team cardTeam;

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;


    public void fillContent(Team team) throws IOException {
        // TODO: fill the students for that team
        cardTeam = team;
        for (Student student : cardTeam.students) { // Add each student card to the student container VBox
            if(student.name.length() != 0) {
                System.out.println(student.toString() + "in ranking slot");
                FXMLLoader studentNode = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                studentContainer.getChildren().add(studentNode.load()); // Add the student card to the student container
                StudentCard controller = studentNode.getController();
                controller.fillContent(student);
            }
        }
    }

    public String retreiveRank(){
        return rankingInput.getText();
    }

}
