# README #

This REAMD describes the overall structure and instructions for building and running the components of the parking app. 


Summary:

The source tree structure will be composed of multiple projects:

gs-spring-boot: this is the spring boot container has the web server/API and core engine. 
firmware: this will contain the Arduino project code for the firmware running on the Arduino controller. 
mobile-app: this will contain the mobilt appication code.
deployment: this will contain the scripts for deployment once a datacenter is setup.


Setup:

Summary of set up:
Multiple parts need to setup for an end to end user case to work: 
- Download mobile app to a phone. 
- Run the gs-spring-boot as web server. 
- Run the firmware on the Arduino controller

Mobile App download build and configure:

Web Server download, build and configure:
	Dependencies:
		1. JDK 1.7
		2. mongo 3.x

	Database configuration:
	 	use teh default configuration.
	
  	run unit test:
	
	Deployment/install instructions:
	1. download and install jdk 1.7. (this differs for windows/mac/linux, search for instructions on the web for your target machine)
	2. download and install mongo
	3. git clone the server repository:
		- create a local directory as WORKSPACE
		- cd $WORKSPACE
		- git clone git@bitbucket.org:zyxparking/core.git	
	4. cd core/gs-spring-boot/initial
	5. mvn clean install
	6. mvn package
	7. start mongo: mongod --config /usr/local/etc/mongod.conf
	8. java -jar target/*, where * is the latest jar built.
  	9. the server should be up and running	
	10.simple test: http://localhost:8080/list to see a list of parking lots.
		 

