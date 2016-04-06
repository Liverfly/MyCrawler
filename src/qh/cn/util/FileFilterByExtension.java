package qh.cn.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFilterByExtension {

	private String type;
	
	public FileFilterByExtension(String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	public File[] getFiles(String srcDir) {
		// TODO Auto-generated method stub
		File root = new File(srcDir);
		File[] fs = root.listFiles();
		List<File> list = new ArrayList<File>();
		for(int i =0 ;i < fs.length; i++)
		{
			if(fs[i].getName().endsWith(type))
			{
				list.add(fs[i]);
			}
		}
		final int size =  list.size();
		
		return (File[])list.toArray(new File[size]);
	}
	
	final static void showAllFiles(File dir) throws Exception{
		File[] fs = dir.listFiles();
		for(int i=0; i<fs.length; i++){
			System.out.println(fs[i].getAbsolutePath());
			if(fs[i].isDirectory()){
				try{
					showAllFiles(fs[i]);
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
}
