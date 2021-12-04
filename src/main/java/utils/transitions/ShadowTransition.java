package utils.transitions;

import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class ShadowTransition extends Transition {

    private Button btn;
    private boolean isIn;
    private DropShadow shadow;

    public ShadowTransition(Duration duration,Button btn, DropShadow shadow){
        setCycleDuration(duration);
        this.btn = btn;
        this.shadow = shadow;
    }
    @Override
    protected void interpolate(double v) {
        if (isIn) {
            shadow.setOffsetY((10 - (v*10)) + 3);
            shadow.setHeight((30 - (v*30)) + 8);
            shadow.setWidth((30 - (v*30)) + 8);
            shadow.setColor(Color.rgb(62,76,92,0.1 + (0.2*v)));
        }else{
            shadow.setOffsetY(7*v + 3);
            shadow.setHeight(22*v + 8);
            shadow.setWidth(22*v + 8);
            shadow.setColor(Color.rgb(62,76,92,0.3 - v*0.2 ));
        }


        this.btn.setEffect(shadow);
    }

    public void setIsIn(boolean in){
        this.isIn = in;
    }
}
