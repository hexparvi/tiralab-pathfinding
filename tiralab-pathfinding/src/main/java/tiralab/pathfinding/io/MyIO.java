package tiralab.pathfinding.io;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 */
public class MyIO {
    
    public static int[][] readFromFile(String filepath) {
        BufferedImage image = null;
        File inputFile = null;
        
        try {
            inputFile = new File(filepath);
            image = ImageIO.read(inputFile);
            
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        
        int[][] pixelArray = convertToPixelArray(image);
        
        return pixelArray;
    }
    
    public static void writeToFile(int[][] pixelArray, String extension, String filename) {
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, pixelArray[x][y]);
            }
        }
        
        try {
            ImageIO.write(img, extension,  new File(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static BufferedImage getBufferedImage(String filepath) {
        BufferedImage image = null;
        File inputFile = null;
        
        try {
            inputFile = new File(filepath);
            image = ImageIO.read(inputFile);
            
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        
        return image;
    }
    
    private static int[][] convertToPixelArray(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixels = new int[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = img.getRGB(x, y);
            }
        }
        return pixels;
    }
}
