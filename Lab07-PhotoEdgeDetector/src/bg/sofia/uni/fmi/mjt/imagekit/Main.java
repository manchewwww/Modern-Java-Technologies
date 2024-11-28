package bg.sofia.uni.fmi.mjt.imagekit;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection.SobelEdgeDetection;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.FileSystemImageManager;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileSystemImageManager manager = new LocalFileSystemImageManager();

        BufferedImage car = manager.loadImage(new File("resources", "car.jpg"));
        BufferedImage kitten = manager.loadImage(new File("resources", "kitten.png"));

        ImageAlgorithm grayscaleAlgorithm = new LuminosityGrayscale();
        BufferedImage greyCar = grayscaleAlgorithm.process(car);
        BufferedImage greyKitten = grayscaleAlgorithm.process(kitten);

        ImageAlgorithm edgeDetectionAlgorithm = new SobelEdgeDetection(grayscaleAlgorithm);
        BufferedImage edgeCar = edgeDetectionAlgorithm.process(car);
        BufferedImage edgeKitten = edgeDetectionAlgorithm.process(kitten);

        manager.saveImage(greyCar, new File("resources", "greyCar.jpg"));
        manager.saveImage(greyKitten, new File("resources", "greyKitten.png"));
        manager.saveImage(edgeCar, new File("resources", "edgeCar.jpg"));
        manager.saveImage(edgeKitten, new File("resources", "edgeKitten.jpg"));
    }

}
