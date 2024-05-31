# TFG DAW Víctor Paredes Sala

Factura Express, una aplicación para gestionar facturas y poder generarlas. Además puedes gestionar usuarios para que varias personas puedan gestionar facturas para ti o tu empresa.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Factura Express, a web app to manage your business invoices and generate them. Also you can manage users so some people can manage the invoices for you and your business.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Como utilizarlo/How to use it

Es bastante sencillo, simplemente descarga el repositorio en .zip o clonalo. A continuación asegurate de tener Angular 16 o superior, NodeJs 18.16.0 o superior, npm 9.5.1 o superior, Java JDK 17.
Lo siguiente será realizar el comando ng serve desde la carpeta front en la terminal de comandos (cmd), abrir la carpeta back con el IDE que utilicemos y ejecutar el archivo BackApplication.java.
En caso de desplegar en la nube, cambiar la variable apiUrl at the front/src/environment.ts por la url de nuestro back en la nube. En el back cambiar la url de la linea 60 de config/SecurityConfig.java por la url de nuestro front en la nube.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

It's quite simple, just download the .zip of the project or clone it form GitHub. Next we are ensuring that we have Angular 16 or upper version, NodeJs 18.16.0 or upper version, npm 9.5.1 or upper version, Java JDK 17.
Then we are going to execute the command ng serve on the front directory at cmd, open the back directory on your IDE, remember to import the maven dependencies. Then execute the BackApplication.java file.
If you are uploading the project to the cloud remember to change the apiUrl variable at front/src/environment.ts for the url where you have the back. At the back remember to change the url on line 60 of config/SecurityConfig.java by the url of your front at the cloud.
