package utils;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.util.Duration;
import utils.transitions.ShadowTransition;

/**
 * class to add hover effect on cards
 */
public class Hover {

    /**
     * triggering the raise effect on selected node
     * @param node target node
     */
    public static void raising(Node node){
        TranslateTransition trans = new TranslateTransition(Duration.millis(200), node);

        DropShadow drop = new DropShadow();
        drop.setRadius(30);

        ShadowTransition shadow = new ShadowTransition(Duration.millis(200), node, drop);
        shadow.setRaised(true);

        node.setOnMouseEntered(e -> {
            trans.setByY(-3);
            trans.playFromStart();

            shadow.setIsIn(true);
            shadow.playFromStart();
        });
        node.setOnMouseExited(e -> {
            trans.setByY(3);
            trans.playFromStart();

            shadow.setIsIn(false);
            shadow.playFromStart();
        });
    }
}
