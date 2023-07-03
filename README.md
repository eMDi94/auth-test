# AuthDemo Application

Questa applicazione è un esempio di un semplice sistema di authenticazione.

Per eseguire l'applicazione è possibile usare il file <b>docker-compose-production.yml</b> che contiene tutto il necessario
per buildare ed eseguire l'applicativo. Questo è esposto sulla porta 8080 della macchina host. La pagina di registrazione è 
presente al path <b>/registration/register</b>. La pagina di login è presente al path <b>/users/login</b>.

Nel caso in cui si voglia eseguire l'applicativo da linea di comando, è possibile usare maven. Il comando da usare è
```
./mvnw clean package -Dmaven.test.skip=true
```
Questo produrrà un jar nella cartella target, da cui sarà possibile esguire il comando
```
java -jar app.jar
```

Nel file docker-compose.yml sono presenti tutte le dipendenze del sistema.