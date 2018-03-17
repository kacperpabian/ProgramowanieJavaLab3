package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.lang.ref.WeakReference;
import javafx.concurrent.Task;
import javafx.scene.effect.GaussianBlur;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerDirectories {
    @FXML
    private TreeView<String> treeDirectory;
    @FXML
    private Button buttonLoad;

    @FXML
    private ImageView imageTree;

    String dirPath;

    @FXML
    void loadFolder(ActionEvent event) throws MalformedURLException {
        Stage stage = (Stage) buttonLoad.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        File choice = dc.showDialog(stage);
        if(choice == null || ! choice.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not open directory");
            alert.setContentText("The file is invalid.");

            alert.showAndWait();
        } else {
            dirPath = choice.getAbsolutePath();
            treeDirectory.setRoot(getNodesForDirectory(choice));
        }
    }


    public TreeItem<String> getNodesForDirectory(File directory) throws MalformedURLException { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for(File f : directory.listFiles()) {
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                if(f.getName().matches("(?i).*.jpg") || f.getName().matches("(?i).*.png"))
                {
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            WeakReference<ImageView> imageForFile = new WeakReference<>(new ImageView(new Image(f.toURI().toURL().toExternalForm(), 50, 50, true, true)));
                            Node jsonImage = imageForFile.get();

                            TreeItem<String> temp = new TreeItem<String>(f.getName());
                            temp.setGraphic(jsonImage);
                            root.getChildren().add(temp);
                            return null;
                        }
                    };
                    new Thread(task).start();
                }
                else
                {
                    TreeItem<String> temp = new TreeItem<String>(f.getName());
                    root.getChildren().add(temp);
                }

            }
        }
        return root;
    }

    @FXML
    void manipulateImage(ActionEvent event) throws FileNotFoundException {
        String imgVal = treeDirectory.getSelectionModel().getSelectedItem().getValue();
        String tempDirPath = dirPath;
        TreeItem tempParent = treeDirectory.getSelectionModel().getSelectedItem().getParent();
        List<String> tab = new ArrayList<>();


        while(tempParent != null)
        {
            tab.add(tempParent.getValue().toString());
            tempParent = tempParent.getParent();
        }
        for (int j = tab.size()-2;j>=0;j--)
        {
            tempDirPath += "\\" + tab.get(j);
        }

        tempDirPath += "\\" + imgVal;
        imageTree.setImage(new Image(new FileInputStream(tempDirPath)));

    }

    @FXML
    void blurImage(ActionEvent event)
    {
        ImageView img = new ImageView (imageTree.getImage());
        GaussianBlur gb = new GaussianBlur(9999);
        img.setEffect(gb);
        imageTree.setImage(img.getImage());
        System.out.println("done");
    }
}
