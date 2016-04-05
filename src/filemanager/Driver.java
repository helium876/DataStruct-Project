package filemanager;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;



import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Driver extends JFrame {

  public Driver(JScrollPane cp, ArrayList<String> arr) throws NoSuchAlgorithmException, IOException {
    super("Duplicate Finder");
    String[] strs = arr.toArray(new String[0]);
    String[] av = {};
    final JList list = new JList(createData(strs));

    list.setCellRenderer(new CheckListRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setBorder(new EmptyBorder(0, 4, 0, 0));
    list.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int index = list.locationToIndex(e.getPoint());
        CheckableItem item = (CheckableItem) list.getModel()
            .getElementAt(index);
        item.setSelected(!item.isSelected());
        Rectangle rect = list.getCellBounds(index, index);
        list.repaint(rect);
      }
    });
    JScrollPane sp = new JScrollPane(list);

    final JTextArea textArea = new JTextArea(3, 10);
    JScrollPane textPanel = new JScrollPane(textArea);
    JButton printButton = new JButton("Select Folder"); //Get Files
    printButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        /*ListModel model = list.getModel();
        int n = model.getSize();
        for (int i = 0; i < n; i++) {
          CheckableItem item = (CheckableItem) model.getElementAt(i);
          if (item.isSelected()) {
            textArea.append(item.toString());
            textArea.append(System.getProperty("line.separator"));
          }
        }*/

      }
    });
    JButton clearButton = new JButton("Delete Duplicates");
    clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textArea.setText("");
      }
    });
    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(printButton);
    panel.add(clearButton);

    getContentPane().add(cp, BorderLayout.WEST);
    getContentPane().add(panel, BorderLayout.EAST);
    getContentPane().add(sp, BorderLayout.CENTER);
    getContentPane().add(textPanel, BorderLayout.SOUTH);
  }

  private CheckableItem[] createData(String[] strs) {
    int n = strs.length;
    CheckableItem[] items = new CheckableItem[n];
    for (int i = 0; i < n; i++) {
      items[i] = new CheckableItem(strs[i]);
    }
    return items;
  }

  class CheckableItem {
    private String str;

    private boolean isSelected;

    public CheckableItem(String str) {
      this.str = str;
      isSelected = false;
    }

    public void setSelected(boolean b) {
      isSelected = b;
    }

    public boolean isSelected() {
      return isSelected;
    }

    public String toString() {
      return str;
    }
  }

  class CheckListRenderer extends JCheckBox implements ListCellRenderer {

    public CheckListRenderer() {
      setBackground(UIManager.getColor("List.textBackground"));
      setForeground(UIManager.getColor("List.textForeground"));
    }

    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean hasFocus) {
      setEnabled(list.isEnabled());
      setSelected(((CheckableItem) value).isSelected());
      setFont(list.getFont());
      setText(value.toString());
      return this;
    }
  }

  public static void main(String[]av) throws NoSuchAlgorithmException, IOException {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}
  
    
    //JScrollPane cp = new JScrollPane();

	//if (av.length == 0) {
	  //cp.add(new BuildTree(new File(".")));
    BuildTree tree = new BuildTree(new File("."));
    JScrollPane cp = new JScrollPane(tree);
	//} else {
	  //cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
	  //for (int i = 0; i < av.length; i++)
	    //cp.add(new BuildTree(new File(av[i])));

	//}
	Driver frame = new Driver(cp, tree.DupFiles);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    frame.pack();
    frame.setSize(1000, 600);
    frame.setVisible(true);
  }
}
/*
public class Driver {
	public static void main (String[]av) throws NoSuchAlgorithmException, IOException{
		   JFrame frame = new JFrame("Duplicate File Finder");
		    frame.setForeground(Color.black);
		    frame.setBackground(Color.lightGray);
		    Container cp = frame.getContentPane();

		    if (av.length == 0) {
		      cp.add(new BuildTree(new File(".")));
		    } else {
		      cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		      for (int i = 0; i < av.length; i++)
		        cp.add(new BuildTree(new File(av[i])));
		    }
		    

		    frame.pack();
		    frame.setVisible(true);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  }
}
*/