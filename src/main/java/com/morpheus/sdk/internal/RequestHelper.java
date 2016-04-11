package com.morpheus.sdk.internal;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.*;
import com.morpheus.sdk.provisioning.ApplySecurityGroupsResponse;
import com.morpheus.sdk.provisioning.DisableFirewallResponse;
import com.morpheus.sdk.provisioning.EnableFirewallResponse;
import com.morpheus.sdk.provisioning.ListAppliedSecurityGroupsResponse;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class RequestHelper {

	public static <T extends ApiResponse> T executeRequest(Class<T> clazz, AbstractApiRequest<T> apiRequest, String path, String method) throws MorpheusApiRequestException{

		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(apiRequest.endpointUrl);
			uriBuilder.setPath(path);
			HttpRequestBase request = null;

			if(method == HttpPut.METHOD_NAME) {
				request = new HttpPut(uriBuilder.build());
			} else if (method == HttpPost.METHOD_NAME) {
				request = new HttpPost(uriBuilder.build());
			} else if (method == HttpDelete.METHOD_NAME) {
				request = new HttpDelete(uriBuilder.build());
			} else if (method == HttpGet.METHOD_NAME) {
				request = new HttpGet(uriBuilder.build());
			}
			apiRequest.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(apiRequest.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			String requestBody = apiRequest.generateRequestBody();
			if(requestBody != null && (method == HttpPost.METHOD_NAME || method == HttpPut.METHOD_NAME)) {
				((HttpEntityEnclosingRequestBase)request).setEntity(new StringEntity(requestBody));
			}
			CloseableHttpResponse response = client.execute(request);
			return RequestHelper.createInstanceFromResponse(clazz, response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request", ex);
		} finally {
			if(client != null) {
				try {
					client.close();
				} catch(IOException io) {
					//ignore
				}
			}
		}
	}

	protected static <T extends ApiResponse> T createInstanceFromResponse(Class<T> clazz, InputStream stream) throws Exception{
		if(clazz.getName() == UpdateSecurityGroupResponse.class.getName()) {
			return (T) UpdateSecurityGroupResponse.createFromStream(stream);
		} if(clazz.getName() == ListSecurityGroupsResponse.class.getName()) {
			return (T) ListSecurityGroupsResponse.createFromStream(stream);
		} if(clazz.getName() == GetSecurityGroupResponse.class.getName()) {
			return (T) GetSecurityGroupResponse.createFromStream(stream);
		} if(clazz.getName() == CreateSecurityGroupResponse.class.getName()) {
			return (T) CreateSecurityGroupResponse.createFromStream(stream);
		} if(clazz.getName() == DeleteSecurityGroupResponse.class.getName()) {
			return (T) DeleteSecurityGroupResponse.createFromStream(stream);
		} if(clazz.getName() == ListSecurityGroupRulesResponse.class.getName()) {
			return (T) ListSecurityGroupRulesResponse.createFromStream(stream);
		} if(clazz.getName() == GetSecurityGroupRuleResponse.class.getName()) {
			return (T) GetSecurityGroupRuleResponse.createFromStream(stream);
		} if(clazz.getName() == DeleteSecurityGroupRuleResponse.class.getName()) {
			return (T) DeleteSecurityGroupRuleResponse.createFromStream(stream);
		} if(clazz.getName() == UpdateSecurityGroupRuleResponse.class.getName()) {
			return (T) UpdateSecurityGroupRuleResponse.createFromStream(stream);
		} if(clazz.getName() == CreateSecurityGroupRuleResponse.class.getName()) {
			return (T) CreateSecurityGroupRuleResponse.createFromStream(stream);
		} if(clazz.getName() == DisableFirewallResponse.class.getName()) {
			return (T) DisableFirewallResponse.createFromStream(stream);
		} if(clazz.getName() == EnableFirewallResponse.class.getName()) {
			return (T) EnableFirewallResponse.createFromStream(stream);
		} if(clazz.getName() == ListAppliedSecurityGroupsResponse.class.getName()) {
			return (T) ListAppliedSecurityGroupsResponse.createFromStream(stream);
		} if(clazz.getName() == ApplySecurityGroupsResponse.class.getName()) {
			return (T) ApplySecurityGroupsResponse.createFromStream(stream);
		} else {
			throw new Exception(clazz.getName());
		}
	}
}
