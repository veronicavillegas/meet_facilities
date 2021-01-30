# Meet facilities
[Documento Funcional] https://docs.google.com/document/d/1U0OqnWIwEQlvJsDnV7UuBiXoW8u9qiDIQhCDoPpm0CQ/edit?usp=sharing

¿Qué hace?

Assumptions

Notas Importantes
* Sobre las temperaturas del pronóstico del tiempo:
* Sobre la cantidad de cajas de cervezas: 
* Timeouts del rest client:
* Sobre la hora para la que pedís el pronóstico o en la que hacés la meet
* Si la api y la caché no me devuelven el pronóstico del tiempo, voy a suponer que va a ser un día de calor y voy a calcular las cajas de cerveza en base a ese supuesto. Esto, porque siempre preferimos que sobre y no que falte. También podría adicionar de alertar algún monitor por ejemplo Datadog para enterarme de que la api del pronóstico está con problemas.
* Los asistentes tienen que ser 2 o más