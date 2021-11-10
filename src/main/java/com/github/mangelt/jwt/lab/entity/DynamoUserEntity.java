package com.github.mangelt.jwt.lab.entity;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "OAUHT_USERS")
public class DynamoUserEntity{
	@DynamoDBHashKey
	private String userId;
	@DynamoDBAttribute
    private String name;
	@DynamoDBAttribute
    private String lastName;
	@DynamoDBAttribute
    private String secondName;
	@DynamoDBAttribute
    private String password;
	@DynamoDBAttribute
    private String edge;
	@DynamoDBAttribute
    private Set<String> authGroups;
	@DynamoDBAttribute
	@DynamoDBTyped(DynamoDBAttributeType.BOOL)
    private Boolean isAccountNonExpired;
	@DynamoDBAttribute
	@DynamoDBTyped(DynamoDBAttributeType.BOOL)
    private Boolean isAccountNonLocked;
	@DynamoDBAttribute
	@DynamoDBTyped(DynamoDBAttributeType.BOOL)
    private Boolean isCredentialsNonExpired;
	@DynamoDBAttribute
	@DynamoDBTyped(DynamoDBAttributeType.BOOL)
    private Boolean isEnabled;
}
