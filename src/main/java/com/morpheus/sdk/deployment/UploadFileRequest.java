package com.morpheus.sdk.deployment;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * A request used for uploading files into an App Deployment archive. Once all your files are uploaded to the specified {@link AppDeploy} object,
 * a deploy call can be made to push the deployment out to the running containers.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     }
 * </pre>
 */
public class UploadFileRequest extends AbstractApiRequest<UploadFileResponse> {
	private Long appDeployId;
	private InputStream inputStream;
	private File file;
	private String contentType;
	private String originalName;
	private String destination;


	@Override
	public UploadFileResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			if(this.getAppDeployId() == null) {
				throw new MorpheusApiRequestException("App Deploy id not specified. This API request requires an appDeployId be set.");
			}
			if(this.getDestination() == null) {
				throw new MorpheusApiRequestException("Destination path for file upload not specified");
			}
			if(this.getInputStream() == null) {
				throw new MorpheusApiRequestException("Input Stream or File not specified for upload");
			}

			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			String basePath = "/api/deploy/" + this.getAppDeployId() + "/files/";

			uriBuilder.setPath(basePath + this.getDestination());
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);

			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.setEntity(generateRequestBody());
			CloseableHttpResponse response = client.execute(request);
			return UploadFileResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Uploading File", ex);
		} finally {
			if(client != null) {
				try {
					client.close();
				} catch(IOException io) {
					//ignore
				}
			}
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch(IOException io) {
					//ignore
				}
			}
		}
	}

	public HttpEntity generateRequestBody() {
		return MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody("file",this.inputStream, ContentType.DEFAULT_BINARY,getOriginalName()).build();
	}

	public InputStream getInputStream() throws FileNotFoundException {
		if(file != null && inputStream == null) {
			this.inputStream = new BufferedInputStream(new FileInputStream(file),512);
		}
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setFile(File file) {
		this.file = file;
		this.setOriginalName(file.getName());
	}

	public UploadFileRequest file(File file){
		setFile(file);
		return this;
	}

	public UploadFileRequest inputStream(InputStream inputStream) {
		setInputStream(inputStream);
		return this;
	}


	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public UploadFileRequest destination(String destination) {
		this.setDestination(destination);
		return this;
	}

	public Long getAppDeployId() {
		return appDeployId;
	}

	public void setAppDeployId(Long appDeployId) {
		this.appDeployId = appDeployId;
	}

	public UploadFileRequest appDeployId(Long appDeployId) {
		this.setAppDeployId(appDeployId);
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public UploadFileRequest originalName(String originalName) {
		this.setOriginalName(originalName);
		return this;
	}
}
