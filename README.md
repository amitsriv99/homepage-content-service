# homepage-content-service

Q. How to set-up the .war file in the webserver

Ans. The below steps for Tomcat webserver:
> - Go to the /release folder and grab the .war file. 
> - Drop the .war file in the webapps folder in your webserver
> - Set the following JAVA_OPTS in the webserver start-up scripts
        -Denviron=prod -Dhomepage.content.repository=D:\datafiles
> - Go to the /release folder and grab the homepage-contentmodel.json file. 
> - Drop the homepage-contentmodel.json file in the D:\datafiles folder
> - Start the webserver
