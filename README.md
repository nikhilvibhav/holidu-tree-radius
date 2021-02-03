# Tree Radius Interview Assignment [![Build Status](https://travis-ci.com/nikhilvibhav/holidu-tree-radius.svg?branch=main)](https://travis-ci.com/nikhilvibhav/holidu-tree-radius)

Hi there! Congratulations on making it to the next step!

You are given a scaffold application based on Spring Boot to save your time, but you are free to use any other frameworks if you would like to.

Your task is to implement a specific feature as a REST service that uses some 3rd party API.
A service should make an aggregated search of trees (plants, not binary trees) in the circle.

Input:
  - X and Y of the cartesian center point
  - A search radius in meters

Output:
  - Aggregation by "common name" (please see in the documentation on the same page above which field to refer to)

Example of the expected output:
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```

The service should use the data from the 3rd party API (https://data.cityofnewyork.us/Environment/2015-Street-Tree-Census-Tree-Data/uvpi-gqnh): `https://data.cityofnewyork.us/resource/nwxe-4ae8.json`

If you happen to have any questions, please send an email to your HR contact at Holidu.

Good luck and happy coding!

## Build steps

#### Pre-requisites:
* You should have Java 11 installed on your computer

To build and run the tests, run the following command -

* `./mvnw clean verify` OR `mvnw.cmd clean verify` depending on whether you're using Linux/Mac or Windows.

To run the application via command line, run -

* `./mvnw spring-boot:run` OR `mvnw.cmd spring-boot:run`

## Testing

You can hit the endpoint - `GET` http://localhost:8080/holidu/tree-census/v1/aggregate/common-name?xCoordinate=1021900&yCoordinate=208600&radius=20000

You should get a response with the aggregated count of trees by their common names

OR, you can also import the postman collection under `src/test/resources/postman` and test the API via Postman
