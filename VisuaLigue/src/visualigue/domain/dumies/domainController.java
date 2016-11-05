/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.dumies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class domainController implements Serializable {

    List<Sport> sports = new ArrayList<>();
    int sportCpt = 0;

    private domainController() {
        initSports();
        sportCpt = sports.size();
    }

    private static domainController instance = null;

    public static domainController getInstance() {
        if (instance == null) {
            instance = new domainController();
        }
        return instance;
    }

    private void initSports() {
        sports.add(new Sport("/", "Soccer", "0"));
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void addSport(String path, String name) {
        sports.add(new Sport(path, name, Integer.toString(sportCpt)));
        ++sportCpt;
    }

    public void deleteSport(String id) {
        boolean found = false;
        int i = 0;
        while (i < sports.size() && !found) {
            if (sports.get(i).getId().equals(id)) {
                sports.remove(i);
                found = true;
            }
            ++i;
        }
    }

}
