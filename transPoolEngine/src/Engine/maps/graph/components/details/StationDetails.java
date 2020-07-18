package Engine.maps.graph.components.details;

import java.util.List;

public class StationDetails {
    private String name;
    private int x;
    private int y;
    private List<String> drives;

    public StationDetails(List<String> drives) {
        this.drives = drives;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getDrives() {
        return drives;
    }
}
