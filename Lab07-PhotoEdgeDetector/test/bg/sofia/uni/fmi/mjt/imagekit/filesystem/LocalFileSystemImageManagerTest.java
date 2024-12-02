package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class LocalFileSystemImageManagerTest {

    FileSystemImageManager manager;

    @BeforeEach
    void setUp() {
        manager = new LocalFileSystemImageManager();
    }

    @Test
    void testLoadImageWithNullPath() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null), "Null path should throw IllegalArgumentException");
    }

    @Test
    void testLoadImageWithEmptyPath() {
        assertThrows(IOException.class, ()-> manager.loadImage(new File("")), "Empty path should throw IOException");
    }

    @Test
    void testLoadImageWithInvalidExtension() throws IOException {
        File path = new File("resources", "car.txt");
        path.createNewFile();
        assertThrows(IOException.class, ()-> manager.loadImage(new File("resources", "car.txt")), "Invalid file extension should throw IOException");
        path.delete();
    }

    @Test
    void testLoadImageWithInvalidPath() {
        assertThrows(IOException.class, ()-> manager.loadImage(new File("resources", "car1.png")), "Invalid file should throw IOException");
    }

    @Test
    void testLoadImageWithDirectory() {
        assertThrows(IOException.class, ()-> manager.loadImage(new File("resources")), "Directory is submitted to load file and should throw IOException");
    }

    @Test
    void testLoadImageWithInvalidFileDirectory() {
        assertThrows(IOException.class, ()-> manager.loadImage(new File("resources1", "car.jpg")), "Directory is submitted to load file and should throw IOException");
    }

    @Test
    void testLoadImageValidFile() throws IOException {
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.getName()).thenReturn("valid.jpg");

        BufferedImage mockImage = mock(BufferedImage.class);
        ImageIO i = mock(ImageIO.class);
        when(i.read(mockFile)).thenReturn(mockImage);

        BufferedImage result = manager.loadImage(mockFile);

        assertNotNull(result, "The image should be successfully loaded");
    }

    @Test
    void testLoadImagesFromDirectoriesWithNotDirectory() {
        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(new File("resources", "car.jpg")), "File is submitted to load file from directories and should throw IOException");
    }

    @Test
    void testLoadImagesFromDirectoryValid() throws IOException {
        File mockDirectory = mock(File.class);
        when(mockDirectory.exists()).thenReturn(true);
        when(mockDirectory.isDirectory()).thenReturn(true);

        File mockImageFile = mock(File.class);
        when(mockImageFile.exists()).thenReturn(true);
        when(mockImageFile.isFile()).thenReturn(true);
        when(mockImageFile.getName()).thenReturn("image.jpg");

        when(mockDirectory.listFiles()).thenReturn(new File[]{mockImageFile});

        BufferedImage mockImage = mock(BufferedImage.class);
        mockStatic(ImageIO.class);
        when(ImageIO.read(mockImageFile)).thenReturn(mockImage);

        assertEquals(1, manager.loadImagesFromDirectory(mockDirectory).size(),
            "Directory with one valid image should load successfully");
    }

    @Test//
    void testSaveImageWithNullImage() {
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(null, new File("resources", "car1.jpg")), "Null image should throw IOException");
    }

    @Test//
    void testSaveImageWithInvalidParent() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        assertThrows(IOException.class, () -> manager.saveImage(image, new File("resources1", "car1.jpg")), "Null image should throw IOException");
    }

    @Test
    void testSaveImageWithNullImagePath() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(image, null), "Null image file should throw IOException");
    }

    @Test
    void testSaveImageWhenFileExist() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        assertThrows(IOException.class, () -> manager.saveImage(image, new File("resources", "car.jpg")), "When file exist should throw IOException");
    }

    @Test
    void testSaveImageValid() throws IOException {
        BufferedImage mockImage = mock(BufferedImage.class);
        File mockFile = mock(File.class);

        when(mockFile.exists()).thenReturn(false);
        when(mockFile.getParentFile()).thenReturn(mock(File.class));
        when(mockFile.getParentFile().exists()).thenReturn(true);
        when(mockFile.getName()).thenReturn("output.jpg");

        ImageIO i = mock(ImageIO.class);
        when(i.write(mockImage, "jpg", mockFile)).thenReturn(true);

        assertDoesNotThrow(() -> manager.saveImage(mockImage, mockFile),
            "Saving a valid image should not throw any exceptions");
    }

}
