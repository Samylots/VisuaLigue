/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.gui.javafx.fxdrawers;

import java.util.List;
import javafx.scene.image.Image;
import visualigue.domain.VisuaLigueController;
import visualigue.domain.game.entities.Entity;
import visualigue.domain.game.entities.Obstacle;
import visualigue.domain.game.entities.Player;
import visualigue.domain.game.Position;
import visualigue.domain.utils.Coords;
import visualigue.domain.utils.Dimension;
import visualigue.gui.javafx.fxcontrollers.VisuaLigueBoard;

/**
 *
 * @author Samuel
 */
public class GameDrawer {

    private final VisuaLigueBoard canvas;
    private final VisuaLigueController domain;

    public GameDrawer(VisuaLigueBoard canvas, VisuaLigueController domain) {
        this.canvas = canvas;
        this.domain = domain;
    }

    public void drawGame() {
        List<Position> positions = domain.getActualPositions();
        positions.stream().forEach((position) -> {
            Entity entity = position.getEntity();
            if (entity instanceof Player) {
                drawPlayer((Player) entity, getPixelPosition(position.getCoords()));
            } else if (entity instanceof Obstacle) {
                drawObstacle((Obstacle) entity, getPixelPosition(position.getCoords()));
            } else if (entity instanceof Entity) {
                drawEntity((Entity) entity, getPixelPosition(position.getCoords()));
            } else {
                //error or not?
            }
        });
    }

    private void drawPlayer(Player player, Coords position) {
        Dimension playerDimension = player.getDimension();
        canvas.getGraphicsContext2D().drawImage(new Image(player.getPicturePath()), position.getX(), position.getY(), playerDimension.getWidth(), playerDimension.getHeight());
        if (domain.isShowingRoles()) {
            //TODO draw roles/names etc.
        }
    }

    private void drawObstacle(Obstacle obstacle, Coords position) {
        Dimension dimension = obstacle.getDimension();
        canvas.getGraphicsContext2D().drawImage(new Image(obstacle.getPicturePath()), position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight());
    }

    private void drawEntity(Entity accessory, Coords position) {
        Dimension dimension = accessory.getDimension();
        canvas.getGraphicsContext2D().drawImage(new Image(accessory.getPicturePath()), position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight());
    }

    private Coords getPixelPosition(Coords domainCoords) {
        return canvas.getConverter().meterToPixel(domainCoords, canvas.getActualFieldDimension());
    }

}
