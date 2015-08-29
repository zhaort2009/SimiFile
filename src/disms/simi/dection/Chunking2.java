package disms.simi.dection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFileChooser;

public class Chunking2 {
	 static Vector<Long> pos = new Vector<Long>();
     static Vector<Integer> len = new Vector<Integer>(); 
     static Vector<Long> pos2 = new Vector<Long>();
     static Vector<Integer> len2 = new Vector<Integer>();
     static Vector<Long> pos3 = new Vector<Long>();
     static Vector<Integer> len3 = new Vector<Integer>();
     static Vector<Integer> filevec = new Vector<Integer>();
     static long avesize = 0;
     static HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
     static HashMap<String, Integer> hashmap2 = new HashMap<String, Integer>();
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
	public static void printHexString( byte[] b) {  
		   for (int i = 0; i < b.length; i++) { 
		     String hex = Integer.toHexString(b[i] & 0xFF); 
		     if (hex.length() == 1) { 
		       hex = '0' + hex; 
		     } 
		     System.out.print(hex.toUpperCase() ); 
		   } 

		}
	public  String bytesToHexString1(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	   if (src == null || src.length <= 0) {   
	       return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	       String hv = Integer.toHexString(v);   
	       if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	       }   
	       stringBuilder.append(hv);   
	    }   
	   return stringBuilder.toString();   
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
    		for (j = 0; j < 1024*1024-2; j++) { 
	    		if((i+j<=size)&&buffer[j]==74&&buffer[j+1]==78&&(buffer[j+2])/16==4){
//	    			if((buffer[j+2]/16)==3){
	    				len.addElement((int)(i+j-temp));	    			
		    	        pos.addElement(temp);
		    	        temp = i+j;
//	    			}
//	    			else
//	    				j++;
		    			
	    			
	    		}	    		
    		}
        }
        len.addElement((int)(size-temp));	    			
        pos.addElement(temp);
//        System.out.println((size-temp)+"\t"+temp);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        
        avesize = size/len.size();
    }
    public static void printVec(){
//    	 System.out.println("块大小");
//    	 for(int c = 0;c<len.size();c++){
//         	System.out.println(c+"\t"+len.elementAt(c)+"\t"+pos.elementAt(c));          	        	
//         }
//    	 System.out.println("平均块大小");
    	 System.out.println(avesize);
     	long pow = 0;
     	for(int e = 0;e<len.size();e++)
     		pow += (int)Math.pow((avesize-len.elementAt(e)), 2);
     	pow=pow/(len.size());
//     	System.out.println("块标准差");
//     	System.out.println((int)(Math.sqrt(pow))); 
    }
    public static  String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	   if (src == null || src.length <= 0) {   
	       return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	       String hv = Integer.toHexString(v);   
	       if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	       }   
	       stringBuilder.append(hv);   
	    }   
	   return stringBuilder.toString();   
	}   
    public static void computeHASH(String file) throws IOException, NoSuchAlgorithmException{
 
        FileInputStream fis = new FileInputStream(file);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        long num = 0;
        int count =0;
        byte[] HashValue = new byte[16];
        for(int c = 0;c<len.size();c++){
        	num=len.elementAt(c);
        	byte[] b = new byte[(int) num];
        	fis.read(b);
        	HashValue=messageDigest.digest(b);
        	hashmap.put(bytesToHexString(HashValue),count);
        	System.out.println(count+"\t"+pos.elementAt(count)+"\t"+len.elementAt(count)+"\t"+bytesToHexString(HashValue));
        	count++;
        }
        
    }
    public static void storeDiff(String file) throws IOException, NoSuchAlgorithmException{
    	
    	File f = new File(file);
        long size = f.length();       
        FileInputStream fis = new FileInputStream(file);        		 
    	byte[] buffer = new byte[1024*1024];
    	long temp = 0;
    	long i = 0;
    	int j = 0;
//    	System.out.println(size);
//    	System.out.println(System.currentTimeMillis());
    	for(i = 0;i<size;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-2; j++) { 
	    		if((i+j<=size)&&buffer[j]==74&&buffer[j+1]==78&&(buffer[j+2])/16==4){
		    			len2.addElement((int)(i+j-temp));	    			
		    	        pos2.addElement(temp);
		    	        System.out.println((i+j-temp)+"\t"+temp);
		    	        temp = i+j;
	    		}
    		
    		}
        }
        len2.addElement((int)(size-temp));	    			
        pos2.addElement(temp);
