/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import java.util.ArrayList;
import java.util.List;
import visualigue.domain.main.entities.Player;
import visualigue.domain.main.Team;

/**
 *
 * @author Bruno L.L.
 */
public class TeamDTO {

    public String name;
    public String color;
    public List<PlayerDTO> players = new ArrayList<>();

    public TeamDTO(Team team) {
        this.name = team.getName();
        this.color = team.getColor();

        int i = 1;
        for (Player player : team.getPlayers()) {
            PlayerDTO playerDTO = new PlayerDTO(player);
            players.add(playerDTO);
            i++;
        }
    }
}
