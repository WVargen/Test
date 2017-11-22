package testRec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Utils {
	public static void checkAndCreateDirectory(String path)
    {
         if (path == null)
         {
               return;
         }

         File file = new File(path);
         File parent = file.getParentFile();

         if (parent != null && !parent.exists())
         {
             parent.mkdirs();
         }
    }
	
	public static String readStringFromFile(String path)
    {
        byte[] bytes = readFromFile(path);

        try
        {
            return new String(bytes, "utf-8");
        }
        catch (Exception e)
        {

        }

        return null;
    }

    public static byte[] readFromFile(String path)
    {
        try
        {
            FileInputStream fis = new FileInputStream(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 32];
            int k = 0;
            while ((k = fis.read(buffer)) > 0)
            {
                bos.write(buffer, 0, k);
                bos.flush();
            }

            fis.close();
            bos.close();

            return bos.toByteArray();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
