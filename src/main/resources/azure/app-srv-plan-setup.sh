#!/bin/bash

. ./login.sh

#declaring variables
appSerName="mtr-$AZ_ENV-app-srv"

isReserved(){

	#checking if the resource already exists
	rs=$(az appservice plan list --query "[?name=='$appSerName'].reserved" -o tsv)
	#checking if the result is not null and is reserved
	[ ! -z "$rs" ] && rs="true" || rs="false"
	if [ $rs == 'true' ]; then
		echo "The resource already exists."
		return 0
	else
		echo "The resource doesnt exist."
		return 1
	fi
}

main(){

	#calling loging function.
	login
	#loging was successfully
	if [ $? == 0 ]; then
		#checking if the resource already exists
		isReserved
		if [ $? == 1 ]; then
			echo "Creting app service plan"
			az appservice plan create \
				--name $appSerName \
				--resource-group $AZ_RESOURCE_GROUP \
				--location "$AZ_REGION" \
				--number-of-workers 1 \
				--sku B1 \
				--is-linux \
				--tags [enviroment=$AZ_ENV]
				
		fi
	fi
}

#calling main function
main



