#  Match Time Converter
This project converts a String representing match time in one format into a String representing match time in another format.
The input format is:

    [period] minutes:seconds.milliseconds
   The output format is 
   

    normalTimeMinutes:normalTimeSeconds - period
   Where `[period]` is the short form version of `period`.

## Running the project
The converter is written in scala and can be run using [SBT](https://www.scala-sbt.org/). 
To run the converter with custom input run the following command from the project root:

    sbt 'run "<input_format>"'
  For example:
  

    sbt 'run "[HT] 45:00.500"'
 The output will be printed to the console.  If the input is not in the correct format then `"INVALID"` will be printed to the console. There will also be some error logging from the application.

The source code can all be found in `match-time/src/main/scala/com/iain`.

## Running the tests
There are 3 sets of unit tests that can be found in `match-time/src/test/scala/com/iain/timeConverter`. These can also be run using SBT.
To run all the tests run the following command from the project root:

    sbt test

Tests can also be run individually. There is a unit test in `com.iain.timeConverter.TimeConverterTest` that tests the entire convert function. You can add cases to this test to check input and output easily.
To run it run the following from the root of the project:

    sbt "testOnly com.iain.timeConverter.TimeConverterTest -- -z Convert"
    

