package loadClasses;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class shadowClass {

    public void shadowImg(ImageView imageTemp, ImageView imageTree)
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

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(10.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.GREY);

        img.setEffect(dropShadow);

        SnapshotParameters parameters = new SnapshotParameters();

        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img.snapshot(parameters, null);



        // store the rounded image in the imageView.
        imageTemp.setImage(image);
    }
}
