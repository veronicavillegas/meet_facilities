# Meet facilities
[Documento Funcional] (https://docs.google.com/document/d/1U0OqnWIwEQlvJsDnV7UuBiXoW8u9qiDIQhCDoPpm0CQ/edit?usp=sharing)

* Proyecto en java 8 con Spring

## ¿Qué hace?
* Calculo la cantidad de cajas de cervezas que necesitás para tu meetup según la cantidad de asistentes el clima para ese día y las cervezas por caja.
* Proveo el pronóstico para una fecha específica.

## ¿Qué no hace?
* No calculo cajas de cerveza ni el clima en fechas anteriores a la actual.


## Seguridad
* Debes mandar atributo en el header "x-auth-token": "user" o "admin_admin" para indicar de quién proviene el request. Es un campo requerido, sin el mismo, no podrás hacer peticiones.

## Notas Importantes
* Sobre las temperaturas del pronóstico del tiempo: Si la solicitud del pronóstico o la meet es antes de del medio día, tomo la temperatura de ese día a las 12 PM. Si la solicitud es sobre un horario luego de las 12, la temperatura que tomo es la de ese día a las 15hs.
* Sobre la cantidad de cajas de cervezas: Siempre el redondeo del resultado es para arriba.
* Timeouts del rest client: Establezco 10seg. de read timeout y de connection timeout y 3 reintentos.
* Si la api y la caché no me devuelven el pronóstico del tiempo, voy a suponer que va a ser un día de calor y voy a calcular las cajas de cerveza en base a ese supuesto. Esto, porque siempre preferimos que sobre y no que falte. También podría adicionar de alertar algún monitor por ejemplo Datadog para enterarme de que la api del pronóstico está con problemas.
* Los asistentes tienen que ser 2 o más.
* Por cuestiones de tiempo, no estoy validando match entre city y country.
* La fecha y hora para pedir el pronostico o calcular cervezas debe ser superior a la actual
* Circuit breaker: Con Hystrix.
* Unit tests con JUnit.
* Link documentación [Documentacion](http://localhost:8080/swagger-ui.html)