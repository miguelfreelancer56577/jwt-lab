package com.github.mangelt.jwt.lab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.github.mangelt.jwt.lab.util.ApiConstants;

@Configuration
@Profile(ApiConstants.PROFILE_AWS)
public class AwsDynamoDBConfig {
	
	protected final String tableNameOauth;
	
	public AwsDynamoDBConfig(
			@Value("${app.config.aws.dynamodb.table.name.oauth}") String tableNameOauth) {
		this.tableNameOauth = tableNameOauth;
	}
	
	@Bean
	protected AmazonDynamoDB dynamoDbClient(AWSCredentials credentials) {
		final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
		return AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(awsStaticCredentialsProvider)
			.build();
	}
	
	@Bean
	protected DynamoDB dynamoDB(AmazonDynamoDB client) {
		return new DynamoDB(client);
	}
	
	@Bean
	protected Table oauthUserTable(DynamoDB dynamoDB) {
		return dynamoDB.getTable(tableNameOauth);
	}
	
	@Bean
	protected DynamoDBMapper dynamoDBMapper(AmazonDynamoDB client) {
		return new DynamoDBMapper(client);
	}
	
}
