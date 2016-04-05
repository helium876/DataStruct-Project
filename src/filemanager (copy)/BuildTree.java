package filemanager;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
public class BuildTree extends JPanel {

	public BuildTree(File dir) throws NoSuchAlgorithmException, IOException {

		this.setLayout(new BorderLayout());
		File folder = new File("dir");
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			folder = fc.getSelectedFile();
		}
		// Make a tree list with all the nodes, and make it a JTree
		JTree tree = new JTree(addNodes(new DefaultMutableTreeNode("."), folder));

		// Add a listener so you can click
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				System.out.println("You selected " + node);
			}
		});

		// Add stuff to the JPanel

		JScrollPane scrollpane = new JScrollPane();
		scrollpane.getViewport().add(tree);
		this.add(BorderLayout.CENTER, scrollpane);

	}

	// adding nodes
	public DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir)
			throws NoSuchAlgorithmException, IOException {
		String curPath = dir.getAbsolutePath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
		if (curTop != null) { // should only be null at root
			curTop.add(curDir);
		}
		Vector<String> ol = new Vector<String>();
		String[] tmp = dir.list();
		File f;
		Boolean t;
		File check = null;
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		Vector<String> files = new Vector();
		File[] fileList = dir.listFiles();
		for(File currFile : fileList){
				//File useThis = new File(ol.elementAt(i));
				if (currFile.isDirectory())
					addNodes(curDir,currFile);
				else{

					//Use MD5 algorithm
					//check = new File(thisObject);
					String checksum = getFileMD5(md5Digest, currFile.getAbsolutePath());
					System.out.println(checksum);
					FileHash fill = new FileHash(currFile.getAbsolutePath(), checksum);
					fill.print();
					files.addElement(fill.getFile());

				}				
		}
		//print(curTop);
		findDups(fileList);

		for (int fnum = 0; fnum < files.size(); fnum++)
			curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
		return curDir;
	}
	public static String getFileMD5(MessageDigest digest, String file) throws IOException {
		// Get file input stream for reading the file content
		File actualFile = new File(file);
		if(actualFile.isFile()){
			FileInputStream fis = new FileInputStream(file);

			// Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;

			// Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
			;

			// close the stream; We don't need it now.
			fis.close();

			// Get the hash's bytes
			byte[] bytes = digest.digest();

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// return complete hash
			return sb.toString();
			
		}
		return "";
	}
	public static String[] findDups(File[] fileList) throws NoSuchAlgorithmException, IOException{
        //String[] dups = [];
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        int dubs = 0;
        String[] dupFiles = null;
        for (int i = 0; i < fileList.length; i++) {
        	if (fileList[i].isDirectory()) {
        		continue;
        	}
            String curFile = fileList[i].getAbsolutePath().toString();
            String checksum1 = getFileMD5(md5Digest,curFile);
            //System.out.println(i + "000 :FILE: "  + curFile + " HASH: " + checksum1);
            for (int y = 0; y < fileList.length; y++) {
            	if (y == i) {
            		continue;
            	}
                String curFile2 = fileList[y].getAbsolutePath().toString();
                String checksum2 = getFileMD5(md5Digest,curFile2);
                //System.out.println(y + " :FILE: "  + curFile2 + " HASH: " + checksum2);
                System.out.println("HASH 1: " + checksum1 + " HASH 2: " + checksum2);
                if(checksum1.equals(checksum2)) {
                    dubs++;
                   	//dupFiles.add(curFile);
                   	//dupFiles.add(curFile2);
                }
            }
        }
        System.out.println("Copies: " + dubs);
        return dupFiles;
    }

	public static void print(DefaultMutableTreeNode node)
	{
	    String nodeName = node.toString();
	    int level= node.getLevel();
	    String placement = "";
	    while (level > 0)
	    {
	        placement += ">";
	        level--;
	    }
	    if(node.isLeaf())
	    {
	        System.out.println(placement + nodeName);
	        return;
	    }

	    System.out.println(placement + "--- " + nodeName + " ---");
	    for(int i = 0 ; i < node.getChildCount() ; i++)
	    {
	        print((DefaultMutableTreeNode)node.getChildAt(i));
	    }
	    System.out.println(placement + "+++ " + nodeName + " +++");
	}
	public Dimension getMinimumSize() {
		return new Dimension(400, 800);
	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 400);
	}

}