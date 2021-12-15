package cards;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Student;
import models.Team;

import java.io.IOException;

public class RankingSlot {

    public Team cardTeam;
    private RankingDialog controller;

    @FXML
    private HBox container;

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

    @FXML
    void enterSubmit(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            controller.ranker(event);
    }

    public void fillContent(Team team, RankingDialog dialogcontroller) throws IOException {
        this.controller = dialogcontroller;
        container.setId("rankslot-" + team.index);
        rankingInput.setId("teamslot-" + team.index);
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

    public String retrieveRank(){
        return rankingInput.getText();
    }

    public void flagError(){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        rankingInput.pseudoClassStateChanged(errorClass, true);

    }

    public void clearError(){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        rankingInput.pseudoClassStateChanged(errorClass, false);

    }


}
