# SimuladorAeropuerto
Práctica de programación avanzada basada en la simulador de un aeropuerto donde se hará uso de la programación concurrente y distribuida. El enunciado de la práctica es el siguiente: 

Simulación del funcionamiento de aeropuertos
Parte 1: Programación Concurrente
Se desea modelar el comportamiento de los aeropuertos de Madrid y Barcelona, y la conexión
entre ambos aeropuertos. Se tienen las siguientes características generales:
 El sistema cuenta con 2 aeropuertos.
 La capacidad máxima de pasajeros en los aeropuertos es ilimitada.
 Cada aeropuerto cuenta con varias zonas de actividad para los aviones: HANGAR,
TALLER, PUERTAS DE EMBARQUE, PISTAS, ÁREA DE ESTACIONAMIENTO y
ÁREA DE RODAJE.
 Se dispone de 2 aerovías que conectan ambos aeropuertos. Una para cada sentido. La
capacidad de ambas aerovías es ilimitada.
 Cada aeropuerto dispone de autobuses que conectan la ciudad con el aeropuerto (y
viceversa), para llevar y traer pasajeros.
 Los autobuses y los aviones deberán ser modelados obligatoriamente mediante hilos.
 Las personas NO serán consideradas como hilos.
 El número de aviones que el sistema genera es de 8.000. Para evitar congestión, los aviones
serán creados de forma escalonada, en intervalos aleatorios de entre 1 y 3 segundos.
 El número de autobuses que el sistema genera es de 4.000. Para evitar congestión, los
autobuses serán creados de forma escalonada, en intervalos aleatorios de entre 0,5 y 1
segundo.
 La generación de aviones y autobuses al comienzo de la ejecución del programa debe
realizarse de forma concurrente, es decir, de forma simultánea.
Autobuses
Los autobuses deben ser modelados como hilos y se identificarán como “B-XXXX”, donde XXXX
es un número (id) único como, por ejemplo, B-0001, B-0023, B-1234, etc. Se encargan de llevar y
traer pasajeros entre la ciudad correspondiente y el aeropuerto de dicha ciudad.

Los autobuses con identificador par (solo la parte numérica) serán generados para el aeropuerto
de Madrid. De forma análoga, los autobuses con identificador impar (solo la parte numérica) serán
generados para el aeropuerto de Barcelona.
Los autobuses tendrán el siguiente ciclo de vida, que será repetido infinitamente:
 Llegada a la parada del centro de la ciudad, donde se detiene, para esperar que suban los
pasajeros, durante un tiempo aleatorio entre 2 y 5 segundos.
 Se monta en el autobús un número aleatorio entre 0 y 50 pasajeros.
 El autobús inicia su marcha en dirección al aeropuerto. Dicho trayecto le llevará un tiempo
aleatorio entre 5 y 10 segundos.
 Llegada a la parada del aeropuerto, donde los pasajeros entran al aeropuerto. En este
momento, los pasajeros son incorporados al sistema del aeropuerto.
 El autobús espera a que suban nuevos pasajeros durante un tiempo aleatorio entre 2 y 5
segundos.
 Se montan en el autobús un número aleatorio entre 0 y 50 pasajeros. Este número de
pasajeros son restados del sistema del aeropuerto.
 El autobús inicia su marcha en dirección al centro de la ciudad. Dicho trayecto le llevará
un tiempo aleatorio entre 5 y 10 segundos.
 Llegada a la parada del centro de la ciudad, donde bajarán los pasajeros. En este momento,
los pasajeros dejarán de contar para el sistema.
Aviones
Los aviones deben ser modelados como hilos y se identificarán como “YY-XXXX”, donde XXXX
es un número (id) único, e YY son 2 caracteres alfabéticos al azar (cada carácter en el rango A-Z).
Por ejemplo, AC-0001, BF-0023, ER-1234, etc. Los aviones se encargan de llevar y traer pasajeros
entre los 2 aeropuertos disponibles en el sistema.
Los aviones con identificador par (solo la parte numérica) serán generados para el aeropuerto de
Madrid. De forma análoga, los aviones con identificador impar (solo la parte numérica) serán
generados para el aeropuerto de Barcelona.
Los aviones se generan con una capacidad máxima aleatoria entre 100 y 300 pasajeros.
Los aviones tendrán el siguiente ciclo de vida, que será repetido infinitamente:
 El avión, cuando se genera, siempre aparece en el HANGAR.
 El avión accede al ÁREA DE ESTACIONAMIENTO, donde esperará a que una de las
PUERTAS DE EMBARQUE esté disponible (estrategia FIFO).
 Una vez en la PUERTA DE EMBARQUE, procede a embarcar pasajeros. Intentará coger
la capacidad máxima del avión (máximo número de pasajeros posible). Si no hay
suficientes pasajeros en el aeropuerto, cogerá los que haya disponibles; y espera un tiempo
aleatorio (1-5 segundos), antes de volver a admitir más pasajeros. Cada transferencia de
pasajeros al avión dura un tiempo aleatorio entre 1 y 3 segundos. El proceso de embarque
sin capacidad máxima se intentará un máximo de 3 veces. Si, pasado ese tiempo, no se
completa el aforo del avión, la puerta de embarque cierra y no se admiten más pasajeros.
El avión está listo para irse.

 El avión, una vez abandonada la PUERTA DE EMBARQUE, accede directamente al