//        System.out.println((size-temp)+"\t"+temp);
        FileInputStream fis2 = new FileInputStream(file);
        MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
        int num = 0;
        int count =0;
        byte[] HashValue = new byte[16];
        FileOutputStream fos=new FileOutputStream("H:\\DATATEST\\data.dat",true);
        long pos = 0;
        int len= 0;
        for(int c = 0;c<len2.size();c++){
        	num=Integer.valueOf(len2.elementAt(c));
        	byte[] b = new byte[num];
        	fis2.read(b);
        	HashValue=messageDigest2.digest(b);
        	String s = bytesToHexString(HashValue);
        
//        	System.out.println("storeDiff"+s);
        	if(!hashmap.containsKey(s))
        	{
        		int k = count+10000000;
//        		System.out.println(k+"\t"+pos2.elementAt(c)+"\t"+len2.elementAt(c)+"\t"+s);
        		hashmap2.put(s,count);
        		filevec.addElement(count+10000000);
        		len = len2.elementAt(c);
        		len3.addElement(len);
        		pos3.addElement(pos);
        		pos+=len;
        		count++;
        		fos.write(b);
        		fos.flush();
        	}
        	else{
        		filevec.addElement(hashmap.get(s));
//        		System.out.println(hashmap.get(s)+"\t"+pos2.elementAt(c)+"\t"+len2.elementAt(c)+"\t"+s);
        	}
        	System.out.println(c+"\t"+pos2.elementAt(c)+"\t"+len2.elementAt(c)+"\t"+s);	
        }
        fos.close();
    }
    public static void restoreFile(String resFile,String oriFile,String diffFile) throws IOException, NoSuchAlgorithmException{
    	FileOutputStream fos=new FileOutputStream(resFile,true);
    	FileInputStream oriFis = new FileInputStream(oriFile);
//    	MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
    	long curPos = 0;
    	FileInputStream diffFis = new FileInputStream(diffFile);
    	long curPos2 = 0;
    	int chkNO = 0;
    	int curNO = 0;
    	long ps2 = 0;
    	long ps =0;
    	int length=0;
        for(int c = 0;c<filevec.size();){
        	chkNO = filevec.elementAt(c);
        	curNO=chkNO-10000000;
        	if(chkNO>=10000000){
        		ps2 = pos3.elementAt(curNO);
        		if(curPos2!=ps2){
        			diffFis.skip(ps2-curPos2);
        			curPos2=ps2;        			
        		}
//        		length+=len3.elementAt(c);
        		while(c<filevec.size()){
        			if(filevec.elementAt(c)>=10000000&&length<=50000000){
        					length+=len3.elementAt(filevec.elementAt(c)-10000000);
        					c++;   
        			}
        			else{
//        				c++;
        				break;
        			}
        				
        		     			
        		}
        		
        			
        	
        		byte[] b = new byte[length];
        		diffFis.read(b);
        		curPos2=curPos2+length;        		
        		fos.write(b);
        		length=0;
        		fos.flush();
//        		System.out.println(chkNO+"\t"+pos3.elementAt(curNO)+"\t"+len3.elementAt(curNO));
//        		System.out.println(chkNO+"\t"+pos3.elementAt(curNO)+"\t"+len3.elementAt(curNO)+bytesToHexString(messageDigest2.digest(b)));
        	}
        	else{
        		ps = pos.elementAt(chkNO);
        		if(curPos!=ps){
        			oriFis.skip(ps-curPos);
        			curPos=ps;        			
        		}
       		
        		while(c<filevec.size()){
        			if(filevec.elementAt(c)<10000000&&length<=50000000){
        				length+=len.elementAt(filevec.elementAt(c));
        				c++;
        			}
        			else{
//        				c++;
        				break;
        			}
        		}
        		byte[] b = new byte[length];
        		oriFis.read(b);
        		curPos+=length;
        		fos.write(b);
        		length=0;
        		fos.flush();
//        		System.out.println(chkNO+"\t"+pos.elementAt(chkNO)+"\t"+len.elementAt(chkNO));
//        		System.out.println(chkNO+"\t"+pos.elementAt(chkNO)+"\t"+len.elementAt(chkNO)+"\t"+bytesToHexString(messageDigest2.digest(b)));
        	}
//        	if(c%50==0)
//        		fos.flush();
        }
//        fos.flush();
          fos.close();
      }
    

