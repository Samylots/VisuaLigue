/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import java.util.ArrayList;
import java.util.List;
import visualigue.domain.main.Sport;
import visualigue.domain.main.Team;
import visualigue.domain.main.entities.Accessory;
import visualigue.utils.Dimension;

/**
 *
 * @author Bruno L.L.
 */
public class SportDTO {
    
    public int id;
    public String name;
    public String fieldPicturePath;
    public Dimension fieldDimension;
    public AccessoryDTO accessory;
    public List<TeamDTO> teams;
    
    public SportDTO(Sport sport) {
        this.id = sport.getId();
        this.name = sport.getName();
        this.fieldDimension = sport.getFieldDimension();
        this.fieldPicturePath = sport.getFieldPicturePath();
        this.accessory = new AccessoryDTO(sport.getAccessory());
        
        this.teams = new ArrayList<TeamDTO>();
        
        for (Team team : sport.getTeams()) {
            teams.add(new TeamDTO(team));
        }
    }
}
