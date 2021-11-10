package com.github.mangelt.jwt.lab.entity;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TableStorageUser extends TableServiceEntity{
    private String name;
    private String lastName;
    private String secondName;
    private String password;
    private String edge;
    private String authGroups;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
    public TableStorageUser(String userId) {
    	this.partitionKey = userId;
    	this.rowKey = userId;
    }
}