//    	System.out.println(System.currentTimeMillis());
//        len.addElement(String.valueOf((size-temp)));
//        int ss[] = new int[len.size()];
//        System.out.println(len.size());
//    	System.out.println("块大小");
//        for(int c = 0;c<len.size();c++){
//        	System.out.println(len.elementAt(c));
//        	ss[c]=Integer.valueOf(len.elementAt(c));
//        	System.out.println(ss[c]);
//        }
//        for(int d = 0;d<pos.size();d++){
//        	System.out.println(pos.elementAt(d));        	
//        }
//    	long avesize = size/len.size();
//    	System.out.println("平均块大小");
//    	System.out.println(avesize);
//    	long pow = 0;
//    	for(int e = 0;e<ss.length;e++)
//    		pow += (int)Math.pow((avesize-ss[e]), 2);
//    	System.out.println(pow);
//    	pow=pow/(ss.length-1);
//    	System.out.println("块标准差");
//    	System.out.println((int)(Math.sqrt(pow))); 
//    		     if(j%16==0){
//     				System.out.println();
//     				System.out.print(Long.toHexString(j/16)+":");
//     			 }
//    		     System.out.print(hex.toUpperCase()+' '); 
//    		String hex = Integer.toHexString(buffer[0] & 0xFF); 
//    		if (hex.length() == 1) { 
//    			hex = '0' + hex; 
//    		}
//    		if(hex.equalsIgnoreCase("AC")){     			
//        		fis.read(buffer);
//        		hex = Integer.toHexString(buffer[0] & 0xFF);
//        		if(hex.equalsIgnoreCase("ED")){
//                	System.out.print(i);	
//        		pos.addElement(String.valueOf(i));
//        		}
//        		i++; 
//    }
//        for(int j = 0;j<pos.size();j++){
//        	if(j%20==0)
//            	System.out.println();
//        	System.out.print(pos.elementAt(j));
//        	
//        }
//        
//    			if(i%16==0){
//    				System.out.println();
//    				System.out.print(Integer.toHexString(i/16)+":");
//    			}
//    			System.out.print(hex.toUpperCase()+' '); .


  
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//      
//    	byte i = -128;
//    	String hex = Byte.toHexString(i);
//    	System.out.println(hex);
//    	long startTime = System.currentTimeMillis();
//    	chooseFile(fs);
//    	for(int i=0;i<fs.size();i++){
//    		readfile(fs.elementAt(i).toString());
//        	printVec();
//        	len.clear();
//        	pos.clear();
//    	}
//    	long endTime = System.currentTimeMillis();
    	readfile("H:\\DATATEST\\(7).rar");
    	computeHASH("H:\\DATATEST\\(7).rar");
    	storeDiff("H:\\DATATEST\\(1).rar");
    	long startTime = System.currentTimeMillis();
    	restoreFile("H:\\DATATEST\\(11).rar","H:\\DATATEST\\(7).rar","H:\\DATATEST\\data.dat");
    	long endTime = System.currentTimeMillis();
    	System.out.println(endTime-startTime);
//    	readfile("H:\\1.mp3");
//    	computeHASH("H:\\1.mp3");
//    	storeDiff("H:\\2.mp3");
//    	long startTime = System.currentTimeMillis();
//    	restoreFile("H:\\DATATEST\\(11).rar","H:\\1.mp3","H:\\DATATEST\\data.dat");
//    	long endTime = System.currentTimeMillis();
//    	System.out.println(endTime-startTime);
//    	computeHASH("H:\\DATATEST\\(7).rar");
//    	storeDiff("H:\\DATATEST\\(1).rar");
//    	storeDiff("H:\\DATATEST\\(2).rar");
//    	storeDiff("H:\\DATATEST\\(3).rar");
//    	storeDiff("H:\\DATATEST\\(4).rar");
//    	storeDiff("H:\\DATATEST\\(5).rar");
//    	storeDiff("H:\\DATATEST\\(6).rar");
//    	storeDiff("H:\\DATATEST\\(8).rar");
//    	storeDiff("H:\\DATATEST\\(9).rar");
//    	storeDiff("H:\\DATATEST\\(10).rar");
    	
    }
}
/*
 long HalfFileSize = file.length()/(int)(Math.random()*100);
		     FileInputStream fis = new FileInputStream(file);
		     byte[] buffer = new byte[2];
		     fis.skip(HalfFileSize);
		     fis.read(buffer);
		     byte[] HashValue = messageDigest.digest(buffer);
		     return HashValue;
 */
