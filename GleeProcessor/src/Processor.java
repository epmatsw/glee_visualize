import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFileChooser;
public class Processor 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		try
		{
			start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	static JFileChooser browser = new JFileChooser("/Users/wxstamper/github/Iron/client/public/modes/ready_training");
	static ArrayList<String> pNames = new ArrayList<String>();
	static ArrayList<String> pPaths = new ArrayList<String>();
	static HashMap<String, String> pathMap = new HashMap<String, String>();
	static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	public static void start() throws IOException
	{
		browser.showOpenDialog(null);
		File f = browser.getSelectedFile();
		BufferedReader in = new BufferedReader(new FileReader(f));
		System.out.println("[");
		String line = in.readLine();
		while(line != null)
		{
      String temp = read(new File(line));
			line = in.readLine();
      if(line == null)
		    System.out.println("]");
      else
        System.out.print(temp);
		}
	}
	public static String read(File f) throws FileNotFoundException
	{
		pNames = new ArrayList<String>();
		pPaths = new ArrayList<String>();
		int[] counts;
		BufferedReader in = new BufferedReader(new FileReader(f));
		String whole = "";
		String line = "";
		do
		{
			try
			{
				line = in.readLine();
				whole += line;
				if(line.contains("[]") || line.contains("()"))
					return("");
				if(line.contains(")"))
					break;
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}while(true);
		String theRest = "";
		try
		{
			line = in.readLine();
			while(line != null)
			{
				theRest += line;
				line = in.readLine();
			}
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		String paths = whole.substring(whole.indexOf("[")+1, whole.indexOf("]"));
		if(paths.length()==0)
			return("");
		String names = whole.substring(whole.indexOf("(")+1, whole.indexOf(")"));
		String[] splitPaths = paths.split(",");
		for(String s : splitPaths)
		{
			if(s.indexOf("'")==-1)
				pPaths.add(s);
			else
				pPaths.add(s.substring(s.indexOf("'"), s.lastIndexOf("'")+1).trim());
		}
		String[] splitNames = names.split(",");
		for(String s : splitNames)
		{
			if(s.length()==0)
				pPaths.add(s);
			pNames.add(s.trim().replace("\\", ""));
		}	
		System.out.println("{\"file\":\"" + f.toString() + "\",\"counts\":[");
		counts = new int[pNames.size()];
		for(int i = 0; i < pNames.size(); i++)
		{
			counts[i] = countString(theRest, pNames.get(i));
			if(i < pNames.size()-1)
				System.out.println("{\"name\":\"" + pNames.get(i) + "\",\"path\":" + pPaths.get(i).replace("'", "\"").replace("\\","") + ",\"count\":\""+counts[i]+"\"},");
			else
				System.out.println("{\"name\":\"" + pNames.get(i) + "\",\"path\":" + pPaths.get(i).replace("'", "\"").replace("\\","") + ",\"count\":\""+counts[i]+"\"}]}");
		}
    return(",");
	}
	public static int countString(String text, String target)
	{
		int count = 0;
		int location = 0;
		while(text.indexOf(target, location)!=-1)
		{
			count++;
			location = text.indexOf(target, location)+1;
		}
		return count;
	}
}
