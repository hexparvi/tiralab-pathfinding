package tiralab.pathfinding.io;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 */
public class MyIO {
    
    /**
     * Reads a .png file and turns it into a 2d array of pixel RGB values.
     * @param filepath path pointing to the file to be read
     * @return 2d array of pixel RGB values
     */
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
    
    /**
     * Writes a 2d array of pixel RGB values into an image file.
     * @param pixelArray 2d array of pixels
     * @param extension file extension
     * @param filename name of file to be written to
     */
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
    
    /**
     * Reads a .png file and turns it into BufferedImage.
     * @param filepath path pointing to file to be read
     * @return BufferedImage object based on the file
     */
    public static BufferedImage getBufferedImageFromFilepath(String filepath) {
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
    
    /**
     * Creates a BufferedImage object based on a 2d array of pixel RGB values.
     * @param pixelArray 2d pixel array to be turned into a BufferedImage
     * @return BufferedImage object based on the 2d array
     */
    public static BufferedImage getBufferedImageFromPixels(int[][] pixelArray) {
        int width = pixelArray.length;
        int height = pixelArray[0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, pixelArray[x][y]);
            }
        }
        
        return img;
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
