package com.platform.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.ParameterizedTypeReference;

/**
 * @author Muhil
 */
public class TypeReferenceUtil {

	/**
	 * @param <T>
	 * @param rawClass
	 * @param parameterClass
	 * @return rawClass<parameterClass>
	 */
	public static <T> ParameterizedTypeReference<T> createParameterizedTypeReference(Class<?> rawClass,
			Class<?> parameterClass) {

		ParameterizedType parameterizedType = new ParameterizedType() {
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[] { parameterClass };
			}

			@Override
			public Type getRawType() {
				return rawClass;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
		};

		return new ParameterizedTypeReference<T>() {
			@Override
			public Type getType() {
				return parameterizedType;
			}
		};
	}

}
