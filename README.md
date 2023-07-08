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
![Registration.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2F2b%2Flrpdm5217x507g6b6d9mwp5w0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_1xWNsF%2FScreenshot%202023-07-08%20alle%2018.43.20.png)

Pagina di conferma della registrazione
![ConfirmRegistration.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2F2b%2Flrpdm5217x507g6b6d9mwp5w0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_f5wDAq%2FScreenshot%202023-07-08%20alle%2018.44.36.png)

Pagina di Login
![Screenshot 2023-07-08 alle 18.45.24.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2F2b%2Flrpdm5217x507g6b6d9mwp5w0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_NBrpF4%2FScreenshot%202023-07-08%20alle%2018.45.24.png)

Attualmente, dopo la registrazione si viene reinderizzati direttamente alla pagina di conferma della registrazione utilizzando il token di attivazione.
Il funzionamento corretto sarebbe che il link per la conferma venisse mandato tramite mail. Ho deciso di cortocircuitarlo solo a scopo di demo.

## Scelte tecniche
Le scelte tecniche sono state descritte nel documento allegato.