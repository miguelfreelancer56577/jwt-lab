#!/bin/bash

groupName="mtr-$AZ_ENV-grp"
echo "resource group name: $groupName";
az group create --location "$AZ_REGION" \
                --name $groupName;