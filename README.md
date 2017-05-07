#  [![Build Status][travis-image]][travis-url] [![Coverage Status][coveralls-image]][coveralls-url] [![Docker Pulls][dockerpulls-image]][dockerpulls-url]


> Chess Server that will provide restful APIs and a complete Web UI.

## Build and Run from Source

```sh
$ ./mvnw clean package
```

This builds an executable jar file in the target folder.
This jar file can now be started as:

```sh
$ java -jar chess-club-1.0.0.jar
```

The server is then listening on port 8080. This port can be overridden:

```sh
$ java -Dserver.port=8888 -jar chess-club-1.0.0.jar
```

## Run from docker image

### Run on an embedded H2 database (volatile)

The default configuration will start a volatile, in-memory database. This means
that data will only live as long as the container is running.

```

docker run -p 80:80 chesscorp/chess-club

```


### Run on an embedded H2 database (persistent)

The embedded H2 database will allow you to without additional components. All data is stored
on the filesystem in the /data folder of the container.

```

docker run \
    -v /tmp/chess-database:/data \
    -p 80:80 \
    -e dbddl=update \
    chesscorp/chess-club

```

### Run on an external PostgreSQL database

The chess-club application has also a built-in PostgreSQL driver. To use it,
override environment variables:

```

docker run \
    -p 80:80 \
    -e dburl=jdbc:postgresql://127.0.0.1:5432/chess1 \
    -e dbusername=<your database username> \
    -e dbpassword=<your database password> \
    -e dbdatabase=POSTGRESQL \
    -e dbddl=update \
    chesscorp/chess-club

```

## See also

Our in-house automation scripts, docker-compose configurations and other stuff may be found in the [ChessCorp/chess-club-automation](http://github.com/ChessCorp/chess-club-automation/) repository.

## License

MIT © [Yannick Kirschhoffer](http://www.alcibiade.org/)

[travis-image]: https://travis-ci.org/ChessCorp/chess-club.svg?branch=master
[travis-url]: https://travis-ci.org/ChessCorp/chess-club
[coveralls-image]: https://coveralls.io/repos/ChessCorp/chess-club/badge.svg?branch=master&service=github
[coveralls-url]: https://coveralls.io/github/ChessCorp/chess-club?branch=master
[dockerpulls-image]: https://img.shields.io/docker/pulls/chesscorp/chess-club.svg
[dockerpulls-url]: https://hub.docker.com/r/chesscorp/chess-club/
[sonar-coverage-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.chesscorp:chess-club/coverage.svg
[sonar-coverage-url]: https://sonarqube.com/overview/coverage?id=org.chesscorp:chess-club
[sonar-quality-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.chesscorp:chess-club/tech_debt.svg
[sonar-quality-url]: https://sonarqube.com/overview/debt?id=org.chesscorp:chess-club
