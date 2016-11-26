/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.events;

import javafx.scene.Parent;

/**
 *
 * @author Bruno L.L.
 */
public interface FramesListener extends Listener {

    public void init(Parent parent);

    public void updateFrames();
}
