package com.github.mangelt.jwt.lab.config;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.github.mangelt.jwt.lab.util.ApiConstants;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile(ApiConstants.PROFILE_AZURE)
@Slf4j
public class AzureBlobStorageConfig {
	
	@Value("${azure.storage.connection.string}")
	protected String connectionString;

	@Value("${azure.storage.container.name}")
	protected String containerName;
	
	@Value("${azure.storage.table.name}")
	protected String tableName;
	
	@Bean
	protected BlobServiceClient storageClient()
	{
//			return a new client to use blob storage
		log.debug("Setting up azure storage client.");
		return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
	}
	
	@Bean
	@ConditionalOnClass(value = {BlobServiceClient.class})
	protected BlobContainerClient  blobContainer(BlobServiceClient storageClient)
	{
		log.debug("Setting up azure blob client.");
		return storageClient.getBlobContainerClient(containerName);
	}
	
	@Bean
	protected CloudStorageAccount  cloudStorageAccount() throws InvalidKeyException, URISyntaxException {
		return CloudStorageAccount.parse(connectionString);
	}
	
	@Bean
	protected CloudTableClient cloudTableClient(CloudStorageAccount account){
		return account.createCloudTableClient();
	}
	
	@Bean
	protected CloudTable userCloudTable(CloudTableClient cloudTableClient) throws URISyntaxException, StorageException{
		return cloudTableClient.getTableReference(tableName);
	}
	
}
