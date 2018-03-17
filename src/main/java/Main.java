import Controllers.ControllerDirectories;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Directories.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("Directories");
        ControllerDirectories controller = (ControllerDirectories) loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}