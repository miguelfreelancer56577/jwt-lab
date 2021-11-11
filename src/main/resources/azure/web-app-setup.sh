#!/bin/bash

. ./login.sh

#declaring variables
appSerName="mtr-$AZ_ENV-app-srv"
webAppName="mtr-$AZ_ENV-web-app"
stackRuntime="JAVA|8-jre8"

isReserved(){

	#checking if the resource already exists
	rs=$(az webapp list --query "[?name=='$webAppName'].name" -o tsv)
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
			echo "Creting web app service."
			az webapp create \
				-g $AZ_RESOURCE_GROUP \
				-p $appSerName \
				-n $webAppName \
				--runtime $stackRuntime
				
		fi
	fi
}

#calling main function
main



