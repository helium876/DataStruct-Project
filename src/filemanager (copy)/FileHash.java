package filemanager;

//import java.io.File;

public class FileHash {
	private String file;
	private String hash;
	
	public FileHash(){
		file = null;
		hash = "";
	}
	
	public FileHash(String f, String h){
		file=f;
		hash=h;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String f) {
		file = f;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public void print(){
		System.out.println("File: \t"+ file + "Hash \t" + hash);
	}
}