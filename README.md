# Proyecto Backend con Arquitectura de Microservicios

## Integrantes

| GitHub       | Nombre        |
| ------------ | ------------- |
| znatan13     | Matías Durán  |
| manolitoking | Manuel Mora   |
| Viejas6590   | Bastián Muñoz |


* Antes de utilizar el sistema, es necesario iniciar todos los microservicios que componen la aplicación.
* Cada microservicio debe contar con su base de datos configurada y disponible.
* Esta versión se encuentra enfocada en la implementación y comunicación entre microservicios.
* Por el momento, no se realiza validación ni verificación de roles o permisos de usuario. Dichas funcionalidades serán consideradas en futuras versiones del proyecto.

*El sistema puede auto registrarse el Eureka Server por lo cual Implmenta Api Gateway, para aumentar la rapidez al momento de probar el sistema, gracias al enrutamiento de gateway.

*A continuacion url:

*Eureka Server : http://localhost:8761 -> Url que dara ascceso a la consola de eureka y visualizar los microservicios registrados

*Api Gateway : http://localhost:8060 -> Url que dara frutos en ejecutar en postman permitiendo probar los microservicios 1 por 1 , sin la necesidad de aprenderse sus puertos.

*Pruebas unitarias : Se aplico pruebas unitarias en cada uno de los microservicios testeando y evintando futuros errores. ademas mejora la calidad del codigo.

* Por ultimo se aplico OpenApi (Swagger) para dar una mejor calidad al sistema, documentando cada edpoints, por si necesita ser visualizado de mejor forma sin la necesidad de revisar codigo fuente.

*URL ejemplo: http://localhost8080/swagger-ui/index.html. si usted ejecuta esta url en su navegador podra visualizar todos los edpoints documentados y podra probar edpoint en tiempo real correspondiente de microservicios autenticacion.
