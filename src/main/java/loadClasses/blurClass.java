package loadClasses;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class blurClass {
    public void blurImg(ImageView imageTemp, ImageView imageTree)
    {
        ImageView img;
        if(imageTemp.getImage() != null)
        {
            img = new ImageView(imageTemp.getImage());
        }
        else
        {
            img = new ImageView (imageTree.getImage());
        }
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(10.5);
        img.setEffect(gb);
        SnapshotParameters parameters = new SnapshotParameters();

        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img.snapshot(parameters, null);

        imageTemp.setImage(image);
    }
}
