#AVOK

This README describes the overall structure and instructions for building and running the components of the parking app.


##Summary:

The source tree structure will be composed of multiple projects:

- *API*: this is the spring boot container with the API and core engine.
- *web*: this contains the web server (MEAN).
- *firmware*: this will contain the Arduino project code for the firmware running on the Arduino controller.
- *mobile*: this will contain the mobile appication code.
- *deployment*: this will contain the scripts for deployment once a datacenter is setup.


##Setup:

###Basic Setup

*Dependencies:*

- git

*Instructions:*

1. git clone the server repository:
    - create a local directory as WORKSPACE
    - cd WORKSPACE
    - `git clone git@bitbucket.org:zyxparking/core.git`

###API

*Dependencies:*

- JDK 1.8
- mongo 3.x
- maven

*Database configuration:* use the default configuration.
	
*Deployment/install instructions:*
	
1. download and install jdk 1.8. (this differs for windows/mac/linux, search for instructions on the web for your target machine)
2. download and install mongo, maven, git
3. `cd core/API`
4. `mvn clean install`
5. `mvn package`
6. (start mongo) `mongod`
7. `java -jar target/*`, where * is the latest jar build
8. the server should be up and running
9. simple test: http://localhost:8080/listLots to see a list of parking lots

In `src/main/resources/application.properties`, `server.ssl.key-store` points to the keystore, which is currently `keystore.jks`. SSL is currently `disabled`.

###Web Server

*Dependencies:*

- npm (run `brew install npm`)

*Instructions:*

1. `cd core/web`
2. `npm install`
3. `node app`
4. go to http://localhost:3000

###Mobile App

TBD
