package disms.simi.dection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.planetj.math.rabinhash.*;

public class CDC{
	static Vector<Long> pos = new Vector<Long>();
    static Vector<Integer> len = new Vector<Integer>(); 
    static long avesize = 0;
    static Vector fs = new Vector();
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
// 		System.out.println(fs);
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
	public static void calCDC(String file) throws IOException{
		RabinHashFunction32 rhf = new RabinHashFunction32(36524);
		File f = new File(file);
		System.out.println(f.getName());
        long size = f.length();
        FileInputStream fis = new FileInputStream(file);        		 
    	byte[] buffer = new byte[1024*1024];
    	byte[] cal = new byte[16];
    	long temp = 0;
    	long i = 0;
    	int j = 0;
    	int tail;    	   
//        System.out.println(f.getName());  
//    	System.out.println(size);
    	long startTime=System.currentTimeMillis();
        for(i = 0;i<size;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-15; j++) {  
    			cal = Arrays.copyOfRange(buffer, j, j+15);

    			tail = rhf.hash(cal);
	    		if((i+j<=size)&&(tail%64779==0)&&(tail!=0)){
		    			len.addElement((int)(i+j-temp));	    			
		    	        pos.addElement(temp);
		    	       
//		    	        System.out.println((i+j-temp)+"\t"+temp); 
		    	        temp = i+j;
	    		}	    		
    		}
        }
        len.addElement((int)(size-temp));	    			
        pos.addElement(temp);
//        System.out.println((size-temp)+"\t"+temp);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        avesize = size/len.size();
	}
	   public static void printVec(){
//	    	 System.out.println("块大小");
//	    	 for(int c = 0;c<len.size();c++){
//	         	System.out.println(c+"\t"+len.elementAt(c)+"\t"+pos.elementAt(c));          	        	
//	         }
//	    	 System.out.println("平均块大小");
	    	 System.out.println(avesize);
	     	long pow = 0;
	     	for(int e = 0;e<len.size();e++)
	     		pow += (int)Math.pow((avesize-len.elementAt(e)), 2);
	     	pow=pow/(len.size());
//	     	System.out.println("块标准差");
	     	System.out.println((int)(Math.sqrt(pow))); 
	    }
	public static void main(String[] args) throws IOException{
		chooseFile(fs);
    	for(int i=0;i<fs.size();i++){
    		calCDC(fs.elementAt(i).toString());
        	printVec();
        	len.clear();
        	pos.clear();
    	}
		
	}
}