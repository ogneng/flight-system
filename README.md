Hi,

This is a quick solution for an airport/flight system. \
I have used gradle build and for running the app just run it from FlightSystemApplication.java \
It has H2 database and the port is 9292 

The link should look something like this: \
http://localhost:9292/airports/saveCSV

The csv data is in **flight-system/src/main/resources/static/** and it is fetched from there, using these two endpoints: \
/airports/saveCSV \
/flights/saveCSV

There are few easy functionalities implmeneted.

P.S. since the two methods for saving CSV are Post mapped and no request params, they might need to be pinged with empty json file {}
