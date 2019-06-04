#! /bin/bash
DBNAME=xwang296_DB
PORT=8000
USER=xwang296

# Example: source ./run.sh flightDB 5432 user
java -cp lib/*:bin/ DBproject $DBNAME $PORT $USER


