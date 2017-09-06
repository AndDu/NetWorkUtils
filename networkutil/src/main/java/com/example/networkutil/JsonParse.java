package com.example.networkutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Huang on 2017/7/1.
 */

public class JsonParse {

	
	public static <T> List<T> parseArray(String content, Class<T> cls){
		
		List<T> list=new ArrayList<T>();
		try {
			JSONArray array = new JSONArray(content);
			for (int i = 0; i < array.length(); i++) {
				T mode = parse(array.optJSONObject(i).toString(), cls);
				list.add(mode);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
		
	}
	
	
    public static <T> T parse(String content, Class<T> cls) {

        Field[] mainFields = cls.getDeclaredFields();
        Method[] mainMethods = cls.getDeclaredMethods();

        Field[] fields = getSuperClassFields(cls, mainFields);
        Method[] methods = getSuperClassMethods(cls, mainMethods);

        if (fields.length == 0 || methods.length == 0 || content==null || content.isEmpty()) return null;
        try {
            JSONObject object = new JSONObject(content);
            T o = cls.newInstance();

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName(); //参数名
//                LogUtil.("解析的参数：" + fieldName);
                Class<?> fieldType = field.getType(); //获取参数类型
                String fieldTypeName = fieldType.getName();  //参数名类型的全名 类似java.lang.String
                if (fieldType.isPrimitive()) { //判断是否为基本数据类型
                    Object opt = object.opt(field.getName());
                    if (opt != null) {
                        field.set(o, opt);
                    }
                } else {
                    if (fieldType.isAssignableFrom(String.class)) {  //判断是否为字符串
                        String optString = object.optString(fieldName);
						field.set(o, optString);
                    } else if (fieldType.isAssignableFrom(List.class)) { //属于List类型
                        Type genericType = field.getGenericType();
                        if (genericType instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) genericType;
                            Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                            String listTypeName = genericClazz.getName();   //List泛型的全名
//                            System.out.println("List的泛型为：" + name1);
                            JSONArray jsonArray = object.optJSONArray(field.getName());
                            int length = jsonArray.length();
                            List<Object> list = new ArrayList<Object>(length);
                            for (int i = 0; i < length; i++) {
                                JSONObject object1 = jsonArray.optJSONObject(i);
                                if (object1 != null) {
                                    Object parse = parse(object1.toString(), Class.forName(listTypeName));
                                    list.add(parse);
                                }
                            }
                            field.set(o, list);
                        }
                    } else { //属于某个实体类
                        JSONObject childObject = object.optJSONObject(field.getName());
                        if (childObject != null) {
                            Object parseChild = parse(childObject.toString(), Class.forName(fieldTypeName));
                            if (parseChild != null) {
                                field.set(o, parseChild);
                            }
                        }
                    }
                }
            }
            return o;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 两个数组相加
     *
     * @param a
     * @param b
     * @param <A>
     * @return
     */
    public static <A> A[] addAll(A[] a, A[] b) { //两个数组相加

        A[] addArray = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, addArray, a.length, b.length);
        return addArray;
    }


    /**
     * 获取父类的参数
     *
     * @param cls
     * @param fields
     * @return
     */
    private static Field[] getSuperClassFields(Class<?> cls, Field[] fields) {
        Class<?> superclass = cls.getSuperclass();
        if (!superclass.equals(Object.class)) {
            Field[] superclassFields = superclass.getDeclaredFields();
            return getSuperClassFields(superclass, addAll(fields, superclassFields));
        } else {
            return fields;
        }
    }

    /**
     * 获取父类的方法
     *
     * @param cls
     * @param methods
     * @return
     */
    private static Method[] getSuperClassMethods(Class<?> cls, Method[] methods) {
        Class<?> superclass = cls.getSuperclass();
        if (!superclass.equals(Object.class)) {
            Method[] superMethods = superclass.getDeclaredMethods();
            return getSuperClassMethods(superclass, addAll(methods, superMethods));
        } else {
            return methods;
        }
    }


}
