import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

import javax.swing.JFileChooser;
public class Processor 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		try
		{
			start(args);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	static JFileChooser browser = new JFileChooser("/Users/wxstamper/github/Iron/client/public/modes/ready_training");
	static ArrayList<String> pNames = new ArrayList<String>();
	static ArrayList<String> pPaths = new ArrayList<String>();
  static String totalResult = "";
	public static void start(String[] args) throws IOException
	{
    File f;
    if(args.length >= 1)
    {
      f = new File(args[0]);
    }
    else
    {
      if(browser.showOpenDialog(null)==JFileChooser.CANCEL_OPTION)
      {
        System.exit(0);
      }
      f = browser.getSelectedFile();
    }
		BufferedReader in = new BufferedReader(new FileReader(f));

		totalResult = "var filecounts = [";
		String line = in.readLine();
		while(line != null)
		{
      String temp = read(new File(line));
			line = in.readLine();
      if(line == null)
		    totalResult = totalResult + "]";
      else
        totalResult = totalResult + temp;
		}

    if(args.length >= 2)
    {
      PrintStream jsonPrinter = new PrintStream(args[1]);
      jsonPrinter.print(totalResult);
    }
    else
    {
      System.out.println(totalResult);
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
		totalResult = totalResult + "{\"file\":\"" + f.toString() + "\",\"counts\":[";
		counts = new int[pNames.size()];
		for(int i = 0; i < pNames.size(); i++)
		{
			counts[i] = countString(theRest, pNames.get(i));
			if(i < pNames.size()-1)
				totalResult = totalResult + "{\"name\":\"" + pNames.get(i) + "\",\"path\":" + pPaths.get(i).replace("'", "\"").replace("\\","") + ",\"count\":\""+counts[i]+"\"},\n";
			else
				totalResult = totalResult + "{\"name\":\"" + pNames.get(i) + "\",\"path\":" + pPaths.get(i).replace("'", "\"").replace("\\","") + ",\"count\":\""+counts[i]+"\"}]}\n";
		}
    return(",");
	}
	public static int countString(String text, String target)
	{
		int count = 0;
    Pattern p = Pattern.compile(target+"[\\[\\(\\.]");
    Matcher m = p.matcher(text);
    while(m.find())
      count++;
    return count;
	}
}
