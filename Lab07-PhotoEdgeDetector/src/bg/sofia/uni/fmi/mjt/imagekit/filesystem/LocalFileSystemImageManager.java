package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }
        if (!imageFile.exists()) {
            throw new IOException(imageFile.getAbsolutePath() + " does not exist");
        }
        if (!imageFile.isFile()) {
            throw new IOException(imageFile.getAbsolutePath() + " is not a file");
        }
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException(imageFile.getAbsolutePath() + " could not be loaded");
        }
        return image;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("imagesDirectory is null");
        }
        if (!imagesDirectory.exists()) {
            throw new IOException(imagesDirectory.getAbsolutePath() + " does not exist");
        }
        if (!imagesDirectory.isDirectory()) {
            throw new IOException(imagesDirectory.getAbsolutePath() + " is not a directory");
        }
        List<BufferedImage> images = new ArrayList<>();
        for (File file : imagesDirectory.listFiles()) {
            images.add(loadImage(file));
        }
        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        if (imageFile.exists()) {
            throw new IOException(imageFile.getAbsolutePath() + " already exists");
        }
        if (!imageFile.getParentFile().exists()) {
            throw new IOException(imageFile.getParentFile().getAbsolutePath() + " does not exist");
        }
        ImageIO.write(image, "png", imageFile);
    }

}
