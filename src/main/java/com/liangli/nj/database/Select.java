package com.liangli.nj.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.liangli.nj.log.FatalLog;
import com.liangli.nj.log.Logger;
import com.liangli.nj.log.SqlLog;
import com.liangli.nj.utils.Strings;

public class Select {

	Logger logger = Logger.get();
	
	public static Select get(StorageManagerDef storageManager)
	{
		Select select = new Select(storageManager);
		
		return select;
	}
	
	StorageManagerDef storageManager;
	
	public Select(StorageManagerDef storageManager) {
		super();
		this.storageManager = storageManager;
	}

	final static String BLANK = " ";
	final static String SELECT = "SELECT";
	final static String UPDATE = "UPDATE";
	final static String DELETE = "DELETE";
	final static String FROM = "FROM";
	final static String WHERE = "WHERE";
	final static String ORDERBY = "ORDER BY";
	final static String GROUPBY = "GROUP BY";
	final static String LIMIT = "LIMIT";
	
	/**
     * Representing the limit clause in SQL.
     */
    String mLimit;

    /**
     * Representing the offset in SQL.
     */
    String mOffset;
    
    public Select limit(int value) {

        this.mLimit = String.valueOf(value);

        return this;
    }
    
    public Select offset(int value) 
    {
        mOffset = String.valueOf(value);
        
        return this;
    }
	
	private StringBuilder selectSb = new StringBuilder();
	private StringBuilder whereSb = new StringBuilder();
	private StringBuilder fromSb = new StringBuilder();
	private String orderByStr;
	private String groupByStr;
	private BaseTable firstTable;
	private Set<BaseTable> tables = new TreeSet<>(new Comparator<BaseTable>() {
	
		@Override
		public int compare(BaseTable o1, BaseTable o2) 
		{
			return o1.tableName().compareTo(o2.tableName());
		}
	});
	
	public Select orderBy(String str)
	{
		this.orderByStr = str;
		
		return this;
	}
	
	public Select groupBy(String str)
	{
		this.groupByStr = str;
		
		return this;
	}
	
	public Select select(BaseTable table)
	{
		if (table == null)
		{
			return this;
		}
		
		if (firstTable == null && fromSb.length() == 0)
		{
			firstTable = table;
		}
		
		tables.add(table);		
		appendSelect(table.columns());
		
		return this;
	}
	
	public Select select(BaseTable table, String... columns)
	{
		if (table == null)
		{
			return this;
		}
		
		if (firstTable == null && fromSb.length() == 0)
		{
			firstTable = table;
		}
		
		tables.add(table);
		
		if (columns != null)
		{
			for (String column : columns)
			{
				appendSelect(table.column(column));
			}
		}
		
		return this;
	}
	
