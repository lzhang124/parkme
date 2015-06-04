#AVOK

This README describes the overall structure and instructions for building and running the components of the parking app.


##Summary:

The source tree structure will be composed of multiple projects:

- *API*: this is the spring boot container with the API and core engine.
- *web*: this contains the web server (MEAN).
- *firmware*: this will contain the Arduino project code for the firmware running on the Arduino controller.
- *mobile*: this will contain the mobilt appication code.
- *deployment*: this will contain the scripts for deployment once a datacenter is setup.


##Setup:

Multiple parts need to setup for an end to end use case to work:

- Download mobile app to a phone.
- Run the application as web server.
- Run the firmware on the Arduino controller.

###Mobile App

TBD

###Web Server

*Dependencies:*

1. JDK 1.7
2. mongo 3.x
3. maven
4. git

*Database configuration:* use the default configuration.
	
*Deployment/install instructions:*
	
1. download and install jdk 1.7. (this differs for windows/mac/linux, search for instructions on the web for your target machine)
2. download and install mongo, maven, git
3. git clone the server repository:
	- create a local directory as WORKSPACE
	- cd $WORKSPACE
	- git clone git@bitbucket.org:zyxparking/core.git	
4. cd core/API
5. mvn clean install
6. mvn package
7. (start mongo) mongod
8. java -jar target/*, where * is the latest jar built.
9. the server should be up and running.
10.simple test: http://localhost:8080/list to see a list of parking lots.
