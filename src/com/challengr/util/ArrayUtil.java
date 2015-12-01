package com.challengr.util;

import java.lang.reflect.Array;

public class ArrayUtil {
	
	/**
	 * 给数组扩容
	 * @param obj 待扩容的数组
	 * @param addLength 需要增加的长度
	 * @return
	 */
	public static Object arrayExpand(Object obj) {
		Class klass = obj.getClass();
		if(!klass.isArray()) {
			throw new ArrayUtil().new NotArrayException();
		}
		Class componentType = klass.getComponentType();
		int length = Array.getLength(obj);
		int newLength = 2 * length;
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(obj, 0, newArray, 0, length);
		return newArray;
		
	}
	
	class NotArrayException extends RuntimeException {
		private static final long serialVersionUID = -1613625006275070456L;

		public NotArrayException() {
			super();
		}
	}
	
}
