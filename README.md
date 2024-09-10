# Local Project Setup

## Requirements

Mandatory requirements:

* Java 21 (OpenJDK). 
```
$ java -version
openjdk version "21.0.4" 2024-07-16
OpenJDK Runtime Environment (build 21.0.4+7-Ubuntu-1ubuntu222.04)
OpenJDK 64-Bit Server VM (build 21.0.4+7-Ubuntu-1ubuntu222.04, mixed mode, sharing)

```

* Docker Engine (Community Edition is enough) and Docker Compose:

```
$ docker version
Client: Docker Engine - Community
 Version:           27.2.0
 API version:       1.47
 Go version:        go1.21.13
 Git commit:        3ab4256
 Built:             Tue Aug 27 14:15:13 2024
 OS/Arch:           linux/amd64
 Context:           default

Server: Docker Engine - Community
 Engine:
  Version:          27.2.0
  API version:      1.47 (minimum version 1.24)
  Go version:       go1.21.13
  Git commit:       3ab5c7d
  Built:            Tue Aug 27 14:15:13 2024
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.7.21
  GitCommit:        472731909fa34bd7bc9c087e4c27943f9835f111
 runc:
  Version:          1.1.13
  GitCommit:        v1.1.13-0-g58aa920
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0


$ docker-compose version
ddocker-compose version 1.29.2, build unknown
docker-py version: 5.0.3
CPython version: 3.10.12
OpenSSL version: OpenSSL 3.0.2 15 Mar 2022
```

Optional requirements:

* Maven >= 3.6 (the project also includes the Maven Wrapper).

```
$ ./mvnw -version
Apache Maven 3.9.7 (8b094c9513efc1b9ce2d952b3b9c8eaedaf8cbf0)
Maven home: /home/***/.m2/wrapper/dists/apache-maven-3.9.7/2a4cb831
Java version: 21.0.4, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
Default locale: pl_PL, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-119-generic", arch: "amd64", family: "unix"
```

## Running the Project Locally

Assuming your local setups meets all requirements as stated above, you can now start the application:

1. Make sure your Docker Engine is up- and running command
    - run command build Docker image:
   ```shell
    ./mvnw -DskipTests=true spring-boot:build-image
   ```
   - Start the required infrastructure components with
3. ```shell
    docker-compose up
   ```
   
## Running the Tests command
```json
    curl -X POST http://localhost:8080/api/companies \
-H "Content-Type: application/json" \
-d '{
  "name": "Firma1",
  "departments": [
    {
      "name": "IT",
      "teams": [
        {
          "name": "Zaspol 1",
          "projects": [{
            "name": "Projekt",
            "manager": {
              "nameManager": "Jacek",
              "contactInfo": "+123456789"
            }
          }]
        }
      ]
    }
  ]
}' -s | jq .
   ```
