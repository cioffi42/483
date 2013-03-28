import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileParser {
	  public String fileToString(String file)
	  {
	      String contents = "";

	      File f = null;
	      try
	      {
	          f = new File(file);

	          if (f.exists())
	          {
	              FileReader fr = null;
	              try
	              {
	                  fr = new FileReader(f);
	                  char[] template = new char[(int) f.length()];
	                  fr.read(template);
	                  contents = new String(template);
	              }
	              catch (Exception e)
	              {
	                  e.printStackTrace();
	              }
	              finally
	              {
	                  if (fr != null)
	                  {
	                      fr.close();
	                  }
	              }
	          }
	      }
	      catch (Exception e)
	      {
	          e.printStackTrace();
	      }
	      return contents;
	  }
}