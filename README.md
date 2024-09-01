# InvoiceController
The application runs on Spring boot 3.3.2 and Java 17

Import the project to STS or Injtellij and run as spring boot application 

Do maven clean install using command mvn clean install

Build the docker image using command docker build -t my-docker-image .

Once the image is built, you can run it using the docker run command:
docker run -p 8080:8080 my-docker-image
