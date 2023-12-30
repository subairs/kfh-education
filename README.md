# kfh-education - backend

The Kfh-education project could include various functionalities and features related to educational platforms.
The specific scope and features of such a project can vary based on requirements,
but here's a general overview of our project.
	* Created HTTP REST services for various backend functionalities. 
	* Administrators can only access the entire project. 
	
1. Authentication and Authorisation
	* Implementing secure mechanisms for administrators.
	* Role based access control to different part of system.
	* In this project, We have implemented Spring security with JWT(Json Web Token).
		more details: https://jwt.io/, https://spring.io/guides/gs/securing-web/.

		
2. Course Management
	* Creting, managing and organizing courses.
	
3. Student management
	* Creating, managing Students.
	* Enabling Registration of students.

4. Student-Course management

	* Allocate course for registered students
	* Updating course details for students.
	
Technologies Used
* Java 17
* Spring boot
* Maven
* h2 database
* spring Data JPA
* Spring security with JWT

Implemented other functionalities
* Swagger UI
* Slf4j for Logging
* Exception Handling
* Github

Create project using spring initializer (https://start.spring.io/)
	
	1. Select 'Maven' for the project.
	2. Select 'Java' for language.
	3. Select/update Spring boot version (Updated 3.0.4).
	4. Add dependencies for 'web, security, data jpa, h2 ad dev tools1'.
	5. Update group and Artifact.
	6. Select packaging 'Jar' and Java version '17'
	
Maven tools used

mvn package:- Builds and packages the project without installing it in the local repository.
mvn install:- Builds, packages, and installs the project's artifacts into the local Maven repository.

mvn spring-boot:run:- It allows you to run a Spring Boot application directly from the Maven command line without explicitly creating an executable JAR file beforehand.


Example of dev branch working
	* Go to the root folder of the project.
	* Dev branch is used for development.
* Checkout dev branch.	
		Run dev profile project local machine using maven
	
	* Using Terminal/cmd, then run the below command of maven  
		 mvn spring-boot:run -Dspring.profiles.active=dev

Deployee dev profile *.jar file with help of spring profile
	java -jar -Dspring.profiles.active=dev <**.jar file>
	default port is 8080


