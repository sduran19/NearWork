
# NearWork

Proyecto Integrador – Politécnico Colombiano Jaime Isaza Cadavid




## Authors

- Maria Jose Medina
- Marlon Andres Vargas
- Sebastian Duran Zuleta


## Deployment

Como ejecutar el proyecto.

1. Validar la configuracion de cada Microservicio que se encuntra en:
- [NearWork Config](https://github.com/sduran19/NearWorkConfigData)
2. Ejecutar en orden los Microservicios para su correcto funcionamiento:
-  'config-service'    Puerto:9000
-  'eureka-service' Puerto:9010
-  'gateway-service'    Puerto:8080
-  'auth-service'   Puerto:9060
-  'operation-service'  Puerto:9070
3. Para la consulta de los Microservicios desde el gateway es una conexion directa http://localhost:8080/"servicio"/
4. Para conocer la documentacion de cada Microservicio auth-service, operation-service. Se consulta la ruta http://localhost:{puerto}/swagger-ui/index.htm

### Contenedor

Generar las imagenes de Docker de cada Microservicio.

```bash
List all projects under gradle project
$ gradle -q projects
Build and create docker images for all project from parent
$ gradle clean build bootBuildImage
Execute gradle task on specific project from parent
$ gradle clean :service-a:bootRun
$ gradle clean :service-a:build :service-a:bootBuildImage
```

