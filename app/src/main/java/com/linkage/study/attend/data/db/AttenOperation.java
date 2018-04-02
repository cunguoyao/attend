package com.linkage.study.attend.data.db;

import org.json.JSONObject;

import java.io.Serializable;

public class AttenOperation implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public int status ;
    public String name;
    
    public static AttenOperation parseJson(JSONObject jsonObj)
    {
        AttenOperation bean = new AttenOperation();
        bean.name = jsonObj.optString("name");
        bean.status = jsonObj.optInt("status");
        return bean;
    }
}
