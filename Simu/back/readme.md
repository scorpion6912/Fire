## Commande Docker pour lancer les containers (à la racine du projet)
docker compose build && docker compose up -d

## Pour générer le .jar du projet (à la racine du back)
./mvnw clean package -DskipTests