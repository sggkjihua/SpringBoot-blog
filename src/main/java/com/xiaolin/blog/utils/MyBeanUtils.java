package com.xiaolin.blog.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    public static String[] getNullPropertyNames(Object object){
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for(PropertyDescriptor pd:pds){
            if(beanWrapper.getPropertyValue(pd.getName()) == null){
                nullPropertyNames.add(pd.getName());
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);

    }

}
