package filemanager;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

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