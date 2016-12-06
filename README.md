# homepage-content-service

Q. How to set-up the .war file in the webserver?

Ans. The below steps for deploying the service in Tomcat webserver:
> - Go to the /release folder and grab the .war file. 
> - Drop the .war file in the webapps folder in your webserver
> - Set the following JAVA_OPTS in the webserver start-up scripts
     
     -Denviron=prod -Dhomepage.content.repository=D:\datafiles
     
> - Go to the /release folder and grab the homepage-contentmodel.json file. 
> - Drop the homepage-contentmodel.json file in the D:\datafiles folder
> - Start the webserver

Q. How do I access the service?

Ans. The below RESTful endpoint URLs
> - To check whether the service is in healthy state or not
     
          http://<hostname>:<port>/homepage-content-service-1.0.0/_status

> - To access the content model for the homepage
     
          http://<hostname>:<port>/homepage-content-service-1.0.0/homepage/v1/content-model
          Note: Pass a valid X-OAUTH-TOKEN