	public Select select(Class<? extends BaseTable> cls)
	{
		try {
			if (cls == null)
			{
				return this;
			}
			
			BaseTable table = cls.newInstance();			
			
			if (firstTable == null && fromSb.length() == 0)
			{
				firstTable = table;
			}
			
			tables.add(table);
			appendSelect(table.columns());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Select select(Class<? extends BaseTable> cls, String... columns)
	{
		try 
		{
			if (cls == null)
			{
				return this;
			}			
			
			BaseTable table = cls.newInstance();
			
			if (firstTable == null && fromSb.length() == 0)
			{
				firstTable = table;
			}
			
			tables.add(table);
			
			if (columns != null)
			{
				for (String column : columns)
				{
					appendSelect(table.column(column));
				}
			}
		} catch (Exception e)
		{
			
		}
		
		return this;
	}
	
	public Select appendSelect(String param)
	{
		if (selectSb.length() == 0)
		{
			selectSb.append(param);
		}
		else
		{
			selectSb.append("," + param);
		}
		
		return this;
	}
	
	public <T> T findFirst(Class<T> cls)
	{
		List<T> items = find(cls);
		
		if (items != null && items.size() > 0)
		{
			return items.get(0);
		}
		
		return null;
	}
	
	public <T> T findFirst(String sql, Class<T> cls)
	{
		List<T> items = find(sql, cls);
		
		if (items != null && items.size() > 0)
		{
			return items.get(0);
		}
		
		return null;
	}
	
	private void generateFrom()
	{
		if (fromSb.length() > 0)
		{
			return;
		}
		
		for (BaseTable table : tables)
		{
			if (fromSb.length() == 0)
			{
				fromSb.append(table.tableName());
			}
			else
			{
				fromSb.append("," + table.tableName());
			}
		}
	}
	
	public boolean delete()
	{		
		generateFrom();
		
		String sql = DELETE + BLANK + FROM + BLANK + fromSb.toString();
		
		if (whereSb.length() != 0)
		{
			sql += BLANK + WHERE + BLANK + whereSb.toString();			
		}
		
//		if (MobileClientSettings.printSqlInConsol)
//		{
//			SqlLog.log("[Select->delete] - " + sql);
//		}
		
		try
		{
			return storageManager.executeSQL(sql, null);
		}
		catch (Exception e)
		{
			logger.error("delete", e);
		}
		
		return false;
	}
	
	public boolean update(BaseTable table)
	{
		generateFrom();
		
		String sql = UPDATE + BLANK + table.tableName() + BLANK + "SET" + BLANK;
		sql += table.updateClause();
		
		if (whereSb.length() != 0)
		{
			sql += BLANK + WHERE + BLANK + whereSb.toString();			
		}
		
//		if (MobileClientSettings.printSqlInConsol)
//		{
//			SqlLog.log("[Select->update] - " + sql);
//		}
		
		try
		{
			return storageManager.executeSQL(sql, null);
		}
		catch (Exception e)
		{
			logger.error("update", e);
		}
		
		return false;
	}
	
	public <T> List<T> find(Class<T> cls)
	{
		return find(getQuerySql(), cls);
	}
	
	public String getQuerySql()
	{
		String limit;
        if (mOffset == null) {
            limit = mLimit;
        } else {
            if (mLimit == null) {
                mLimit = "0";
            }
            limit = mOffset + "," + mLimit;
        }
        
		generateFrom();
		
		String sql = SELECT + BLANK + selectSb.toString();
		
		sql += BLANK + FROM + BLANK + fromSb.toString();
		
		if (whereSb.length() != 0)
		{
			sql += BLANK + WHERE + BLANK + whereSb.toString();			
		}
		
		if (Strings.notNullOrEmpty(groupByStr))
		{
			sql += BLANK + GROUPBY + BLANK + groupByStr;
		}
		
		if (Strings.notNullOrEmpty(orderByStr))
		{
			sql += BLANK + ORDERBY + BLANK + orderByStr;
		}
		
		if (Strings.notNullOrEmpty(limit))
		{
			sql += BLANK + LIMIT + BLANK + limit;
		}
		
		return sql;
	}
	
	public <T> List<T> find(String sql, Class<T> cls)
	{		
		List<T> dataList = new ArrayList<T>();
		
//		if (MobileClientSettings.printSqlInConsol)
//		{
//			SqlLog.log("[Select->find] - " + sql);
//		}
		
		try
		{
			List<Map<String, String>> result = storageManager.executeSQLForMapList(sql, null);
			
			if (result != null)
	        {
	            for (Map<String, String> map : result)
	            {
	                try
	                {
	                    @SuppressWarnings("unchecked")
						T modelInstance = (T) createInstanceFromClass(cls);
	                    setValueToModel(modelInstance, getSupportedFields(cls.getName()), map);
	                    dataList.add(modelInstance);
	                }
	                catch (IllegalAccessException e)
	                {
	                    SqlLog.logWarning(e);
	                }
	            }
	        }
		}
		catch (Exception e)
		{
			logger.error("find", e);
		}
		
		return dataList;
	}
	
	public <T> List<T> toDataList(List<Map<String, String>> result, Class<T> cls)
	{
		List<T> dataList = new ArrayList<T>();
		
		try
		{
			if (result != null)
	        {
	            for (Map<String, String> map : result)
	            {
	                try
	                {
	                    @SuppressWarnings("unchecked")
						T modelInstance = (T) createInstanceFromClass(cls);
	                    setValueToModel(modelInstance, getSupportedFields(cls.getName()), map);
	                    dataList.add(modelInstance);
	                }
	                catch (IllegalAccessException e)
	                {
	                	FatalLog.log(e);
	                }
	            }
	        }
		}
		catch (Exception e)
		{
			FatalLog.log(e);
		}
		
		return dataList;
	}
	
	public Select appendFrom(String str)
	{
		if (fromSb.length() == 0) {
			fromSb.append(str);

		} else {
			fromSb.append("," + str);
		}
		
		return this;
	}
	
	public Select from(Class<? extends BaseTable> cls) {

		try {
			appendFrom(cls.newInstance().tableName());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Select from(BaseTable table) {

		try {
			if (fromSb.length() == 0) {
				fromSb.append(table.tableName());

			} else {
				fromSb.append("," + table.tableName());
			}
		} catch (Exception e) {

		}

		return this;
	}
	
	public Select join(Class<? extends BaseTable> cls1, String col1, Class<? extends BaseTable> cls2, String col2)
	{
		try {

			whereSb.append(cls1.newInstance().column(col1))
			.append("=")
			.append(cls2.newInstance().column(col2));
			
		} catch (Exception e)
		{
			
		}
		
		return this;
	}
	
	public Select leftJoin(Class<? extends BaseTable> joinTableClass, Class<? extends BaseTable> onClass, String... on)
	{
		try 
		{
			BaseTable joinTable = joinTableClass.newInstance();
			BaseTable onTable = onClass.newInstance();
			
			leftJoin(joinTable, onTable, on);
		} catch (Exception e)
		{
			
		}
		
		return this;
	}
	
	public Select leftJoin(BaseTable joinTable,BaseTable onTable, String... on)
	{
		try {
			if (firstTable != null && fromSb.length() == 0 )
			{
				from(firstTable);
			}
			
			fromSb.append(" LEFT JOIN ")
			.append(joinTable.tableName())
			.append(" ON ");
			
			for (int i = 0; i < on.length; i += 2)
			{
				if (i != 0 && i % 2 == 0)
				{
					fromSb.append(" AND ");
				}			

				fromSb.append(joinTable.column(on[i]))
				.append("=")
				.append(onTable.column(on[i + 1]));
				
			}			
		} catch (Exception e)
		{
			
		}
		
		return this;
	}
	
	public Select joinAnd()
	{
		return whereAnd();
	}
	
	public Select whereLeft()
	{
		whereSb.append(" (");
		return this;
	}
	
	public Select whereRight()
	{
		whereSb.append(")");
		return this;
	}
	
	public Select whereAnd()
	{
		whereSb.append(" AND ");
		return this;
	}
	
	public Select whereOr()
	{
		whereSb.append(" OR ");
		return this;
	}
	
	public Select where(Class<? extends BaseTable> cls, Object...conditions)
	{
		BaseTable table = null;
		try 
		{
			table = cls.newInstance();
		} catch (Exception e)
		{
			
		}

		if (conditions.length > 0 && table != null)
		{
			conditions[0] = String.valueOf(conditions[0]).replaceAll("[$]", table.tableName());
		}

		return where(conditions);
	}
	
	public Select where(Object... conditions)
	{
		try {
			checkConditionsCorrect(conditions);
			
			String selection = getWhereClause(conditions);
			Object[] args = getWhereArgs(conditions);
			
			if (selection != null)
			{
				if (args == null)
				{
					whereSb.append(selection);
					
					return this;
				}
				
				String[] selections = selection.split("[?]");
				
				if (selections.length < args.length)
				{
					return this;
				}
				
				int i = 0;
				
				for (String sel : selections)
				{
					whereSb.append(sel);
					
					if (i >= args.length)
					{
						continue;
					}
					
					Object arg = args[i];
					
					if (arg == null)
					{
						whereSb.append("'null'");
					}
					else if (arg.getClass() == String.class)
					{
						whereSb.append("'" + escape((String)arg) + "'");
					}
					else
					{
						whereSb.append(arg);
					}
					
					i ++;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}
	
	protected String escape(String keyWord)
    {
        if (keyWord != null)
        {
            keyWord = keyWord.replace("'", "''");
        }

        return keyWord;
    }
	
	protected Object[] getWhereArgs(Object... conditions) {
        if (isAffectAllLines((Object) conditions)) {
            return null;
        }
        if (conditions != null && conditions.length > 1) {
            Object[] whereArgs = new Object[conditions.length - 1];
            System.arraycopy(conditions, 1, whereArgs, 0, conditions.length - 1);
            return whereArgs;
        }
        return null;
    }
	
	protected String getWhereClause(Object... conditions) {
        if (isAffectAllLines((Object) conditions)) {
            return null;
        }
        if (conditions != null && conditions.length > 0) {
            return (String)conditions[0];
        }
        return null;
    }
	
	protected boolean isAffectAllLines(Object... conditions) {
        if (conditions != null && conditions.length == 0) {
            return true;
        }
        return false;
    }

	
    public void checkConditionsCorrect(Object... conditions) throws Exception {
        if (conditions != null) {
            int conditionsSize = conditions.length;
            if (conditionsSize > 0) {
                String whereClause = (String)conditions[0];
                int placeHolderSize = Strings.count(whereClause, "?");
                if (conditionsSize != placeHolderSize + 1) {
                    throw new Exception("The parameters in conditions are incorrect.");
                }
            }
        }
    }
    
    protected Object createInstanceFromClass(Class<?> modelClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor<?> constructor = findBestSuitConstructor(modelClass);
            return constructor.newInstance(getConstructorParams(modelClass, constructor));
        } catch (Exception e) {
            throw e;
        }
    }
    
    protected Constructor<?> findBestSuitConstructor(Class<?> modelClass) {
        Constructor<?>[] constructors = modelClass.getDeclaredConstructors();
        Map<Integer, Constructor<?>> map = new HashMap<>();
        int minKey = Integer.MAX_VALUE;
        for (Constructor<?> constructor : constructors) {
            int key = constructor.getParameterTypes().length;
            Class<?>[] types = constructor.getParameterTypes();
            for (Class<?> parameterType : types) {
                if (parameterType == modelClass) {
                    key = key + 10000;
                }
            }
            if (map.get(key) == null) {
                map.put(key, constructor);
            }
            if (key < minKey) {
                minKey = key;
            }
        }
        Constructor<?> bestSuitConstructor = map.get(minKey);
        if (bestSuitConstructor != null) {
            bestSuitConstructor.setAccessible(true);
        }
        return bestSuitConstructor;
    }
    
    protected Object[] getConstructorParams(Class<?> modelClass, Constructor<?> constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getInitParamValue(modelClass, paramTypes[i]);
        }
        return params;
    }
    
    private Object getInitParamValue(Class<?> modelClass, Class<?> paramType) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String paramTypeName = paramType.getName();
        if ("boolean".equals(paramTypeName) || "java.lang.Boolean".equals(paramTypeName)) {
            return false;
        }
        if ("float".equals(paramTypeName) || "java.lang.Float".equals(paramTypeName)) {
            return 0f;
        }
        if ("double".equals(paramTypeName) || "java.lang.Double".equals(paramTypeName)) {
            return 0.0;
        }
        if ("int".equals(paramTypeName) || "java.lang.Integer".equals(paramTypeName)) {
            return 0;
        }
        if ("long".equals(paramTypeName) || "java.lang.Long".equals(paramTypeName)) {
            return 0L;
        }
        if ("short".equals(paramTypeName) || "java.lang.Short".equals(paramTypeName)) {
            return 0;
        }
        if ("char".equals(paramTypeName) || "java.lang.Character".equals(paramTypeName)) {
            return ' ';
        }
        if ("[B".equals(paramTypeName) || "[Ljava.lang.Byte;".equals(paramTypeName)) {
            return new byte[0];
        }
        if ("java.lang.String".equals(paramTypeName)) {
            return "";
        }
        if (modelClass == paramType) {
            return null;
        }
        return createInstanceFromClass(paramType);
    }
    
    final private static Map<String, List<Field>> classFieldsList = new HashMap<String, List<Field>>();
    
    public static List<Field> getSupportedFields(String className) throws ClassNotFoundException {
        List<Field> fieldList = classFieldsList.get(className);
        if (fieldList == null) {
            List<Field> supportedFields = new ArrayList<Field>();
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw e;
            }
            recursiveSupportedFields(clazz, supportedFields);
            classFieldsList.put(className, supportedFields);
            return supportedFields;
        }
        return fieldList;
    }
    
    private static void recursiveSupportedFields(Class<?> clazz, List<Field> supportedFields) {
        if (clazz == Object.class) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                Column annotation = field.getAnnotation(Column.class);
                if (annotation != null && annotation.ignore()) {
                    continue;
                }
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    Class<?> fieldTypeClass = field.getType();
                    String fieldType = fieldTypeClass.getName();
                    if (isFieldTypeSupported(fieldType)) {
                        supportedFields.add(field);
                    }
                }
            }
        }
        recursiveSupportedFields(clazz.getSuperclass(), supportedFields);
    }
    
