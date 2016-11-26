/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.inter.utils;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class IdGenerator implements Serializable {

    private static final IdGenerator instance = new IdGenerator();
    private int actualId = 0;

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        actualId++;
        return actualId;
    }

    public void restoreId(int id) {
        actualId = id;
    }

    public int getLastId() {
        return actualId;
    }

}
