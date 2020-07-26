package engine.maps.graph.components;

import com.fxgraph.cells.AbstractCell;
import com.fxgraph.graph.Graph;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;

public class CoordinateNode extends AbstractCell {
    private int x;
    private int y;
    private int indexX;
    private int indexY;

    public CoordinateNode(int x, int y, int indexX, int indexY) {
        this.x = x;
        this.y = y;
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    @Override
    public Region getGraphic(Graph graph) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("visual/CoordinateView.fxml");
            fxmlLoader.setLocation(url);
            VBox root = fxmlLoader.load(url.openStream());

//            CoordinateController controller = fxmlLoader.getController();
//            controller.setLocation(x, y);

            return root;
        } catch (Exception e) {
            return new Label("Error when tried to create graphic node !");
        }

    }
}



