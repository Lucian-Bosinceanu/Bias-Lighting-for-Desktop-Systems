package controller.io;

import model.config.ConfigManager;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private static final String documentsPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Desktop_Lights";
    private static final String presetsPath = documentsPath + File.separator + "presets";

    public static String getTextFromFile(String fileName) throws IOException {

        InputStream inputStream = new FileInputStream(getPresetPathOfFile(fileName));
        return getContentFromInputStream(inputStream);
    }

    private static String getTextFromInternalFile(String fileName) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static_states" + File.separator + fileName);
        return getContentFromInputStream(inputStream);
    }

    private static String getContentFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();

        char c;
        int i;
        while ( (i = inputStream.read()) != -1) {
            c = (char)i;
            content.append(c);
        }

        return content.toString();
    }

    public static InputStream loadImage(String imageName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(imageName);
    }

    public static void writeTextToFile(String fileName, String content) throws IOException {
        OutputStream outputStream = new FileOutputStream(getPresetPathOfFile(fileName));

        outputStream.write(content.getBytes());
    }

    public static String getPresetPath() {
        return presetsPath;
    }

    private static String getPresetPathOfFile(String fileName) {
        return presetsPath + File.separator + fileName;
    }

    public static boolean deleteFile(String fileName) throws IOException {
        Path path = Paths.get(getPresetPathOfFile(fileName));
        return Files.deleteIfExists(path);
    }

    public static boolean createHomeDirectory() {
        File customDir = new File(documentsPath);

        if (!customDir.exists()) {
            if (!customDir.mkdirs()) {
                return false;
            }

            addContentToHomeDirectory();
        }

        return true;
    }

    private static void addContentToHomeDirectory() {
        String presetPath = ConfigManager.getGlobalConfig().getStaticFrameColorStateFolder();
        URL url = FileManager.class.getClassLoader().getResource(presetPath);
        String path = url.getPath();
        File[] files = new File(path).listFiles();

        File savePathDir = new File(presetsPath);
        savePathDir.mkdirs();

        try {
            for (File f : files) {
                String fileSavePath = getPresetPathOfFile(f.getName());

                OutputStream outputStream = new FileOutputStream(fileSavePath);
                outputStream.write(getTextFromInternalFile(f.getName()).getBytes());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
