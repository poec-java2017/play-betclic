# Betclic

## Grunt basics

Grunt is used to generate css[ and js] files in develop mode but also in production mode.\
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

You must initialize your database before loading the project as it is configured to be used with mysql.\
To do so, execute the following commands :
```bash
./docker-mysql-client.sh
create database betclick;
\q
```