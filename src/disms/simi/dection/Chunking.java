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

public class Chunking {
	 static Vector<String> pos = new Vector<String>();
     static Vector<String> len = new Vector<String>(); 
     
     static HashMap<String, Integer> hashmap = new HashMap();
     static HashMap<String, Integer> hashmap2 = new HashMap();
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
        System.out.println(f.length());
        FileInputStream fis = new FileInputStream(file);        		 
    	byte[] buffer = new byte[1024*1024];
    	long temp = 0;
    	long i = 0;
    	int j = 0;
    	System.out.println(size);
    	System.out.println(System.currentTimeMillis());
        for(i = 0;i<size;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-2; j++) { 
	    		if((i+j<=size)&&buffer[j]==74&&buffer[j+1]==78&&(buffer[j+2]/16)==4){
		    			len.addElement(String.valueOf((i+j-temp)));	    			
		    	        pos.addElement(String.valueOf(temp));
		    	        System.out.println(String.valueOf((i+j-temp))+"\t"+String.valueOf(temp));
		    	        temp = i+j;
	    		}	    		
    		}
        }
        len.addElement(String.valueOf((size-temp)));	    			
        pos.addElement(String.valueOf(temp));
        System.out.println(String.valueOf((size-temp))+"\t"+String.valueOf(temp));
    
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
        int num = 0;
        int count =0;
        byte[] HashValue = new byte[16];
        for(int c = 0;c<len.size();c++){
        	num=Integer.valueOf(len.elementAt(c));
        	byte[] b = new byte[num];
        	fis.read(b);
        	HashValue=messageDigest.digest(b);
        	hashmap.put(bytesToHexString(HashValue),count);
        	count++;
        	System.out.println("computeHASH"+count);
        }
        
    }
    public static void chunking(String file){
    	
    }
    public static void storeDiff(String file) throws IOException, NoSuchAlgorithmException{
    	Vector<String> pos2 = new Vector<String>();
        Vector<String> len2 = new Vector<String>();
    	File f = new File(file);
        long size = f.length();       
        FileInputStream fis = new FileInputStream(file);        		 
    	byte[] buffer = new byte[1024*1024];
    	long temp = 0;
    	long i = 0;
    	int j = 0;
    	System.out.println(size);
    	System.out.println(System.currentTimeMillis());
    	for(i = 0;i<size;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-2; j++) { 
	    		if((i+j<=size)&&buffer[j]==74&&buffer[j+1]==78&&(buffer[j+2]/16)==4){
		    			len2.addElement(String.valueOf((i+j-temp)));	    			
		    	        pos2.addElement(String.valueOf(temp));
		    	        System.out.println(String.valueOf((i+j-temp))+"\t"+String.valueOf(temp));
		    	        temp = i+j;
	    		}	    		
    		}
        }
        len2.addElement(String.valueOf((size-temp)));	    			
        pos2.addElement(String.valueOf(temp));
        System.out.println(String.valueOf((size-temp))+"\t"+String.valueOf(temp));
        FileInputStream fis2 = new FileInputStream(file);
        MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
        int num = 0;
        int count =0;
        byte[] HashValue = new byte[16];
        FileOutputStream fos=new FileOutputStream("H:\\data3.dat",true);

        for(int c = 0;c<len2.size();c++){
        	num=Integer.valueOf(len2.elementAt(c));
        	byte[] b = new byte[num];
        	fis2.read(b);
        	HashValue=messageDigest2.digest(b);
        	String s = bytesToHexString(HashValue);
        	System.out.println("storeDiff"+s);
        	if(!hashmap.containsKey(s)&&!hashmap2.containsKey(s))
        	{
        		hashmap2.put(s,count);
        		count++;
        		fos.write(b);
        		fos.flush();
        	}
        }
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
    	readfile("H:\\DATATEST\\(1).rar");
    	computeHASH("H:\\DATATEST\\(1).rar");
    	storeDiff("H:\\DATATEST\\(11).rar");
//    	storeDiff("H:\\DATATEST\\(2).rar");
//    	storeDiff("H:\\DATATEST\\(3).rar");
//    	storeDiff("H:\\DATATEST\\(4).rar");
//    	storeDiff("H:\\DATATEST\\(5).rar");
//    	storeDiff("H:\\DATATEST\\(6).rar");
//    	storeDiff("H:\\DATATEST\\(8).rar");
//    	storeDiff("H:\\DATATEST\\(9).rar");
//    	storeDiff("H:\\DATATEST\\(10).rar");
 //   	System.out.println("done");
    	
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
