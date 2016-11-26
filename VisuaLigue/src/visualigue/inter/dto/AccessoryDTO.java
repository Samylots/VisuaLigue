/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.dto;

import visualigue.domain.main.entities.Accessory;
import visualigue.inter.utils.Dimension;
import visualigue.inter.dto.EntityDTO;

/**
 *
 * @author Bruno L.L.
 */
public class AccessoryDTO extends EntityDTO {

    public AccessoryDTO(Accessory accessory) {
        super(accessory.getDimension(), accessory.getId(), accessory.getPicturePath());
    }
}
