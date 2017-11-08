package testJDBC;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<chinese_unit_test> databean = new ArrayList<chinese_unit_test>();

    	
        databean = JDBCOperation.getAll();
//    	for (int j = 0; j < databean.size(); j++) {
//    		System.out.println(databean.get(j).getName());
//		}
        ExcelUtils.WriteToFile("C:/Users/hp/Desktop/test/chinese_unit_test.xls", databean);
        System.out.println("Output successful!");
    }
}
