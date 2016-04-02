package com.system.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {  
	
	public static final String DATA_SOURCE_USERSYS = "dataSourceUsersys";
    public static final String DATA_SOURCE_USERSYSQUERY = "dataSourceUsersysQuery";
	
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
  
    public static String getCurrentLookupKey() {  
        return (String) contextHolder.get();  
    }  
  
    public static void setCurrentLookupKey(String currentLookupKey) {  
        contextHolder.set(currentLookupKey);  
    }  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return getCurrentLookupKey();  
    }  
  
}  
