/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.services.exporters;

import visualigue.inter.utils.exceptions.InvalidGameExporterTypeException;

/**
 *
 * @author Samuel
 */
public class GameExporterFactory {

    public static GameExporter getExporter(String type) {
        if (type == null) {
            throw new InvalidGameExporterTypeException();
        }
        if (type.equalsIgnoreCase("PNG")) {
            return new PNGExporter();

        } else if (type.equalsIgnoreCase("JPG") || type.equalsIgnoreCase("JPEG")) {
            return new JPGExporter();

        }
        throw new InvalidGameExporterTypeException();
    }

}
