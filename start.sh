cd registry
./mvnw clean package
cd ..

cd gateway-jwt
./gradlew clean build
cd ..

cd AuthService
./gradlew clean build
cd ..

cd CustomerService
./gradlew clean build
cd ..

cd OrderService
./gradlew clean build
cd ..

cd ProductService
./gradlew clean build
cd ..

cd CartService
./gradlew clean build
cd ..


cd AdminService
./gradlew clean build
cd ..


docker login

docker compose push

docker-compose pull

docker compose down

docker compose up --build



#!/bin/bash

#!/bin/bash

# Define variables
# DOCKERHUB_USERNAME="vulantuong"
# REPOSITORY_NAME="group19"
# TAG="upload"


# services=$(docker-compose config --services)

# for service in $services
# do

#   docker tag ${service} ${DOCKERHUB_USERNAME}/${service}:${TAG}

#   docker push ${DOCKERHUB_USERNAME}/${service}:${TAG}
  
#   docker tag ${DOCKERHUB_USERNAME}/${service}:${TAG} ${DOCKERHUB_USERNAME}/${REPOSITORY_NAME}/${service}:${TAG}

#   docker push ${DOCKERHUB_USERNAME}/${REPOSITORY_NAME}/${service}:${TAG}
# done


