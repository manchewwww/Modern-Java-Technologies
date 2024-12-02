package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    private void validateImage(File image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("imagesDirectory is null");
        }
        if (!image.exists()) {
            throw new IOException(image.getAbsolutePath() + " does not exist");
        }
    }

    private String getExtensions(File image) {
        String fileName = image.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String extension = "";
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) { // Ensure dot is not the last character
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }

    private void validateFormatOfImage(File image) throws IOException {
        if (!getExtensions(image).equals("jpg") && !getExtensions(image).equals("png") &&
            !getExtensions(image).equals("bmp")) {
            throw new IOException(image.getAbsolutePath() + " is not a valid image format");
        }
    }

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        validateImage(imageFile);
        if (!imageFile.isFile()) {
            throw new IOException(imageFile.getAbsolutePath() + " is not a file");
        }
        validateFormatOfImage(imageFile);

        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException(imageFile.getAbsolutePath() + " could not be loaded");
        }
        return image;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        validateImage(imagesDirectory);
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
            throw new IllegalArgumentException("imagesDirectory is null");
        }
        if (imageFile.exists()) {
            throw new IOException(imageFile.getAbsolutePath() + " exist");
        }

        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        if (!imageFile.getParentFile().exists()) {
            throw new IOException(imageFile.getParentFile().getAbsolutePath() + " does not exist");
        }

        validateFormatOfImage(imageFile);
        ImageIO.write(image, this.getExtensions(imageFile), imageFile);
    }

}
