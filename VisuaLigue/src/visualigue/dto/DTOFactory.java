/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.game.Team;
import visualigue.domain.game.entities.Player;
import visualigue.dto.*;

/**
 *
 * @author Bruno L.L.
 */
public class DTOFactory {
    
    public TeamDTO createTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO(team);
        return teamDTO;
    }
}