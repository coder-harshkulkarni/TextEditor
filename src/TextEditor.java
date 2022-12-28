import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TextEditor extends JFrame implements ActionListener {
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR); // Cursor
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    JLabel fontLabel = new JLabel("Font Size : ");
    JSpinner fontSizeSpinner = new JSpinner();
    JButton fontColorButton = new JButton("Font Color");
    JButton backgroundColorButton = new JButton("Background Color");
    JCheckBox boldCheckBox = new JCheckBox("Bold");
    //    JCheckBox italicCheckBox = new JCheckBox("Italic");
    JComboBox fontBox;
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");

    TextEditor() {
        // JTextArea Start
        textArea.setWrapStyleWord(true); // Wrap words
        textArea.setLineWrap(true); // Wrap Lines
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        // JTextArea End

        // JScrollPane start
        scrollPane.setPreferredSize(new Dimension(980, 900));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // JScrollPane End

        // JSpinner Start
        fontSizeSpinner.setValue(20); // Set Preferred Value
        fontSizeSpinner.setCursor(handCursor);
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (Integer) fontSizeSpinner.getValue()));
            }
        }); // Add Change Listener for changing the font size
        // JSpinner End

        // JButton Start
        fontColorButton.addActionListener(this); // Adding action listener for color button
        fontColorButton.setCursor(handCursor);
        backgroundColorButton.addActionListener(this);
        backgroundColorButton.setCursor(handCursor);
        // JButton End

        // check box Start
        boldCheckBox.addActionListener(this);
        boldCheckBox.setCursor(handCursor);
//        italicCheckBox.addActionListener(this);
        // check box End

        // array of fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // JComboBox Start
        fontBox = new JComboBox(fonts);
        fontBox.setCursor(handCursor);
        fontBox.setEditable(false);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem(textArea.getFont().getFamily());
        // JComboBox End

        // Menu Bar Start
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        // Menu Bar End

        // Adding components to JFrame Start
        this.setJMenuBar(menuBar); // JMenuBar
        this.add(fontLabel); // JLabel
        this.add(fontSizeSpinner); // JSpinner
        this.add(fontColorButton); // JButton
        this.add(backgroundColorButton);
        this.add(boldCheckBox); // JCheckBox
//        this.add(italicCheckBox); // JCheckBox
        this.add(fontBox); // JComboBox
        this.add(scrollPane); // JScrollPane
        // Adding components to JFrame End

        // JFrame
        this.setTitle("Harsh Text Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // ActionListener for fontColorButton
        if (e.getSource() == fontColorButton) {
            // JColorChooser
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a Color", Color.BLACK);

            textArea.setForeground(color); // Set the foreground text color
        }
        // ActionListener for backgroundColorButton
        if (e.getSource() == backgroundColorButton) {
            // JColorChooser
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a Color", Color.WHITE);

            textArea.setBackground(color); // Set the Background color
        }
        // ActionListener for fontBox
        if (e.getSource() == fontBox) {

            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }
        // ActionListener for boldCheckBox
        if (boldCheckBox.isSelected()) {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.BOLD, textArea.getFont().getSize()));
        } else {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, textArea.getFont().getSize()));
        }
//        if (italicCheckBox.isSelected()) {
//            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.ITALIC, textArea.getFont().getSize()));
//        } else {
//            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, textArea.getFont().getSize()));
//        }

        // ActionListener for Menu bar actions
        // ActionListener for openItem
        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt", "html", "doc", "css", "java", "cpp", "c", "js");

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    fileIn.close();
                }

            }
        }
        // ActionListener for saveItem
        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser(".");

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileOut = null;
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    fileOut.close();
                }
            }
        }
        // ActionListener for exitItem
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    }
}
