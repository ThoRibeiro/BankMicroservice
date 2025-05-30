# ActiveMQ Microservices Java Project

Une architecture micro-services pédagogique en **Java 11** et **Spring Boot**, utilisant **ActiveMQ** pour l’échange de messages.

---

## 📖 Description

Ce projet illustre un pipeline de traitement de paiements, découpé en 5 micro-services :

1. **gateway-service**  
   → expose une API REST `/payments`, génère un `paymentId`, publie sur `payment.request`

2. **card-validation-service**  
   → consomme `payment.request`, valide le format et la date d’expiration de la carte, publie  
   • `card.validated` ou  
   • `payment.failed` (avec `errorReason`)

3. **client-bank-service**  
   → consomme `card.validated`, vérifie les fonds en base H2, publie  
   • `funds.validated` ou  
   • `payment.failed` (avec `errorReason`)

4. **merchant-bank-service**  
   → consomme `funds.validated`, crédite le compte marchand en base H2, publie  
   • `payment.processed` ou  
   • `payment.failed` (avec `errorReason`)

5. **notification-service**  
   → consomme `payment.processed` et `payment.failed`, loggue un message final détaillé.

Le module **common** contient :
- `PaymentMessage` (DTO partagé, avec `status` et `errorReason`)
- `JmsConfig` (connexion ActiveMQ, `trustedPackages`, `ErrorHandler`)

---

## 📂 Structure du projet

```
activemq-microservices/              # Parent POM Maven
├── common/                          # DTO & config JMS
├── gateway-service/                 # API REST → JMS
├── card-validation-service/         # Validation carte
├── client-bank-service/             # Vérification fonds
├── merchant-bank-service/           # Traitement marchand
├── notification-service/            # Logs finaux
└── docker-compose.yml               # Broker ActiveMQ
```

---

## 🔧 Prérequis

- Java 21 SDK  
- Maven 3.6+  
- Docker & Docker Compose  
- (Optionnel) Postman ou HTTPie pour tester l’API

---

## 🚀 Démarrage rapide

1. **Lancer ActiveMQ**  
   ```bash
   docker-compose up -d
   ```
2. **Compiler & packager**  
   ```bash
   mvn clean package -DskipTests
   ```
---

## 🛠️ Configuration

- **Broker JMS** : `tcp://localhost:61616` (user/password = `admin`/`admin`)  

---

## 📝 Logs & Traçabilité

- **HTTP** : `CommonsRequestLoggingFilter` (payload, URI)  
- **JMS** :  
  - `ErrorHandler` global pour toutes exceptions non gérées  
  - Logs DEBUG sur réception/envoi (`org.springframework.jms`, `org.apache.activemq`)  
- **Métier** : chaque étape loggue `paymentId`, status, `errorReason`  
- **SQL** : Hibernate SQL (`org.hibernate.SQL: DEBUG`)  

---

## 🔗 Liens utiles

- ActiveMQ ObjectMessage trusted packages :  
  http://activemq.apache.org/objectmessage.html  
- Spring JMS reference :  
  https://docs.spring.io/spring-framework/docs/current/reference/html/jms.html  
- H2 Database console :  
  http://www.h2database.com/html/console.html  

---

## 📝 License

Ce projet est fourni **tel quel** à des fins pédagogiques.
