# Dependify-The-Dynamic-Dependency-Hub

The idea of this project is that it will be an online repository from which people can download and upload different types of dependencies as per their requirements. It will behave like a marketplace for finding all the available dependencies out there and uploading one if it is not there.
The main components would be downloading an available dependency and uploading a dependency to the central repository. A user can also search the central repository to find the specific dependency. 
The application would be hosted on an AWS server which will provide me with steady infrastructure that will help my development, and also provide me public IP address so that it is accessible to my clients from anywhere. The front end would be hosted on an AWS server, application logic would be hosted on 2 different AWS servers with an application load balancer and the persistence tier would be on a separate AWS server to keep my application fail-safe.
The proposed system’s goal is to create a centralized repository where users can find or upload the latest versions of dependencies that are essential for a particular part of the code to work. For the user, it will make it easier to search in a centralized space instead of going through different links on the internet which can be time-consuming and misleading sometimes. 

# Architecture Diagram

![Architecture_Diagram](Architecture_Diagram.jpeg)

# UML Diagram

![UML_Diagram](UML_Diagram.png)
