package testRec;

import java.io.File;

public class Utils {

	public static class file
	{
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
	}
}
