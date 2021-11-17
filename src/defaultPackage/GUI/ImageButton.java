package defaultPackage.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**************************************************************************
 *                                                                        *
 *                       GUI component by Tyrael                          *
 *         ImageButton, which is the button could be powerful.            *
 *                                                                        *
 *                       Copyright (c) 2020 LYL                           *
 *                            @author LYL                                 *
 *                            @version 1.1                                *
 **************************************************************************/

public class ImageButton extends StackPane {
    private Rectangle background = new Rectangle();
    private Label showText = new Label();
    private ImageView image_background = new ImageView();
    private Color backgroundColor = new Color(0, 0, 0, 0);

    /**
     * This constructor set a new button with height, width and text.
     * With the basic button style. The default background color is gray,
     * {@code new Color(.5, .5, .5, .8)}.
     *
     * @param height button height
     * @param width button width
     * @param text the show text
     * */
    ImageButton(double width, double height, String text) {
        this.backgroundColor = new Color(.5, .5, .5, .8);
        this.background.setHeight(height);
        this.background.setWidth(width);
        this.background.setFill(backgroundColor);
        this.showText.setText(text);

        this.getChildren().addAll(background, showText);
    }

    ImageButton() {}

    /**
     * This constructor set a new button with height, width and text.
     * The background color is defined at here.
     *
     * The color value rage is 0.0 ~ 1.0
     *
     * @param height button height
     * @param width button width
     * @param text the show text
     * @param r the red channel for background color
     * @param g the green channel for background color
     * @param b the blue channel for background color
     * @param a the alpha channel for background color
     * */
    ImageButton(double width, double height, String text, double r, double g, double b, double a) {
        this.backgroundColor = new Color(r, g, b, a);
        this.background.setHeight(height);
        this.background.setWidth(width);
        this.background.setFill(backgroundColor);
        this.showText.setText(text);
        if (isLight(r, g, b))
            this.showText.setTextFill(new Color(0, 0, 0, 1));
        else
            this.showText.setTextFill(new Color(1, 1, 1, 1));
        this.getChildren().addAll(background, showText);
    }

    /**
     * This constructor set a new button with height, width and text.
     * This new button background is an Image.
     *
     * In this new button background style definition is not recommend
     * and will not effect.
     *
     * @param height button height
     * @param width button width
     * @param background button background Image
     * */
    ImageButton(double width, double height, Image background) {
        this.image_background.setImage(background);
        this.image_background.setFitHeight(height);
        this.image_background.setFitWidth(width);
        this.getChildren().add(image_background);
    }

    /**
     * Set the Button location by taken X and Y.
     * The anchor at top left.
     *
     * @param X the point at x-axis
     * @param Y the point at y-axis
     * */
    void setLocation(double X, double Y) {
        this.setLayoutX(X);
        this.setLayoutY(Y);
    }

    /**
     * Set the size for button. Also called for resize the button
     * by {@code resize()} in {@code main gui class}.
     *
     * @param width the width for this component
     * @param height the height for this component
     * */
    void setSize(double width, double height) {
        this.background.setWidth(width);
        this.background.setHeight(height);
        this.image_background.setFitHeight(height);
        this.image_background.setFitWidth(width);
    }

    void convertHexColour(String Hex){

    }

    /**
     * Style setting
     * Set the effect when mouse over the button
     * the button style will setting on background color
     * and could be defined later.
     *
     * @param opacity the alpha channel when mouse over*/
    void setOver(double opacity) {
        double r, g, b;
        r = backgroundColor.getRed();
        g = backgroundColor.getGreen();
        b = backgroundColor.getBlue();
        backgroundColor = new Color(r, g, b, opacity);
        this.background.setFill(backgroundColor);
    }

    /**
     * Style setting
     * Set the font size for text which displayed on button.
     *
     * @param size the size for font
     * */
    void setFont(double size) {
        this.showText.setFont(Font.font(size));
    }

    /**
     * Style setting
     * Set a new display color for button.
     * This method will override the default color which
     * defined in constructors. This method also may called
     * in listener for change color dynamically.
     *
     * The color value rage is 0.0 ~ 1.0
     *
     * @param r the red channel for bar color
     * @param g the green channel for bar color
     * @param b the blue channel for bar color
     * @param a the alpha channel for bar color
     * */
    void setColor(double r, double g, double b, double a){
        this.backgroundColor = new Color(r,g,b,a);
        this.background.setFill(backgroundColor);
        if (isLight(r, g, b))
            this.showText.setTextFill(new Color(0, 0, 0, 1));
        else
            this.showText.setTextFill(new Color(1, 1, 1, 1));
    }

    void setRadius(double r){
        background.setArcHeight(r);
        background.setArcWidth(r);
    }

    private boolean isLight(double r, double g, double b) {
        return ((0.213 * r+ 0.715 * g + 0.072 * b) * 255> 255 / 2f);
    }
}