# java-challenge
 
BUILD INSTRUCTIONS

This was made using the Quarkus environment so the build instructions are as follow.

1) JDK11 needs to be installed.
2) ApacheMaven 3.8.1+
3)   Execute the command script in the java-challenge root folder.	```./mvnw package``` The output should then be placed in the target folder.
4) Execute the command ```java -jar target/quarkus-app/quarkus-run.jar```

The webservice can then by accsed at http://localhost:8080


<h2>Personal Notes</h2>

My objective going into making this service was to keep the calls as simple as possible and not get too muddled up in the details and implementation of the API. To this extent I made the POST requests take on a strict format to eliminate confusion. This is my first time working with the Quarkus environment, previous personal and scholastic projects that I have made in Java were usually created with Jetty and Jersey. I've also done RESTful work in Python with Django/Flask and RESTful work in Golang. I took this as a learning experience to see what working in a more modern Java environment was like. I unfortunaly ran out of time before I had the opportunity to make the data persistent. I did want to do this and tried to write my Services in such a way that I could swap them out for a persistent database. Ideally the plan for this would have been to take advantage of the Hibernate ORM Quarkus supports alongside a Postgres database in a docker container. 


A rough outline on the schema I would have used would by as follows:

```
Product  
-ID  
-Name  
-Description  

Album  
-ID  
-Description  


Image  
-ID  
-Title  
-Description  


AlbumImages  
-ID (OPTIONAL)  
-AlbumID(FK)  
-ImageID (FK)  
```

<h2>API Notes</h2>

Implemented endpoints and how they work:

http://localhost:8080/product 
```
POST  
	A POST request should take on the following format.  
	{  
		"name" : as a string,  
		"description: as a string,  
		"album" : as an integer  
	}  
	Album refers to an ID given to a created album object. If an album object has not been made yet then the POST call gets tossed and a status code 400 is sent.
	Returns the created product if successful with its generated id, else throws a status code 400.
	
GET  
	Returns a list of all products made in json format.
```

http://localhost:8080/product/{id} where id is an id of a product created.  
```
GET 
	Returns a json of the product with that id, gives a status code 404 if not found.
PUT
	A PUT request takes on the following format and updates the product obeying the rules used in POST
	{  
		"name" : as a string,
		"description: as a string,
		"album" : as an integer
	}  
DELETE  
	A DELETE request deletes the created product.  
```

http://localhost:8080/albums  
```
POST  
	A POST request should take on the following format.  
	{  
		"images" : as an int array containing valid id's of made images.  
		"description: as a string,  
	}  
	If an invalid image id is passed in the images array, then entire request gets tossed and a status code 400 is sent.  
	Returns the created album if successful with its generated id, else throws a status code 400.  
GET  
	Returns a list of all the albums made.  
```

http://localhost:8080/albums/{id} where id is an id of an album created.  
```
GET  
	Returns a json of the album with that id, gives a status code 404 if not found.  
PUT  
	A PUT request should take on the following format and updates the album obeying the rules used in POST  
	{  
		"images" : as an int array containing valid id's of made images.  
		"description: as a string,  
	}  
DELETE  
	A DELETE request deletes the created album.  
```

http://localhost:8080/images  
```
POST  
	A POST request should take on the following format.  
	{  
		"albums" : as an int array containing valid id's of albums the image is in.  
		"title: as a string,  
		"description: as a string,  
	}  
	If an invalid album id is passed in the albums array, then entire request gets tossed and a status code 400 is sent.  
	Returns the created album if successful with its generated id, else throws a status code 400.  
```

http://localhost:8080/images/{id} where id is an id of an image created.  
```
GET  
	Returns a json of the image with that id, gives a status code 404 if not found.  
PUT  
	A PUT request should take on the following format and updates the album obeying the rules used in POST  
	{  
		"albums" : as an int array containing valid id's of albums the image is in.  
		"title: as a string,  
		"description: as a string,  
	}  
DELETE  
	A DELETE request deletes the created album.  
```

http://localhost:8080/readable
```
GET
	Returns all the products with their attributes in a more human friendly format.
```
