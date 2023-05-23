/**
 * Package of game with entity (prayer can interact with it).
 */
package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Background class represents a background entity in the game.
 * It extends the Entity class and implements Serializable.
 */
@Log
@Getter
@Setter
public class Background extends Entity implements Serializable {
    /**
     * The file name for the grass texture.
     */
    private static final String GRASS_FILE_NAME = "/grass.png";

    /**
     * The file name for the asphalt texture.
     */
    private static final String ASPHALT_FILE_NAME = "/asphalt.png";

    /**
     * The file name for the floor texture.
     */
    private static final String FLOOR_FILE_NAME = "/floor.png";

    /**
     * The file name for the plate texture.
     */
    private static final String PLATE_FILE_NAME = "/plate.png";

    /**
     * The file name for the cushion texture.
     */
    private static final String CUSHION_FILE_NAME = "/cushion.png";

    /**
     * The image associated with the game win view.
     */
    private ImageIcon image;

    /**
     * Constructs a Background object with the specified position.
     *
     * @param x The x-coordinate of the background's position
     * @param y The y-coordinate of the background's position
     */
    public Background(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Sets the image of the background based on the provided flags.
     *
     * @param isGrass   Flag indicating if the background should be grass
     * @param isAsphalt Flag indicating if the background should be asphalt
     * @param isFloor   Flag indicating if the background should be a floor
     * @param isPlate   Flag indicating if the background should be a plate
     * @param isCushion Flag indicating if the background should be a cushion
     */
    public void setImage(final boolean isGrass, final boolean isAsphalt,
                         final boolean isFloor, final boolean isPlate,
                         final boolean isCushion) {
        if (isGrass) {
            setImageGrass(this);
        } else if (isAsphalt) {
            setImageAsphalt(this);
        } else if (isFloor) {
            setImageFloor(this);
        } else if (isPlate) {
            setImagePlate(this);
        } else if (isCushion) {
            setImageCushion(this);
        }
    }

    /**
     * Sets the background image to an asphalt image.
     *
     * @param o The Background object
     */
    public void setImageAsphalt(final Background o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(ASPHALT_FILE_NAME));
        o.image = new ImageIcon(img);
    }

    /**
     * Sets the background image to a grass image.
     *
     * @param o The Background object
     */
    public void setImageGrass(final Background o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(GRASS_FILE_NAME));
        o.image = new ImageIcon(img);
    }

    /**
     * Sets the background image to a floor image.
     *
     * @param o The Background object
     */
    public void setImageFloor(final Background o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FLOOR_FILE_NAME));
        o.image = new ImageIcon(img);
    }

    /**
     * Sets the background image to a plate image.
     *
     * @param o The Background object
     */
    public void setImagePlate(final Background o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(PLATE_FILE_NAME));
        o.image = new ImageIcon(img);
    }

    /**
     * Sets the background image to a cushion image.
     *
     * @param o The Background object
     */
    public void setImageCushion(final Background o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(CUSHION_FILE_NAME));
        o.image = new ImageIcon(img);
    }

    /**
     * Draws the background on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the background.
     *
     * @return The bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
