package disms.simi.dection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;

public class SimiHash{
	private String filePath = new String();
	private File file = null;
	private byte byteFlag1 = 26;
	private byte byteFlag2 = 63;
	private long count1 = 0;
	private long count2 = 0;
	private byte byteFlag[] = new byte[10];
	private long count[] = new long[10];
	private long fileSize= 0;        		 
	private byte[] buffer = new byte[1024*1024];
	private static Vector<String> simiHash = new Vector<String>();
	private static Vector fs = new Vector();
	private static int[] cb = new int[256];
	private static int[][] com = new int[10][11];
	private static int[][] com2 = new int[10][10];
	public void compared(){
		for(int f = 0;f<10;f++)
			for(int t = f+1;t<10;t++)
			for(int k = 0;k<10;k++){
				com2[f][t]=1;
				if((com[f][k]-com[t][k])!=0)
				if((com[f][k]/(com[f][k]-com[t][k]))<=10&&(com[f][k]/(com[f][k]-com[t][k]))>=-10)
					{
						com2[f][t]=0;
						break;
					}
			}
		for(int f = 0;f<10;f++){
			for(int t = 0;t<10;t++){
				System.out.print(com2[f][t]+"\t");
			}
			System.out.println();
		}
			
	}
	public void resetCountArray(){
		for (int i = 0;i<count.length;i++)
		{
			count[i]=0;
		}
	}
	public void setFlagArray()
	{
		byteFlag[0] = 78;
		byteFlag[1] = 63;
		byteFlag[2] = 24;
		byteFlag[3] = 85;
		byteFlag[4] = -44;
		byteFlag[5] = -69;
		byteFlag[6] = -51;
		byteFlag[7] = -66;
		byteFlag[8] = -85;
		byteFlag[9] = 74;
	}
	public void chooseFile(Vector fs){
		JFileChooser jfc = new JFileChooser("E:\\test\\test");
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
            	  	vt.addElement(f.getPath());
                    fileList(f,vt);
              }
        }
   }
	public void printByte(){
		for(int i = 0;i<256;i++)
			System.out.println(cb[i]);
	}
	public void countTable() throws IOException{
		for(int i = 0;i<fs.size();i++)
		{
			countZero( fs.elementAt(i).toString());			
		}
		printByte();
	}
	public void countZero(String path) throws IOException
	{
		filePath = path;
		file = new File(filePath);
		fileSize = file.length();
        FileInputStream fis = new FileInputStream(file);
        int i = 0;
        int j = 0;
        int k = 0;
        int f = 0;
        int inc = 1;
        int flag = 1;
       // for(f = -128;f<128;f++){
        for(i = 0;i<fileSize;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-1; j++) 
    		{
    			if(i+j<=fileSize)
    			{ 
    				cb[(buffer[j]&0xFF)]++;
    				/*for(k = 0;k<256;k++)
    				{
    					if((buffer[j]&0xFF)==k)
    					{
    						k++;
    						break;
    					}
    				}
    				{
    					flag = 1;
    					for(inc = 1;inc<=128;inc++)
    						if(buffer[j+inc]!=0)
    							flag = 0;    							
    				}
    				j = j+128;
    				if(flag==1)*/
    					
    			}
    			else
    				break;
    		}
    	}
	}
	
	public void computeSimiHash(String path) throws IOException
	{
		filePath = path;
		file = new File(filePath);
		fileSize = file.length();
        FileInputStream fis = new FileInputStream(file);
        int i = 0;
        int j = 0;
        int k = 0;
        for(i = 0;i<fileSize;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-1; j++) 
    		{
    			if(i+j<=fileSize)
    			{ 
    				for(k = 0;k < 10;k++){
    					if(buffer[j]==byteFlag[k]){
    						count[k]++;
    					}
    				}
    			}
    			else
    				break;
    		}
    	}
        for(k = 0;k < 10;k++){
			System.out.println(count[k]);
		}
        
     
        
	}
	
	public void computeSimiHash2(String path) throws IOException
	{
		filePath = path;
		file = new File(filePath);
		fileSize = file.length();
        FileInputStream fis = new FileInputStream(file);
        int i = 0;
        int j = 0;
        int k = 0;
        for(i = 0;i<fileSize;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-2; j++) 
    		{
    			if(i+j<=fileSize)
    			{ 
    				for(k = 0;k < 10;k++){
    					if(buffer[j]==byteFlag[k%10]&&(buffer[j+1]/16)==(byteFlag[(k+1)%10]/16)){
    						count[k]++;
    					}
    				}
    			}
    			else
    				break;
    		}
    	}
        for(k = 0;k < 10;k++){
			System.out.println(count[k]);
		}
       
	}
	public void computeSimiHash3(String path) throws IOException
	{
		filePath = path;
		file = new File(filePath);
		fileSize = file.length();
        FileInputStream fis = new FileInputStream(file);
        int i = 0;
        int j = 0;
        int k = 0;
        for(i = 0;i<fileSize;i=i+1024*1024){
    		fis.read(buffer);
    		for (j = 0; j < 1024*1024-3; j++) 
    		{
    			if(i+j<=fileSize)
    			{ 
    				for(k = 0;k < 10;k++){
    					if(buffer[j]==byteFlag[k%10]&&buffer[j+1]==byteFlag[(k+1)%10]&&(buffer[j+2]/64)==(byteFlag[(k+2)%10]/16)){
    						count[k]++;
    					}
    				}
    			}
    			else
    				break;
    		}
    	}
        for(k = 0;k < 10;k++){
			System.out.println(count[k]);
		}
      
	}
	public static void main(String[] args) throws IOException
	{
		SimiHash sf = new SimiHash();
		/*sf.resetCountArray();
		sf.setFlagArray();
		System.out.println(System.currentTimeMillis());
		sf.computeSimiHash3("E:/3.rar");
		System.out.println(System.currentTimeMillis());*/
		/*sf.countZero("F:\\1.doc");*/
		JFileChooser jfc = new JFileChooser("D:\\test1");
		jfc.setMultiSelectionEnabled(true);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showOpenDialog(new javax.swing.JFrame());
		File[] sf2 = jfc.getSelectedFiles();
		sf.setFlagArray();
		int k = 0;
		for(File f : sf2)
		{
			System.out.println(f.length());
//			sf.computeSimiHash3(f.getAbsolutePath());
//			for(int u = 0;u < 10; u++)
//			{
//				sf.com[k][u]=(int) sf.count[u];
//				sf.com[k][10]= sf.com[k][10]+(int) sf.count[u];
//			}
//			sf.resetCountArray();
//			k++;
			//sf.countZero(f.getAbsolutePath());
			
		}
		//sf.printByte();
//		for(int u = 0;u < 10; u++){
//			for(int t = 0;t < 10; t++)
//			{
//				System.out.print(sf.com[t][u]+" \t");
//			}
//			System.out.println();
//		}
//		sf.compared();		
	}
    
    
	
}