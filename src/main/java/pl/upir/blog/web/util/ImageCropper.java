package pl.upir.blog.web.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vitalii on 24/08/2015.
 */
public class ImageCropper {

    public static void cropp(File file,String type,int height, int width ,int left, int top ){
        try {
            BufferedImage originalImgage = ImageIO.read(file);
            //System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());
            BufferedImage SubImgage = originalImgage.getSubimage(left, top, width, height);
//            System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());
            //File outputfile = new File("C:/Test/croppedImage.jpg");
            ImageIO.write(SubImgage, type, file);
//            System.out.println("Image cropped successfully: "+outputfile.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resizeImage(File file, int height, int width,String typeOfFile){
        try {
            BufferedImage originalImage = ImageIO.read(file);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(resizedImage, typeOfFile, file);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
