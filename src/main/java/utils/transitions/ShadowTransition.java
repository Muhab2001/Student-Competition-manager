package utils.transitions;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class ShadowTransition extends Transition {

    final private Node node;
    private boolean isIn;
    private boolean raised;
    final private DropShadow shadow;

    public ShadowTransition(Duration duration, Node node, DropShadow shadow){
        setCycleDuration(duration);
        this.node = node;
        this.shadow = shadow;
        this.raised = false;
    }
    @Override
    protected void interpolate(double v) {
        if (!raised)
            if (isIn) {
                shadow.setOffsetY(10 - (v*7));
                shadow.setHeight(30 - (22*v));
                shadow.setWidth(30 - (22*v));
                shadow.setColor(Color.rgb(62,76,92,0.1 + (0.2*v)));
            }else{
                shadow.setOffsetY(7*v + 3);
                shadow.setHeight(22*v + 8);
                shadow.setWidth(22*v + 8);
                shadow.setColor(Color.rgb(62,76,92,0.3 - v*0.2 ));
            }
        else
            if (isIn) {
                shadow.setOffsetY(10 + (v*3));
                shadow.setHeight(30 + (v*15));
                shadow.setWidth(30 + (v*15));
                shadow.setColor(Color.rgb(62,76,92,0.1 - (0.06*v)));
            }else{
                shadow.setOffsetY(13 - (3*v));
                shadow.setHeight(55 - (15*v));
                shadow.setWidth(55 - (15*v));
                shadow.setColor(Color.rgb(62,76,92,0.04 + (0.06*v)));
            }


        this.node.setEffect(shadow);
    }

    public void setIsIn(boolean in){
        this.isIn = in;
    }

    public void setRaised(boolean raised){
        this.raised = raised;
    }
}
