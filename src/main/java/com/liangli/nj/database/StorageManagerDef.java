package com.liangli.nj.database;

import java.util.List;
import java.util.Map;

public interface StorageManagerDef
{
    boolean executeSQL(String sql, Object[] selectionArgs);

    List<Map<String, String>> executeSQLForMapList(String query, Object[] selectionArgs);
    
    void checkMaxAllowedPacket();
}
