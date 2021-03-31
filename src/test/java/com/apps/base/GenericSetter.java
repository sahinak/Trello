package com.apps.base;


import java.lang.reflect.Method;

public class GenericSetter
{

    public static void setDataString(Object obj,String key,String value) throws ReflectiveOperationException
    {
        if (!value.equalsIgnoreCase("NOT_SET"))
        {
            Method method=obj.getClass().getMethod("set"+key.substring(0, 1).toUpperCase() + key.substring(1),String.class);
            if (value.equalsIgnoreCase("NULL"))
            {
                method.invoke(obj, (Object) null);
            }
            else
            {
                method.invoke(obj,value);
            }
        }

   }

    public static void setDataBoolean(Object obj,String key,String value) throws ReflectiveOperationException
    {
        if (!value.equalsIgnoreCase("NOT_SET"))
        {
            Method method=obj.getClass().getMethod("set"+key.substring(0, 1).toUpperCase() + key.substring(1),Boolean.class);
            if (value.equalsIgnoreCase("NULL"))
            {
                method.invoke(obj, (Object) null);
            }
            else
            {
                method.invoke(obj,Boolean.parseBoolean(value));
            }
        }
    }

    public static void setDataObject(Object obj,String key,Object value) throws ReflectiveOperationException
    {
        Method method=obj.getClass().getMethod("set"+key.substring(0, 1).toUpperCase() + key.substring(1),Object.class);
        method.invoke(obj,value);

    }

}

