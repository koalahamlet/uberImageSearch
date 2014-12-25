package mikecanco.de.uberimagesearcher;

/**
 * Created by koalahamlet on 12/25/14.
 */
import java.io.Serializable;

public class SearchFilter implements Serializable {

    private String size, color, type, site;

    public SearchFilter() {}

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}

