package loadClasses;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class lightingClass {

    public void lightingImg(ImageView imageTemp, ImageView imageTree)
    {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(50.0);
        light.setElevation(30.0);
        light.setColor(Color.LIGHTYELLOW);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(50.0);

        ImageView img;
        if(imageTemp.getImage() != null)
        {
            img = new ImageView(imageTemp.getImage());
        }
        else
        {
            img = new ImageView (imageTree.getImage());
        }

        img.setEffect(lighting);

        SnapshotParameters parameters = new SnapshotParameters();

        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img.snapshot(parameters, null);



        // store the rounded image in the imageView.
        imageTemp.setImage(image);
    }

}
