# Food Truck Locator

A REST API that returns a list of food trucks in the Bay Area given the users coordinates and radius as parameters. This service retreives a list of food trucks from the San Francisco open data API https://data.sfgov.org/resource/rqzj-sfat.json 

## Getting Started

Clone or Download this repository to your local machine

### Prerequisites

To successfully build and run this application, you will need Java JDK 11 (or higher) and Maven installed.

```
java --version
```
### Building and Running

Once you've verified all prerequisites are installed, you can now proceed to build and run the application.

Run the command below in the directory containing the pom.xml file
```
mvn clean compile package
```

Run the application when the build is complete.

```
java -jar target/food-truck-locator-0.0.1-SNAPSHOT.jar
```

## Running the tests

The application runs with an embebed Apache Tomcat server with 8080 as the default port. You can therefore query the endpoint below to get a sorted list of 5 food trucks within a 2 Mile radius of Microsoft San Francisco's Sales office.

```
http://localhost:8080/foodtruck/locate
```
However, you can pass in specific coordinates, radius and limit to get a number of food trucks from a with the specified radius from those coordinates.

Example, here is how you request a list of 13 food trucks within a 1 mile radius of the Microsoft Store in SF (Centre, 865 Market St Westfield, San Francisco, CA 94103)
latitude = 37.78392297731903
longitude = -122.40662797541306
radius = 1.0
limit = 13

```
http://localhost:8080/foodtruck/locate?latitude=37.78392297731903&longitude=-122.40662797541306&radius=1&limit=13
```


### Enjoy those Tacos :)

