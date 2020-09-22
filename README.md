# Administrador de tareas  

Aplicación para gestionar tareas para un equipo o una pequeña empresa 

  
## Caracteristicas:
La mayoría de las funciones requieren iniciar sesión 
Los usuarios no autorizados tienen acceso a la pantalla de bienvenida y al panel de inicio de sesión o registro
	
**Admin (manager) puede:**
-	Cree tareas y asigne tareas a cualquier usuario
-	Ver lista de todos los usuarios con posibilidad de eliminar usuarios
-	Ver lista de todas las tareas con la edición o eliminación de tareas
-	Cambiar tarea como completada / iniciada / pendiente
	
**Usuario comun puede:**
-	Crear tarea solo para él
-	Ver lista de todos los usuarios sin acción permitida
-	Ver la lista de todas las tareas, pero editar o eliminar solo las tareas de las que es responsable
-	Cambiar la tarea de propiedad como completada / iniciada
	
**Todo usuario autorizado puede:** 
-	Ver su propio perfil


## Construido con
* Spring Boot
* Spring Security
* H2 database
* Maven 
* Thymeleaf
* Bootstrap
* jQuery

## Usuarios test
Pegue el correo electrónico y la contraseña en el formulario de inicio de sesión o haga clic en uno de los botones de demostración debajo del formulario de inicio de sesión para insertarlos rápidamente:

`manager@mail.com`  password: `112233`  
`diana@mail.com`  password: `diana`  
`mizaelm@mail.com`  password: `mizaelm`


## Como corre el proyecto 

git clone https://github.com/mizaelms/admin-tareas.git

cd admin-tareas

mvn clean spring-boot:run
  
  