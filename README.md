### Configuration Management Server

* This project used to manage other application's config properties from multiple storage sources like local, Git, Aws and Google storage.
* The same config server makes sure the configuration properties are in-sync with source systems (local, Git for now) by polling source systems.
* Polling happens every 2 minutes and the poll interval can be changed in application.yml like the Git cloud config repository (https://github.com/sundargsv/cloud-config-samples)


#### Local Storage based solutions

1. Please find the application.yml file and change the default config.type to LOCAL
2. Add application's config files (in .yaml format) into the /local-cloud folder (config file names should follow the pattern {{appName}}-{{environmentName}}.yaml)
3. Start the application either in IDE (ConfigServerApplication.java) as a spring boot app or follow docker commands to run it as a container. 

#### Git Storage based solutions (GitHub)

1. Please find the application.yml file and change the default config.type to GIT
2. Please find the application.yml file and change the default config.git.url to any valid Git repo (assuming. repo to have config files in yaml format.)
3. Add application's config files (in .yaml format) within the GitHub Repo like https://github.com/sundargsv/cloud-config-samples (config file names should follow the pattern {{appName}}-{{environmentName}}.yaml)
4. Start the application either in IDE (ConfigServerApplication.java) as a spring boot app or follow docker commands to run it as a container. 

###### Note: This Git based solution has polling mechanism to get the latest/ updated config from the specified Git repo url

#### Assumptions

* All applications will be using the GET Config API to get the latest app config properties.
* Git based cloud config repository is created in public level for simplicity

#### Improvements

* A dependency can be created as config-client that can be added to the respective application server and the config-client can be leveraged
to get the latest config properties from config-server through GET Config Api or a webHook post can be created in each config-client.

Improvement config-client can be created as a client dependency that can be added to the respective application server

#### Available API and its usages

###### Health Check API

This API used to check whether the config server running and its status

```
http://localhost:8080/config/version
```

######  Config API

This API used to get the updated/ latest config properties for the given application name and environment (dev| test| prod) 

Request Skeleton
```
http://localhost:8080/config/{{applicationName}}?environment={{env}}
```

Example
```
http://localhost:8080/config/app1?environment=dev
```

##### Tech Stacks used

* Spring boot
* Java 11
* Yaml Parser - assuming all our application's config properties in yml format
* Junit & Mockito used for integration test and unit test cases
* Docker - for containerizing the application

Helm can be used for managing the deployments and manifest files while orchestrating the app in Kubernetes

##### Test cases

This project is covered with integration tests and unit tests and which can be easily automated tests in any CI/ CD pipelines later.

```
mvn clean test
```

##### Docker build and Deploy

```
docker build -f docker/Dockerfile -t config-server:latest .
docker run -d -p 8080:8080 config-server:latest
```

##### Helm

Helm can be used for managing the deployments and manifest files while orchestrating the app in Kubernetes.
Please find my base helm config here. (https://github.com/sundargsv/infrastructure-as-code/tree/main/nonprod-k8s/helm-sample)
