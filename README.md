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

Pagina di registrazione
![registration.png](images%2Fregistration.png)

Pagina di conferma della registrazione
![confirm.png](images%2Fconfirm.png)

Pagina di Login
![login.png](images%2Flogin.png)

Attualmente, dopo la registrazione si viene reinderizzati direttamente alla pagina di conferma della registrazione utilizzando il token di attivazione.
Il funzionamento corretto sarebbe che il link per la conferma venisse mandato tramite mail. Ho deciso di cortocircuitarlo solo a scopo di demo.

## Scelte tecniche
Le scelte tecniche sono state descritte nel documento allegato, solution.pdf.

## Organizzazione del codice
Le classi che implementano la soluzione sono raggruppate in package seguendo le direttive BCE.
Ogni i package sono identificati in base al contesto che racchiudono e contengono tutte le classe necessarie per quel contesto.
Ad esempio: il package <b>users</b> contiene tutte le classi necessarie per il contesto legato agli utenti, mentre il package
<b>userregistration</b> contiene tutte le classi necessarie per effettuare la registrazione dell'utente.
