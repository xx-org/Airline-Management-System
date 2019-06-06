#! /bin/bash
DBNAME=jxie031_DB
PORT=8000
USER=jxie031

# Example: source ./run.sh flightDB 5432 user
java -cp lib/*:bin/ DBproject $DBNAME $PORT $USER


