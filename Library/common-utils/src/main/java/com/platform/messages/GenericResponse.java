package com.platform.messages;

import java.util.List;

/**
 * @author MuhilKennedy
 *
 * @param <T> can be used for any object/List of Objects as generic response
 *            type.
 */
public class GenericResponse<T> extends Response {
	private T data;
	private List<?> dataList;

	public GenericResponse() {
		// No Op.
	}
	
	/* default would be a 200 status code */
	public GenericResponse(T body) {
		this.setData(body).setResponseStatus(Response.Status.OK);
	}

	public GenericResponse(List<?> dataList) {
		this.setDataList(dataList).setResponseStatus(Response.Status.OK);
	}

	public GenericResponse(T body, Response.Status status) {
		this.setData(body).setResponseStatus(status);
	}

	public GenericResponse(List<?> dataList, Response.Status status) {
		this.setDataList(dataList).setResponseStatus(status);
	}
	
	public T getData() {
		return data;
	}

	public GenericResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public GenericResponse<T> setDataList(List<?> dataList) {
		this.dataList = dataList;
		return this;
	}
	
	public GenericResponse<T> setStatus(Response.Status status) {
		super.setResponseStatus(status);
		return this;
	}

	public GenericResponse<T> build() {
		return this;
	}
}
