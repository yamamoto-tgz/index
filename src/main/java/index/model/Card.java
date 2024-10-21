package index.model;

import java.util.ArrayList;
import java.util.List;

public final class Card {
    private int id;
    private String frontText;
    private String backText;
    private List<String> frontImages = new ArrayList<>();
    private List<String> backImages = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public List<String> getFrontImages() {
        return frontImages;
    }

    public void setFrontImages(List<String> images) {
        this.frontImages = frontImages;
    }

    public List<String> getBackImages() {
        return backImages;
    }

    public void setBackImages(List<String> backImages) {
        this.backImages = backImages;
    }
}
