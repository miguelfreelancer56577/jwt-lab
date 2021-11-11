login(){
	#login using tenant application.
	echo "Login using tenant application."
	az login \
	    --service-principal \
	    --username $AZ_USERNAME \
	    --tenant $AZ_TENANT \
	    --password $AZ_PASSWORD;
    
    #check if logging was successfully
	if [ $? == 0 ]
	then
		echo "Logged successfully."
		return 0
	else
	    echo "There was an error to log in."
	    return -1
	fi
}
