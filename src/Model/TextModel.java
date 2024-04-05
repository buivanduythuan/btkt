package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.stream.Collectors;
import javax.swing.JTextArea;

public class TextModel {
    public void readFileOrDirectory(File file, JTextArea textArea) throws IOException {
        if (file.isDirectory()) {
            this.traverseDirectory(file, textArea);
        } else {
            textArea.setText(this.readFile(file));
        }

    }

    public String readFile(File file) throws IOException {
        if (file.getName().endsWith(".txt")) {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String var3;
            try {
                var3 = (String)reader.lines().collect(Collectors.joining("\n"));
            } catch (Throwable var6) {
                try {
                    reader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            reader.close();
            return var3;
        } else {
            return "";
        }
    }

    public void saveFile(File file, String content) throws IOException {
        PrintWriter writer = new PrintWriter(file);

        try {
            writer.write(content);
        } catch (Throwable var7) {
            try {
                writer.close();
            } catch (Throwable var6) {
                var7.addSuppressed(var6);
            }

            throw var7;
        }

        writer.close();
    }

    private void traverseDirectory(File directory, JTextArea textArea) throws IOException {
        StringBuilder allContent = new StringBuilder();
        Files.walk(directory.toPath()).filter((x$0) -> {
            return Files.isRegularFile(x$0, new LinkOption[0]);
        }).forEach((file) -> {
            try {
                allContent.append(this.readFile(file.toFile())).append("\n");
            } catch (IOException var4) {
                var4.printStackTrace();
            }

        });
        textArea.setText(allContent.toString());
    }
}
