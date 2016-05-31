package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.deployment.AppDeploy;
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
 * A request used for uploading files into an Artifact Version. Once all your files are uploaded to the specified {@link ArtifactVersion} object,
 * a deploy call can be created  to push the deployment out to a particular running instance.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     }
 * </pre>
 */
public class UploadFileRequest extends AbstractApiRequest<UploadFileResponse> {
	private Long artifactId;
	private Long artifactVersionId;
	private InputStream inputStream;
	private File file;
	private String contentType;
	private String originalName;
	private String destination;


	@Override
	public UploadFileResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			if(this.getArtifactVersionId() == null) {
				throw new MorpheusApiRequestException("Artifact Version id not specified. This API request requires an artifactVersionId be set.");
			}
			if(this.getArtifactId() == null) {
				throw new MorpheusApiRequestException("Artifact id not specified. This API request requires an artifactId be set.");
			}
			if(this.getDestination() == null) {
				throw new MorpheusApiRequestException("Destination path for file upload not specified");
			}
			if(this.getInputStream() == null) {
				throw new MorpheusApiRequestException("Input Stream or File not specified for upload");
			}

			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			String basePath = "/api/artifacts/" + this.getArtifactId() + "/versions/" + this.getArtifactVersionId() + "/files/";

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

	public Long getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(Long artifactId) {
		this.artifactId = artifactId;
	}

	public Long getArtifactVersionId() {
		return artifactVersionId;
	}

	public void setArtifactVersionId(Long artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
	}


	public UploadFileRequest artifactId(Long artifactId) {
		this.setArtifactId(artifactId);
		return this;
	}

	public UploadFileRequest artifactVersionId(Long artifactVersionId) {
		this.setArtifactVersionId(artifactVersionId);
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
