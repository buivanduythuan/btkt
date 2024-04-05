package View;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextView extends JFrame {
    private JTextArea textArea;

    public TextView() {
        this.setTitle("Text Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(3);
        this.initComponents();
        this.setUpMenuBar();
        this.setVisible(true);
    }

    private void initComponents() {
        this.textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        this.add(scrollPane, "Center");
    }

    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        newItem.addActionListener(e -> newFile());
        openItem.addActionListener((e) -> {
            try {
                this.openFile();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
        saveItem.addActionListener((e) -> {
            this.saveFile();
        });
        exitItem.addActionListener((e) -> {
            System.exit(0);
        });
    }
    private void newFile() {
        textArea.setText("");
        setTitle("Text Editor");
    }
    private void openFile() throws IOException {
        FileDialog fileDialog = new FileDialog(this, "Open", 0);
        fileDialog.setVisible(true);
        String fileName = fileDialog.getFile();
        String fileDirectory = fileDialog.getDirectory();
        if (fileName != null && fileDirectory != null) {
            File file = new File(fileDirectory, fileName);

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                try {
                    StringBuilder content = new StringBuilder();

                    while(true) {
                        String line;
                        if ((line = reader.readLine()) == null) {
                            this.textArea.setText(content.toString());
                            this.setTitle(fileName);
                            break;
                        }

                        content.append(line).append("\n");
                    }
                } catch (Throwable var9) {
                    try {
                        reader.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                reader.close();
            } catch (IOException var10) {
                System.out.println("Error: Unable to open file.");
                var10.printStackTrace();
            }
        }

    }

    private void saveFile() {
        FileDialog fileDialog = new FileDialog(this, "Save", 1);
        fileDialog.setVisible(true);
        String fileName = fileDialog.getFile();
        String fileDirectory = fileDialog.getDirectory();
        if (fileName != null && fileDirectory != null) {
            File file = new File(fileDirectory, fileName);

            try {
                FileWriter writer = new FileWriter(file);

                try {
                    writer.write(this.textArea.getText());
                    this.setTitle(fileName);
                    JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", 1);
                } catch (Throwable var9) {
                    try {
                        writer.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                writer.close();
            } catch (IOException var10) {
                System.out.println("Error: Unable to save file.");
                var10.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while saving file.", "Error", 0);
            }
        }

    }

    public JTextArea getTextArea() {
        return this.textArea;
    }
}


