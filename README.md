
#Projet KATA BHE

Le projet comporte les développements réalisés dans le cadre du ing-bank-account-kata.  
Etant donné la taille du projet, j'ai opté pour une organisation technique des package Java.  

##Les développements éffectué:
  
> - Couche persistence (Account, Customer, Transaction);  
> - Services métiers répondant au besoin des USs;  
> - API Rest permettant de consommer les services métiers;  
> - Gestionnaire d'exception pour les erreurs des services;  
> - Swagger décrivant ces apis, accéssible après le lancement du serveur vie le path: /swagger-ui.html;
> - Une base de donnée H2 chargée à chaque lancement de l'application;
> - Gestion du versioning des scripts de BDD à l'aide de "liquibase";
> - Tests unitaires des services métier en utilisant Mockito;
> - Tests d'intégration des controller en utilisant MockMVC;
    
##Comment lancer l'application ?

> - Executer la commande "mvn spring-boot:run"
> - Les informations de BDD chargées par défaut se trouve dans 'resources/db/changelog/script/init-db.sql', à modifier si besoin de plus de comptes ou de client.

