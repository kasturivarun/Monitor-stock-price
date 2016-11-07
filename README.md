# Monitor-stock-price
Monitor Stock Price is an application to monitor the changes and trend in the stock price of companies. This application exposes Rest apis to perform CRUD operations. There is one more project in repository Monitor-stock-price-ui uses this exposed Rest apis and shows CRUD operations in a web application.

##Design features of the application:

1)	I used Singleton pattern that restricts the instantiation of a class to one object. 

2)	I used MVC architecture which makes code maintenance and reuse easy.

3)	Object relational mapping is used in this application which carries the updates of the model to database.

4)	My application has the advantages of spring such as Dependency Injection and Inversion of control. Therefore whenever a StockObject is created then StockPriceHistoryObject is also created.

5)	Unit test are written using Junit in the application which runs when a new build is kicked off.

##API Usage

| API Route  | GET | POST | Delete |
| ------------- | ------------- | ---------| --------|
| /company/{symbol}  | This request will return historical stock data for {symbol}  | | If {symbol} is present in the database then it will delete the record and return true else it returns false.|
| /company/{symbol}  |   |This request will create a new entry in the database for {symbol}. If {symbol} is already present in the database then it returns false else it will return true| |
| /company|This request will return the list of all companies in database with latest stock price|  |  |

##Pre Conditions:

  •	JDK 1.8
  
  •	MySql server. (You can download workbench also from MySql website which makes visualization easy) (http://dev.mysql.com/downloads/mysql/)
  
  •	Maven 3.0+
  
##Technologies

  •	Java 
  
  •	Maven
  
  •	MySql
  
  •	Spring framework
  
  •	Junit, Mockito
  
  •	Github - https://github.com/kasturivarun/Monitor-stock-price
  
##Build and Deployment
 
 1)	Install Java, Maven, MySql
 
2)	Create a database in MySql.

3)	You can clone the application by,

>$ git clone https://github.com/kasturivarun/Monitor-stock-price.git

4)Change the database details in src/main/resources/application.poperties. The changes should be made to following fields,

>spring.datasource.url=jdbc:mysql://localhost:3306/{database name}
  
>spring.datasource.username={username}
  
>spring.datasource.password={password}

Now, the perquisites are finished.
  
5)	Open command and traverse to project folder.

6)	Type the follwoing command 

>$mvn clean package

This command will create a jar in target folder in project folder.

7)	Traverse to the jar folder and type the following command

>$java –jar {JAR NAME}.jar.

8)	This command will start the server now and you can access the rest API from the browser.


