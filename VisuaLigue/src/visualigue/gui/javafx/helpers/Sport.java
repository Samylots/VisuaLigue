/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.helpers;

import javafx.scene.image.Image;

/**
 *
 * @author Samuel
 */
public class Sport {

    private String name;
    private Image pic;

    public String getName() {
        return name;
    }

    public Image getPic() {
        return pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPic(String path) {
        this.pic = new Image(path);
    }

}
