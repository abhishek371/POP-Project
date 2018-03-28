//importing different packages that is to be used in this.

import com.sun.glass.events.KeyEvent;

import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class TextPad extends JFrame {

    //Declaration
    JTextArea mainarea;
    JMenuBar menubar;
    JMenu mFile, mEdit, mHelp, mFormat, mFont;
    JMenuItem iNew, iOpen, iSave, iSaveAs, iExit, iCut, iCopy, iPaste, iColor, iStyle, about, references;
    JCheckBoxMenuItem wordWrap;
    String filename;
    JFileChooser jFileChooser;
    String fileContent;
    UndoManager undo;
    UndoAction undoAction;
    RedoAction redoAction;

    JFontChooser jFontChooser;

    String aboutString = "This is a simple Textpad Desktop Application based on the Microsoft Notepad. It is useful to take down important notes so as to refer them for future use.";
    String authors = "Created by:";
    String author1 = "16IT114 - Suyash";
    String author2 = "16IT202 - Abhishek";
    
    //Constructor
    public TextPad() {
        initComponent();

        //Initializing Save option and adding action to it
        iSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        //Initializing Save As option and adding action to it
        iSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        //Initializing Open option and adding action to it
        iOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        //Initializing New File option and adding action to it
        iNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open_new();
            }
        });

        //Initializing option and adding action to it
        iExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Initializing option and adding action to it
        iCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainarea.cut();
            }
        });

        iCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainarea.copy();
            }
        });

        iPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainarea.paste();
            }
        });

        mainarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }
        });

        wordWrap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wordWrapAction();
            }
        });

        iStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jFontChooser = new JFontChooser();
                jFontChooser.setVisible(true);
            }
        });

        jFontChooser.getOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainarea.setFont(jFontChooser.font());
                jFontChooser.setVisible(false);
            }
        });

        jFontChooser.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFontChooser.setVisible(false);
            }
        });

        iColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(rootPane, "Choose Font Colour ", Color.BLUE);
                mainarea.setForeground(c);
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutAction();
            }
        });

        references.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                referencesAction();
            }
        });
    }

    private void initComponent() {

        jFontChooser = new JFontChooser();

        jFileChooser = new JFileChooser(".");
        //Text Area Created and Scrollbar is added
        mainarea = new JTextArea();

        //adding undo feature
        undo = new UndoManager();
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("/img/undo.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("/img/redo.png"));
        undoAction = new UndoAction(undoIcon);
        redoAction = new RedoAction(redoIcon);

        getContentPane().add(mainarea);
        getContentPane().add(new JScrollPane(mainarea), BorderLayout.CENTER);
        setTitle("Untitled Textpad");
        setSize(800, 600);
        /*ImageIcon titlecon = new ImageIcon("/img/undo.png");
        setIconImage(titlecon);*/
        //menu bar
        menubar = new JMenuBar();
        //menu
        mFile = new JMenu("File");
        mEdit = new JMenu("Edit");
        mFormat = new JMenu("Format");
        mFont = new JMenu("Font");
        mHelp = new JMenu("Help");

        //add icon to menu items
        ImageIcon newIcon = new ImageIcon(getClass().getResource("/img/3.png"));
        ImageIcon openIcon = new ImageIcon(getClass().getResource("/img/open.png"));
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/img/save.png"));
        ImageIcon saveAsIcon = new ImageIcon(getClass().getResource("/img/saveAs.png"));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/img/exit.png"));
        ImageIcon cutIcon = new ImageIcon(getClass().getResource("/img/cut.png"));
        ImageIcon copyIcon = new ImageIcon(getClass().getResource("/img/copy.png"));
        ImageIcon pasteIcon = new ImageIcon(getClass().getResource("/img/paste.png"));
        ImageIcon aboutIcon = new ImageIcon(getClass().getResource("/img/about.png"));
        ImageIcon refIcon = new ImageIcon(getClass().getResource("/img/ref.png"));
        ImageIcon fontIcon = new ImageIcon(getClass().getResource("/img/font.png"));
        ImageIcon colorIcon = new ImageIcon(getClass().getResource("/img/color.png"));
        

        //menu item
        iNew = new JMenuItem(" New", newIcon);
        iOpen = new JMenuItem(" Open", openIcon);
        iSave = new JMenuItem(" Save", saveIcon);
        iSaveAs = new JMenuItem(" Save As", saveAsIcon);
        iExit = new JMenuItem(" Exit", exitIcon);
        iCut = new JMenuItem(" Cut", cutIcon);
        iCopy = new JMenuItem(" Copy", copyIcon);
        iPaste = new JMenuItem(" Paste", pasteIcon);
        iColor = new JMenuItem(" Color",colorIcon);
        iStyle = new JMenuItem("Style",fontIcon);
        wordWrap = new JCheckBoxMenuItem("Word Wrap");
        //wordWrap.setMargin(new Insets(500, 10000, 0, 0));
        about = new JMenuItem("About", aboutIcon);
        references = new JMenuItem("References", refIcon);

        //adding shortcut keys to menu items
        iNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        iOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        iSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        iCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        iCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        iPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        //add menu items to menu
        mFile.add(iNew);
        mFile.add(iOpen);
        mFile.add(iSave);
        mFile.add(iSaveAs);
        mFile.add(iExit);

        mEdit.add(undoAction);
        mEdit.add(redoAction);
        mEdit.add(iCut);
        mEdit.add(iCopy);
        mEdit.add(iPaste);

        mFormat.add(wordWrap);
        mFont.add(iStyle);
        mFont.add(iColor);

        mHelp.add(about);
        mHelp.add(references);

        //add menu item to menubar
        menubar.add(mFile);
        menubar.add(mEdit);
        menubar.add(mFormat);
        menubar.add(mFont);
        menubar.add(mHelp);

        //add menubar to frame
        setJMenuBar(menubar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void save() {
        PrintWriter fout = null;
        //int retval = -1;
        int flag = 0;
        try {
            if (filename == null) {
                saveAs();
            } else {
                fout = new PrintWriter(new FileWriter(jFileChooser.getSelectedFile()));
                String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                //fout.println(s);
                JOptionPane.showMessageDialog(rootPane, "File Saved");
                fileContent = mainarea.getText();
                flag = 1;
            }
        } catch (IOException e) {
        } finally {
            if (flag == 1) {
                fout.close();
            }
        }
    }

    private void saveAs() {
        //System.out.print("Hello");
        PrintWriter fout = null;
        int retval = -1;
        int flag = 0;
        try {
            retval = jFileChooser.showSaveDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                fout = new PrintWriter(new FileWriter(jFileChooser.getSelectedFile()));
                String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                JOptionPane.showMessageDialog(rootPane, "File Saved");
                fileContent = mainarea.getText();
                filename = jFileChooser.getSelectedFile().getName();
                setTitle(filename = jFileChooser.getSelectedFile().getName());
            }
            flag = 1;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null && flag == 1) {
                fout.close();
            }
        }
    }

    private void open() {
        try {
            int retval = jFileChooser.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                mainarea.setText(null);
                Reader in = new FileReader(jFileChooser.getSelectedFile());
                char[] buff = new char[100000000];
                int nch;
                while ((nch = in.read(buff, 0, buff.length)) != -1) {
                    mainarea.append(new String(buff, 0, nch));
                }
            }
            filename = jFileChooser.getSelectedFile().getName();
            setTitle(filename = jFileChooser.getSelectedFile().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void open_new() {
        if (!mainarea.getText().equals("") && !mainarea.getText().equals(fileContent)) {
            if (filename == null) {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the changes?");
                if (option == 0) {
                    saveAs();
                    clear();
                } else if (option == 2) {

                } else {
                    clear();
                }
            } else {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the changes?");
                if (option == 0) {
                    save();
                    clear();
                } else if (option == 2) {

                } else {
                    clear();
                }
            }
        }
    }

    private void clear() {
        mainarea.setText(null);
        setTitle("Untitled Notepad");
        filename = null;
        fileContent = null;
    }

    private void setIconImage(ImageIcon titleIcon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Undo
    class UndoAction extends AbstractAction {

        public UndoAction(ImageIcon undoIcon) {
            super("Undo", undoIcon);
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }

        protected void update() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    //Redo
    class RedoAction extends AbstractAction {

        public RedoAction(ImageIcon redoIcon) {
            super("Redo", redoIcon);
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                ex.printStackTrace();
            }
            update();
            undoAction.update();

        }

        protected void update() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo");
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }

    private void aboutAction() {
        try {
            final ImageIcon imageIcon = new ImageIcon(new URL("https://lh6.ggpht.com/ZCcXSdtdJOWD7MRzsN_-5cV7-6ASnDlkMo2sIrytbaxwCiqg10xeJwyvo0oa0N8haw=w300"));
            JOptionPane.showMessageDialog(null, "<html><body><p style = 'width:200px;'>" + aboutString + "\n" + authors + "\n" + author1 + "\n" + author2 + "\n", "TextPad Project", 0, imageIcon);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void wordWrapAction() {
        if (wordWrap.isSelected()) {
            mainarea.setLineWrap(true);
            mainarea.setWrapStyleWord(false);
        } else {
            mainarea.setLineWrap(false);
            mainarea.setWrapStyleWord(false);
        }
    }

    private void referencesAction() {
        String urlString = "https://www.javatpoint.com/java-swing";
        try {
            openWebpage(new URL(urlString).toURI());
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        TextPad tp = new TextPad();
    }
}
