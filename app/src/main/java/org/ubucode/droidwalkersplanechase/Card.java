package org.ubucode.droidwalkersplanechase;

/**
 * Created by Jon on 7/20/2014.
 */
public class Card {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getChaos() {
        return chaos;
    }

    public void setChaos(String chaos) {
        this.chaos = chaos;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    private int id;
    private String name;
    private String effect;
    private String chaos;
    private String imgPath;
    private String planeType;
}
