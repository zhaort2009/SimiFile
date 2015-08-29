package disms.simi.dection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;

public class SimiFile{
	Vector fs = new Vector();
	public void chooseFile(Vector fs){
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
	public void fileList(File file,Vector vt) {
        File[] files = file.listFiles();
        if (files != null) {
              for (File f : files) {
            	  if(!f.isDirectory())
//                    System.out.println(f.getPath());
            	  	vt.addElement(f.getPath());
                    fileList(f,vt);
              }
        }
   }
	 public static void readfile(String file) throws IOException{
	    	File f = new File(file);
	        long size = f.length();
	        Vector<String> pos = new Vector<String>();
	        Vector<String> len = new Vector<String>();
	    	byte[] buffer = new byte[1024*1024];
	    	long temp = 0;
	    	long i = 0;
	    	int j = 0;
	    	System.out.println(size);
	    	System.out.println(System.currentTimeMillis());
	    	FileInputStream fis = new FileInputStream(file);
	        for(i = 0;i<size;i=i+1024*1024){
	    		fis.read(buffer);
	    		for (j = 0; j < 1024*1024-1; j++) { 
		    		if((i+j<=size)&&(buffer[j]==-34)&&(buffer[j+1]==67)){
			    			len.addElement(String.valueOf((i+j-temp)));	    			
			    	        pos.addElement(String.valueOf(i+j));
			    	        temp = i+j;
		    		}	    		
	    		}
	        }	   
	        len.addElement(String.valueOf((size-temp)));
	        int ss[] = new int[len.size()];
	        System.out.println(len.size());
	    	System.out.println("块大小");
	        for(int c = 0;c<len.size();c++){
	        	ss[c]=Integer.valueOf(len.elementAt(c));
	        	System.out.println(ss[c]);
	        }
	    	long avesize = size/len.size();
	    	System.out.println("平均块大小");
	    	System.out.println(avesize);
	    	long pow = 0;
	    	for(int e = 0;e<ss.length;e++)
	    		pow += (int)Math.pow((avesize-ss[e]), 2);
	    	pow=pow/(ss.length-1);
	    	System.out.println("块标准差");
	    	System.out.println((int)(Math.sqrt(pow)));
      		    
   		}

}
