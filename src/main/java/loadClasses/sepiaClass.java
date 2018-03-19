package loadClasses;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class sepiaClass {

    public void sepiaImg(ImageView imageTemp, ImageView imageTree)
    {
        SepiaTone sepiaTone = new SepiaTone();
        ImageView img;
        if(imageTemp.getImage() != null)
        {
            img = new ImageView(imageTemp.getImage());
        }
        else
        {
            img = new ImageView (imageTree.getImage());
        }

        img.setEffect(sepiaTone);

        SnapshotParameters parameters = new SnapshotParameters();

        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img.snapshot(parameters, null);



        // store the rounded image in the imageView.
        imageTemp.setImage(image);
    }
}
