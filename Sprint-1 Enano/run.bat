REM ejecuta runNano.bat para que se encarge correr el servidor
REM que aloja la pagina
start runNanoWeb.bat

REM Ingresa a la carpeta SimpleRouter y procede a construir el proyecto maven
cd SimpleRouter
mvn package 