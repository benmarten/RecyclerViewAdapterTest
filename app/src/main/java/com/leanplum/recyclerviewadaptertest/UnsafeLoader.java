package com.leanplum.recyclerviewadaptertest;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnsafeLoader {
  private static Object unsafe;
  private static Method allocateInstance;

  public UnsafeLoader() {

  }

  /**
   * Allocates a new instance of the class without invoking any of its constructors.
   *
   * @param clazz The class for the new object.
   * @return If successful, a new object of the provided class, null if failed.
   */
  public Object allocateInstance(Class clazz) {
    try {
      return UnsafeAllocator.create().newInstance(clazz);
    } catch (IllegalAccessException e) {
      // TODO: handleException
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO: handleException
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public Object allocateIdenticalInstance(Class clazz, Object oldObject) {
    Object newObject = this.allocateInstance(clazz);
    if (newObject != null) {
      copyValues(oldObject, newObject);
    }
    return newObject;
  }

  private void copyValues(Object fromObject, Object toObject) {
    for (Field field : getAllDeclaredFields(fromObject.getClass())) {
      Log.d("Leanplum", "Copying field: " + field.getName());
      field.setAccessible(true);
      Object value;
      try {
        value = field.get(fromObject);
        field.set(toObject, value);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  private List<Field> getAllDeclaredFields(Class clazz) {
    List<Field> fields = new ArrayList<>();
    while (!clazz.equals(Object.class)) {
      fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
      clazz = clazz.getSuperclass();
    }
    return fields;
  }
}
