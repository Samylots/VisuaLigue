/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.dto;

import visualigue.domain.main.entities.Accessory;
import visualigue.utils.Dimension;
import visualigue.dto.EntityDTO;

/**
 *
 * @author Bruno L.L.
 */
public class AccessoryDTO extends EntityDTO {

    public AccessoryDTO(Accessory accessory) {
        super(accessory.getDimension(), accessory.getId(), accessory.getPicturePath());
    }
}