    public static boolean isFieldTypeSupported(String fieldType) {
        if ("boolean".equals(fieldType) || "java.lang.Boolean".equals(fieldType)) {
            return true;
        }
        if ("float".equals(fieldType) || "java.lang.Float".equals(fieldType)) {
            return true;
        }
        if ("double".equals(fieldType) || "java.lang.Double".equals(fieldType)) {
            return true;
        }
        if ("int".equals(fieldType) || "java.lang.Integer".equals(fieldType)) {
            return true;
        }
        if ("long".equals(fieldType) || "java.lang.Long".equals(fieldType)) {
            return true;
        }
        if ("short".equals(fieldType) || "java.lang.Short".equals(fieldType)) {
            return true;
        }
        if ("char".equals(fieldType) || "java.lang.Character".equals(fieldType)) {
            return true;
        }
        if ("[B".equals(fieldType) || "[Ljava.lang.Byte;".equals(fieldType)) {
            return true;
        }
        if ("java.lang.String".equals(fieldType) || "java.util.Date".equals(fieldType)) {
            return true;
        }
        return false;
    }
    
    protected void setValueToModel(Object modelInstance, List<Field> supportedFields, Map<String, String> record) throws IllegalAccessException {

        for (Field field : supportedFields) {
            Object value = record.get(field.getName());

            if (value != null) {
                setToModelByReflection(modelInstance, field, value);
            }
        }
    }
    
