package com.reynaldiwijaya.crudapi.model.detail;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailUser{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}
}