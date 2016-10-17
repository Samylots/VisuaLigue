/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain.dumies;

/**
 *
 * @author Samuel
 */
public class Sport {

    String name;
    String picUrl;
    String id;

    public Sport() {
    }

    public Sport(String name, String picUrl, String id) {
        this.name = name;
        this.picUrl = picUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