ÁREA DE RODAJE, donde esperará a tener una PISTA para despegar. Antes de solicitar
pista, los pilotos realizan una serie de comprobaciones durante un tiempo aleatorio entre 1
y 5 segundos.
 Cuando el avión accede a una PISTA, realiza unas últimas verificaciones (tiempo aleatorio
entre 1 y 3 segundos) y procede a despegar. El proceso de despegue dura entre 1 y 5
segundos. Todos los despegues son exitosos.
 El avión, tras despegar, accede a la AEROVÍA que conecte al aeropuerto de destino.
 El vuelo dura un tiempo aleatorio entre 15 y 30 segundos.
 El avión solicita una PISTA para el aterrizaje. Si no hay pista disponible, da un rodeo al
aeropuerto (tiempo aleatorio entre 1 y 5 segundos), y lo vuelve a intentar. Repetirá esta
operación hasta que consiga aterrizar.
 Cuando el avión accede a la PISTA, el proceso de aterrizaje dura entre 1 y 5 segundos.
Todos los aterrizajes son exitosos.
 El avión, una vez abandonada la PISTA, accede directamente al ÁREA DE RODAJE,
donde solicita una PUERTA DE EMBARQUE y esperará hasta tener una asignada para
desembarcar a los pasajeros. El camino, ya en el ÁREA DE RODAJE, entre la pista y las
puertas de embarque dura un tiempo aleatorio entre 3 y 5 segundos.
 Una vez en la PUERTA DE EMBARQUE, procede a desembarcar pasajeros. La
transferencia de pasajeros al aeropuerto dura un tiempo aleatorio entre 1 y 5 segundos.
 Una vez completado el desembarque, el avión accede al ÁREA DE
ESTACIONAMIENTO, donde los pilotos realizan una serie de comprobaciones durante
un tiempo aleatorio entre 1 y 5 segundos.
 Cuando un avión haya realizado 15 vuelos deberá realizar una inspección en profundidad
en el TALLER. Si no pudiera acceder (por haber alcanzado la capacidad máxima de
aviones dentro del TALLER), se pone en cola hasta que sea su turno (estrategia FIFO). La
inspección del avión dura un tiempo aleatorio entre 5 y 10 segundos. La puerta del taller
es estrecha, y solo puede usarse por 1 avión a la vez (independientemente del sentido).
Cada avión tarda 1 segundo en atravesar la puerta del TALLER.
o Aunque al avión no tenga que realizar una inspección en profundidad, debe pasar
al TALLER para una rápida revisión antes de continuar. Esta inspección dura un
tiempo aleatorio entre 1 y 5 segundos. El acceso al TALLER (cola de acceso, puerta
estrecha y tiempo en atravesarla) es igual que para acceder a una inspección en
profundidad. La única diferencia con respecto a la inspección en profundidad es el
tiempo de la revisión.
 El avión, aleatoriamente, decidirá entre ir al HANGAR a reposar (50% de probabilidad de
que esto suceda) o continuar con el ciclo de vida (50% de probabilidad). En caso de ir al
HANGAR, permanecerá allí durante un tiempo aleatorio entre 15 y 30 segundos. Si
continúa con el ciclo de vida, estará listo para ir al ÁREA DE ESTACIONAMIENTO y
continuar con el proceso.

Zonas de actividad disponibles en los aeropuertos
A continuación, se muestran detalles técnicos sobre las zonas en las que los aviones realizan
actividades en el aeropuerto:
 Hangar:
o Cada aeropuerto dispone de 1 de estas áreas.
o Capacidad ilimitada de aviones.
 Taller:
o Cada aeropuerto dispone de 1 de estas áreas.
o Capacidad para 20 aviones de forma simultánea.
 Puertas de Embarque:
o Cada aeropuerto dispone de 6 puertas de embarque/desembarque. De las 6 puertas,
1 es exclusiva para embarques, 1 es exclusiva para desembarques, y las 4 restantes
son libres y se pueden usar indistintamente para ambos procesos.
o Solo puede acceder 1 avión de forma simultánea a una puerta de embarque.
 Pistas:
o Cada aeropuerto dispone de 4 pistas de despegue/aterrizaje.
o Solo puede acceder 1 avión de forma simultánea a una pista.
o Las pistas disponibles pueden abrirse o cerrarse al uso manualmente. Ver parte 2
(Programación Distribuida) para más detalles.
 Área De Estacionamiento:
o Cada aeropuerto dispone de 1 de estas áreas.
o Capacidad ilimitada de aviones.
 Área De Rodaje:
o Cada aeropuerto dispone de 1 de estas áreas.
o Capacidad ilimitada de aviones.
 Aerovías:
o Existen en el sistema únicamente 2 aerovías, que sirven de conexión entre los
aeropuertos disponibles.
o Capacidad ilimitada de aviones.
Otras Consideraciones
Todo el comportamiento del sistema se guardará en un fichero de log (un fichero de texto llamado
“evolucionAeropuerto.txt”), de forma que sea sencillo analizar lo sucedido. El log guardará todos los
eventos que van teniendo lugar, por ejemplo: “Avión AC-1423 es creado”, “Avión ZZ-1553 (257
pasajeros) accede a Pista 1 para Despegue”, “Bus B-003 deja 34 pasajeros en el aeropuerto”, “Avión
RT-6543 (87 pasajeros) accede a aerovía Madrid-Barcelona”, “Avión PR-4563 accede a Puerta de
Embarque 3 para desembarcar 157 pasajeros.” etc. En cada línea de dicho log deberá constar la
marca de tiempo (fecha y hora, incluyendo el segundo determinado en el que tuvo lugar el evento),
el aeropuerto donde surge el evento y el evento en sí. El sistema de log deberá implementarse como
un recurso compartido a utilizar por todo el sistema concurrente y deberá protegerse adecuadamente.

Todo el comportamiento del sistema se mostrará gráficamente por pantalla. Además, se deberá
incluir un botón para pausar/reanudar el sistema, de forma que sea sencillo hacer el seguimiento
de la ejecución del programa.
