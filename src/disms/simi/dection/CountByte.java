package disms.simi.dection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFileChooser;

public class CountByte{
	 static Vector<Long> pos = new Vector<Long>();
     static Vector<Integer> len = new Vector<Integer>(); 
     static long avesize = 0;
     static HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
     static HashMap<String, Integer> hashmap2 = new HashMap<String, Integer>();
     static Vector fs = new Vector();
     static int[] countByte = new int[256];
	public static void chooseFile(Vector fs){
 		JFileChooser jfc = new JFileChooser("E:\\linux源码");
 		jfc.setMultiSelectionEnabled(true);
 		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 		jfc.showOpenDialog(new javax.swing.JFrame());
 		File[] sf = jfc.getSelectedFiles();
 		for(int i = 0; i < sf.length;i++ ){
 			if(!sf[i].isDirectory())
 				fs.addElement(sf[i]);
 			else
 				fileList(sf[i],fs);
 		}
 		System.out.println(fs);
 	}
 	public static void fileList(File file,Vector vt) {
         File[] files = file.listFiles();
         if (files != null) {
               for (File f : files) {
             	  if(!f.isDirectory())
//                     System.out.println(f.getPath());
             	  	vt.addElement(f.getPath());
                     fileList(f,vt);
               }
         }
    }
 	public static void printByte(){
		for(int i = 0;i<256;i++)
			System.out.println(countByte[i]);
	}
 	public static void readfile(String file) throws IOException{
    	File f = new File(file);
        long size = f.length();   
        System.out.println(f.getName());  
        System.out.println(f.length());
        FileInputStream fis = new FileInputStream(file);        		 
    	byte[] buffer = new byte[1024*1024];
    	long temp = 0;
    	long i = 0;
    	int j = 0;
    	long startTime = System.currentTimeMillis();
        for(i = 0;i<size;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024; j++) {
    			if((i+j<=size))
    			{
    				countByte[(buffer[j]&0xFF)]++;
    			}
	    		
	    }	    		


        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

        }
 	}
 	 public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
 		chooseFile(fs);
    	for(int i=0;i<fs.size();i++){
    		readfile(fs.elementAt(i).toString());
    	}
    	printByte();
 	 }
}