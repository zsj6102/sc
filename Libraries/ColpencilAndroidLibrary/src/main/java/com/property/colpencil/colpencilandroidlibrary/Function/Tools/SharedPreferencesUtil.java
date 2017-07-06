package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description:SharedPreferences单例工具
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class SharedPreferencesUtil {

	public SharedPreferences sp;

	private static SharedPreferencesUtil spu = null;

	public final static String SHARED_NAME = "Colpencil";

	public SharedPreferencesUtil(Context context) {
		this.sp = context.getSharedPreferences(SHARED_NAME, 0);
	}

	public static SharedPreferencesUtil getInstance(Context context){
		if (spu == null) {
			spu = new SharedPreferencesUtil(context);
		}
		return spu;
	}

	/**
	 * 保存List
	 * @param tag
	 * @param dataList
	 */
	public <T> void setDataList(String tag, List<T> dataList) {
		if (null == dataList || dataList.size() <= 0)
			return;
		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(dataList);
		spu.setString(tag, strJson);
	}
	/**
	 * 获取List
	 * @param tag
	 * @return
	 */
	public List getDataList(String tag,Type type) {
		List dataList=new ArrayList<>();
		String strJson = spu.getString(tag, null);
		if (null == strJson) {
			return dataList;
		}
		Gson gson = new Gson();
		dataList = gson.fromJson(strJson, type);
		return dataList;

	}

	/**
	 * @param key 键值名
	 * @param b	设置boolean值
	 */
	public void setBoolean(String key,boolean b){
		sp.edit().putBoolean(key, b).commit();
	}


	/**
	 * @param key 键名
	 * @param 	defult
	 * @return boolean值
	 */
	public boolean getBoolean(String key,boolean defult){
		return sp.getBoolean(key,defult);
	}

	/**
	 * @param key 键名
	 * @param s	设置字符串
	 */
	public void setString(String key,String s){
		sp.edit().putString(key, s).commit();
	}

	/**
	 * @param key 键名
	 * @param defult	默认返回值
	 * @return 字符串
	 */
	public String getString(String key,String defult){
		return sp.getString(key, defult);
	}
	public String getString(String key ){
		return sp.getString(key, "");
	}
	/**
	 * @param key 键名
	 * @param i	设置整型值
	 */
	public void setInt(String key,int i){
		sp.edit().putInt(key, i).commit();
	}

	/**
	 * @param key 键名
	 * @param defult	默认返回int值
	 * @return	int
	 */
	public int getInt(String key,int defult){
		return sp.getInt(key, defult);

	}
	public int getInt(String key){
		return sp.getInt(key, 0);
	}

	/**
	 * 清空
	 */
	public void clear() {
		sp.edit().clear().commit();
	}

	/**
	 * 删除指定的key
	 * @param key
     */
	public void remove(String key){
		sp.edit().remove(key).commit();
	}
}
