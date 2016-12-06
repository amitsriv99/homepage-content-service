# homepage-content-service

Q. How to set-up the webserver and deploy the service?

Ans. The below steps for deploying the service in Tomcat webserver:
> - Go to the /release folder and grab the .war file. 
> - Drop the .war file in the /webapps folder of your webserver
> - Set the following JAVA_OPTS in the webserver catalina.bat/catalina.sh script
     
          -Denviron=prod -Dhomepage.content.repository=D:\datafiles
     
> - Go to the /release folder and grab the homepage-contentmodel.json file. 
> - Drop the homepage-contentmodel.json file in the D:\datafiles folder
> - Start/Restart the webserver

Q. How do I access the service?

Ans. The below RESTful endpoint URLs
> - To check whether the service is in healthy state or not
     
          http://<hostname>:<port>/homepage-content-service-1.0.0/_status

> - To access the content model for the homepage
     
          http://<hostname>:<port>/homepage-content-service-1.0.0/homepage/v1/content-model
          Note: Pass a valid X-OAUTH-TOKEN
