package utils.skins;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.transitions.ShadowTransition;

public class PrimaryButtonSkin extends ButtonSkin {

    public PrimaryButtonSkin(Button control) {
        super(control);

        DropShadow dropIn = new DropShadow();
        dropIn.setRadius(30);

        final ShadowTransition shadowIn = new ShadowTransition(Duration.millis(300),control,dropIn);
        shadowIn.setIsIn(true);
        Transition in = animateBackgroundColor(control, Color.valueOf("#3DB2FF"),Color.valueOf("#369cdc"),500);


        control.setOnMouseEntered(e -> {
            shadowIn.playFromStart();
            in.playFromStart();
        });

        DropShadow dropOut = new DropShadow();
        dropOut.setRadius(30);
        final ShadowTransition shadowOut = new ShadowTransition(Duration.millis(300),control,dropOut);
        shadowOut.setIsIn(false);

        Transition out = animateBackgroundColor(control, Color.valueOf("#369cdc"),Color.valueOf("#3DB2FF"),500);


        control.setOnMouseExited(e -> {
            shadowOut.playFromStart();
            out.playFromStart();
        });



    }

    private Transition animateBackgroundColor(Button control, Color fromColor, Color toColor, double duration){
        Rectangle rect = new Rectangle();
        rect.setFill(fromColor);

        FillTransition tr = new FillTransition();
        tr.setShape(rect);
        tr.setDuration(Duration.millis(duration));
        tr.setFromValue(fromColor);
        tr.setToValue(toColor);

        tr.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                control.setBackground(new Background(new BackgroundFill(rect.getFill(), new CornerRadii(4), Insets.EMPTY)));
                return t;
            }
        });

        return tr;
    }

}
