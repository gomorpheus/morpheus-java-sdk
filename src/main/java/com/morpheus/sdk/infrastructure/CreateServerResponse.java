package com.morpheus.sdk.infrastructure;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;

public class CreateServerResponse {
		public HashMap<String,String> errors;
		public Boolean success;
		@SerializedName("server")
		public Server server;

		public static CreateServerResponse createFromStream(InputStream stream) {
				Gson gson = MorpheusGsonBuilder.build();
				InputStreamReader reader = new InputStreamReader(stream);
				return gson.fromJson(reader, CreateServerResponse.class);
		}
}
