# Betclic

## Grunt basics

Grunt is used to generate css[ and js] files in develop mode but also in production mode.<br />
To generate css[ and js] files while editing, execute the following command :
```bash
sudo npm i
grunt dev
```

To generate css[ and js] files for production deployment run this grunt command :
```bash
grunt
```

## Database basics

You must initialize your database before loading the project as it is configured to be used with mysql.<br />
To do so, execute the following commands :
```bash
./docker-mysql-client.sh
create database betclick;
\q
```

## Sonarqube

With [SonarQube](https://hub.docker.com/_/sonarqube/) running on a docker container and [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) installed you can can do code coverage with the following command :
```bash
sonar-scanner
```

## Docker

### Prerequisites

* **[Optional]** Traefik container loaded with [docker-compose.yml](/documentation/docker/traefik/docker-compose.yml)
* Play container [Dokerfile](/documentation/docker/play/Dockerfile) in `/opt/docker/play/`

### Starting the application

To package a complete application with play and mysql-server run the following command :
```bash
docker-compose up [-d]
```

### Stopping the application

```bash
docker-compose down
```