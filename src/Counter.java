import java.io.*;
import java.util.*;

/*
*Spec.Inc.
*2018.10.19
*QZFLS 1601 72/54
*/

//Counter Core for GUI

public class Counter
{
	private FileReader reader;
	private BufferedReader breader;
	private int totalLine = 0;
	private int totalFile = 0;
	private String type = "java";
	private String fileName;
	private int nullLine = 0;
	private int markLine = 0;
	private String line;
	private File[] files;
	

	
	private boolean isThatFile(String name){
		if(name.indexOf(".")>0){
			if(
			name.substring(name.indexOf(".")+1,name.length()).equals(type)
			){
				return true;
			}
			else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public void printInfo(){
		System.out.println("一共检索了：" + totalFile + "个文件");
		System.out.println("有"+nullLine+"条空行");
		System.out.println("有"+markLine+"条注释行");
	}
	
	public ArrayList getInfo(){
		
		HashMap map = new HashMap();
		ArrayList total = new ArrayList();
		ArrayList<String> paths = new ArrayList<>();
		
		map.put("Total Files:",totalFile);
		for(File f:this.files){
			paths.add(f.getAbsolutePath());
		}
		
		map.put("Null Lines:",nullLine);
		map.put("Marked Lines:",markLine);
		
		total.clear();
		total.add(map);
		total.add(paths);
		return total;
	}
	
	public int[] getFullInfo(){
		int[] info = {totalLine,nullLine,markLine};
		return info;
	}
	
	private boolean isMarkedLine(String line){
		//将缩进排除！
		String nline = line.replace("\t","");
		if(nline.startsWith("//")||nline.startsWith("/*")||nline.startsWith("*")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isNullLine(String line){
		String nline = line.replace("\t","");
		if(nline.length()<=0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int ReadLine(File[] f)
	{
		this.files = f;
		for (File file:f)
		{
			if (file.isFile())
			{
				if (isThatFile(file.getName()))
				{
					totalFile++;
					try
					{
						reader = new FileReader(file);
						breader = new BufferedReader(reader);

						try
						{

							while ((line = breader.readLine()) != null)
							{
								totalLine++;
								
								if(isNullLine(line)){
									nullLine++;
								}
								if(isMarkedLine(line)){
									markLine++;
								}
							}

						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				ReadLine(file.listFiles());
			}
		}
		return totalLine;
	}
}

