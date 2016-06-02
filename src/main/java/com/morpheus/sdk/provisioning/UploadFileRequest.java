package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * A request used for uploading files into an Deployment Version. Once all your files are uploaded to the specified {@link DeploymentVersion} object,
 * a deploy call can be created  to push the deployment out to a particular running instance.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     }
 * </pre>
 */
public class UploadFileRequest extends AbstractApiRequest<UploadFileResponse> {
	private Long deploymentId;
	private Long deploymentVersionId;
	private InputStream inputStream;
	private File file;
	private String contentType;
	private String originalName;
	private String destination;


	@Override
	public UploadFileResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			if(this.getDeploymentVersionId() == null) {
				throw new MorpheusApiRequestException("Deployment Version id not specified. This API request requires an deploymentVersionId be set.");
			}
			if(this.getDeploymentId() == null) {
				throw new MorpheusApiRequestException("Deployment id not specified. This API request requires an deploymentId be set.");
			}
			if(this.getDestination() == null) {
				throw new MorpheusApiRequestException("Destination path for file upload not specified");
			}
			if(this.getInputStream() == null) {
				throw new MorpheusApiRequestException("Input Stream or File not specified for upload");
			}

			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			String basePath = "/api/deployments/" + this.getDeploymentId() + "/versions/" + this.getDeploymentVersionId() + "/files/";

			uriBuilder.setPath(basePath + this.getDestination());
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);

			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.setEntity(generateMultipartRequestBody());
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

	public HttpEntity generateMultipartRequestBody() {
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

	public Long getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
	}

	public Long getDeploymentVersionId() {
		return deploymentVersionId;
	}

	public void setDeploymentVersionId(Long deploymentVersionId) {
		this.deploymentVersionId = deploymentVersionId;
	}


	public UploadFileRequest deploymentId(Long deploymentId) {
		this.setDeploymentId(deploymentId);
		return this;
	}

	public UploadFileRequest deploymentVersionId(Long deploymentVersionId) {
		this.setDeploymentVersionId(deploymentVersionId);
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
