package com.liangli.nj.database;

public class DatabaseAccessor {
	
	final static DatabaseAccessor instance = new DatabaseAccessor();
	
	public static DatabaseAccessor get()
	{
		return instance;
	}
	
	StorageManagerDef storageManager = new JDBCUtils();
	
	public Select getSelect()
	{
		return Select.get(storageManager);
	}

}
