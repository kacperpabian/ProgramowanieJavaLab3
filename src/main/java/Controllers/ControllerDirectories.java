package Controllers;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.*;
import java.lang.ref.WeakReference;
import javafx.concurrent.Task;

import loadClasses.blurClass;
import loadClasses.lightingClass;
import loadClasses.sepiaClass;
import loadClasses.shadowClass;

import javax.imageio.ImageIO;
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

    @FXML
    private ImageView imageTemp;

    String dirPath, tempDirPath;

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
        tempDirPath = dirPath;
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
        Image chosenImage = new Image(new FileInputStream(tempDirPath));
        imageTree.setImage(chosenImage);
        imageTemp.setImage(chosenImage);

    }

    @FXML
    void blurImage(ActionEvent event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
        Class cls1 = clsLoader.loadClass("loadClasses.blurClass");
        Object obj1 = cls1.newInstance();
        ((blurClass)obj1).blurImg(imageTemp, imageTree);
    }

    @FXML
    void makeShadow(ActionEvent event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
        Class cls1 = clsLoader.loadClass("loadClasses.shadowClass");
        Object obj1 = cls1.newInstance();
        ((shadowClass)obj1).shadowImg(imageTemp, imageTree);

    }

    @FXML
    void saveImage(ActionEvent event) {
        WritableImage image = imageTemp.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File(tempDirPath);

        try {
            System.out.println(tempDirPath);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            Image newImage = new Image(new FileInputStream(tempDirPath));
            imageTemp.setImage(newImage);
            imageTree.setImage(newImage);


        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    @FXML
    void resetImage(ActionEvent event) {

        ImageView img = new ImageView (imageTree.getImage());

        SnapshotParameters parameters = new SnapshotParameters();

        WritableImage image = img.snapshot(parameters, null);

        // store the rounded image in the imageView.
        imageTemp.setImage(image);
    }

    @FXML
    void getSepia(ActionEvent event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
        Class cls1 = clsLoader.loadClass("loadClasses.sepiaClass");
        Object obj1 = cls1.newInstance();
        ((sepiaClass)obj1).sepiaImg(imageTemp, imageTree);
    }

    @FXML
    void getLighting(ActionEvent event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
        Class cls1 = clsLoader.loadClass("loadClasses.lightingClass");
        Object obj1 = cls1.newInstance();
        ((lightingClass)obj1).lightingImg(imageTemp, imageTree);
    }

}
