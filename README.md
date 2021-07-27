# Getting Started.

## v1

run docker command

```shell
docker run -d -p 33061:3306 --name mysql57 -e MYSQL_ROOT_PASSWORD=secret mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

In the container, we must create an schema named todolist.

```shell
mysql -uroot -p
#secret
create schema todolist;
commit;
```
Then run the application. 

## Sagger ui
After run the application, go to swagger documentation: http://localhost:8080/swagger-ui.html