    private void setToModelByReflection(Object modelInstance, Field field, Object fieldValue) throws IllegalAccessException {
        Object value = getValue(field, fieldValue);
        setField(modelInstance, field.getName(), value,
                modelInstance.getClass());
    }
    
    private Object getValue(Field field, Object value) {
        Class<?> fieldType = field.getType();
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        
        if ("Boolean".equals(typeName)) {
            return Strings.parseBoolean(String.valueOf(value), false);
        } else if ("String".equals(typeName)) {
            return value;
        } else if ("Date".equals(typeName)) {
            return Strings.parseLong(String.valueOf(value), 0);
        }
        else if ("Long".equals(typeName)) {
            return Strings.parseLong(String.valueOf(value), 0);
        }
        else if ("Int".equals(typeName)) {
            return Strings.parseInt(String.valueOf(value), 0);
        }
        else if ("Double".equals(typeName))
        {
        	return Strings.parseDouble(String.valueOf(value), 0);
        }
        else if ("Float".equals(typeName))
        {
        	return Strings.parseFloat(String.valueOf(value), 0);
        }

        return value;
    }
    
    static void setField(Object object, String fieldName, Object value, Class<?> objectClass) throws IllegalAccessException {
        try {
            set(object, fieldName, value, objectClass);
        } catch (NoSuchFieldException e) {
            setField(object, fieldName, value, objectClass.getSuperclass());
        }
    }
    
    static void set(Object object, String fieldName, Object value, Class<?> objectClass)
            throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        Field objectField = objectClass.getDeclaredField(fieldName);
        objectField.setAccessible(true);
        objectField.set(object, value);
    }
    
    public static String capitalize(String string) {
        if (!Strings.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }

}
