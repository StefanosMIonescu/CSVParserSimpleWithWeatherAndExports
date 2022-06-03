import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.*;
import java.io.*;
public class WeatherExport {
    public CSVRecord hottestHourInFile(CSVParser parser) {

        // start with largestSoFar as nothing ="null"
        CSVRecord largestSoFar = null;

        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            //If largestSoFar is nothing
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);

        }
        return largestSoFar;
    }

    public void testHottestInDay() {

        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("The Hottest temperature in Fahrenheit Degrees was : "
                + largest.get("TemperatureF") + " at " + largest.get("TimeEST"));

    }

    public CSVRecord hottestInManyDays() {
        //DirectorResource allows to select any number of files in directory to compare!
        //Create a tempHotestSoFar = null
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();

        // iterate over files
        // the return tipe is File and the runner is f!!! the table element is dr.selectedFiles()!
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);

            //user method to get largest in file
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);

        }

        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord largestSoFar) {

        if (largestSoFar == null) {
            largestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow's temperature > largestSoFar

            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }

        }

        //The largestSoFar is the answer
        return largestSoFar;
    }

    public void testHottestInManyDays() {

        CSVRecord largest = hottestInManyDays();

        System.out.println("The hottest temperature in Fahrenheit Degrees was : "
                + largest.get("TemperatureF") + " on " + largest.get("DateUTC")
                + "(UTC)");

    }

    public CSVRecord getColdestOfTwo(CSVRecord currentRow, CSVRecord coldestSoFar) {

        if (coldestSoFar == null) {

            coldestSoFar = currentRow;

        } else {

            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));

            double tempColdestSoFar = Double.parseDouble(coldestSoFar.get("TemperatureF"));

            if (currentTemp != -9999) {

                if (currentTemp < tempColdestSoFar) {

                    coldestSoFar = currentRow;
                }
            }

        }
        return coldestSoFar;
    }

    public CSVRecord coldestHourInFile(CSVParser parser) {

        CSVRecord coldestSoFar = null;

        for (CSVRecord currentRow : parser) {

            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);

        }

        return coldestSoFar;

    }

    public void testColdestInDay() {

        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("The Coldest temperature in Fahrenheit Degrees was : "
                + coldest.get("TemperatureF") + " at " + coldest.get("TimeEST"));

    }

    public CSVRecord coldestInManyDays() {

        CSVRecord coldestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        //to iterate over files has retrun type File!

        for (File f : dr.selectedFiles()) {

            FileResource fr = new FileResource(f);
            //use method to get smallest temp in File

            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());

            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
        }

        return coldestSoFar;
    }

    public void testColdestInManyDays() {

        CSVRecord smallest = coldestInManyDays();

        System.out.println("The Coldest Temperature was "
                + smallest.get("TemperatureF")
                + " at " + smallest.get("TimeEST"));

    }

    public boolean checkValidHumidity(CSVRecord currentRow) {

        String testCurrentHumidity = null;

        boolean result = true;

        //check valid humidity
        testCurrentHumidity = currentRow.get("Humidity");
        // if not valid use this
        if ("N/A".equals(testCurrentHumidity)) {

            System.out.println("The Value of the Humidity was not valid and is beeing ignored as an error!");

            result = false;

        } //use this

        return result;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser) {

        CSVRecord lowestHumiditySoFar = null;

        for (CSVRecord currentRow : parser) {

            lowestHumiditySoFar = getLowestHumidity(currentRow, lowestHumiditySoFar);

        }

        return lowestHumiditySoFar;

    }

    public CSVRecord getHighestHumidity(CSVRecord currentRow, CSVRecord highestHumidSoFar) {

        String testCurrentHumidity = null;

        if (highestHumidSoFar == null) {

            highestHumidSoFar = currentRow;

        } else {
            //check for iregularities in Humidity

            if (checkValidHumidity(currentRow)) {

                testCurrentHumidity = highestHumidSoFar.toString();

                if (!"N/A".equals(testCurrentHumidity)) {
                    //continuetion of loop
                    double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));

                    double tempLessHumidSoFar = Double.parseDouble(highestHumidSoFar.get("Humidity"));

                    if (currentHumidity > tempLessHumidSoFar) {

                        highestHumidSoFar = currentRow;

                    }

                }
            }

        }
        return highestHumidSoFar;
    }

    public CSVRecord getLowestHumidity(CSVRecord currentRow, CSVRecord lessHumidSoFar) {

        String testCurrentHumidity = null;

        if (lessHumidSoFar == null) {

            lessHumidSoFar = currentRow;

        } else {
            //check for iregularities in Humidity

            if (checkValidHumidity(currentRow)) {

                testCurrentHumidity = lessHumidSoFar.toString();

                if (!"N/A".equals(testCurrentHumidity)) {
                    //continuetion of loop
                    double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));

                    double tempLessHumidSoFar = Double.parseDouble(lessHumidSoFar.get("Humidity"));

                    if (currentHumidity < tempLessHumidSoFar) {

                        lessHumidSoFar = currentRow;

                    }

                }
            }

        }
        return lessHumidSoFar;
    }

    public CSVRecord getHighestHumidityInFile(CSVParser parser) {

        CSVRecord getHighestHumidity = null;

        for (CSVRecord currentRow : parser) {

            getHighestHumidity = getLowestHumidity(currentRow, getHighestHumidity);

        }
        return getHighestHumidity;
    }

    public CSVRecord getLowestHumidityHourInFile(CSVParser parser) {

        CSVRecord lowestHumidity = null;

        for (CSVRecord currentRow : parser) {

            lowestHumidity = getLowestHumidity(currentRow, lowestHumidity);

        }
        return lowestHumidity;
    }

    public void testLowestHumidity() {

        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord lowestHumidity = getLowestHumidityHourInFile(fr.getCSVParser());
        System.out.println("The most Humid persentage is " + lowestHumidity.get("Humidity")
                + " at " + lowestHumidity.get(getTime(lowestHumidity)) + " "
                + getTime(lowestHumidity) + " on " + lowestHumidity.get("DateUTC") + " UTC Time.");

    }

    public String getTime(CSVRecord record) {

        String time = null;

        if (record.get("TimeEST") == null) {

            time = "TimeEDT";

        } else {

            time = "TimeEST";

        }

        //TimeEST or TimeEDT
        //IF time is in EST OR EDT TO CONVER IT TO UTC + 4 hours
        return time;
    }

    public CSVRecord lowestHumidityInManyDays() {

        CSVRecord lowestHumiditySoFar = null;

        DirectoryResource dr = new DirectoryResource();

        //iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currenRow = getLowestHumidityHourInFile(fr.getCSVParser());

            lowestHumiditySoFar = getLowestHumidity(currenRow, lowestHumiditySoFar);

            // use method to get mostHumidInFile
            //mostHumidInFile is the answer
        }
        return lowestHumiditySoFar;
    }

    public void testMostHumidInManyDays() {

        CSVRecord lowestHumidity = lowestHumidityInManyDays();
        System.out.println("The lowest Humidity persentage was : " + lowestHumidity.get("Humidity")
                + " at " + lowestHumidity.get("DateUTC") + " UTC Time");
    }

    public double averageTemperatureInFile(CSVParser parser) {

        double sumTemperature = 0;
        double currentTemperature = 0;
        int i = 0;

        String tempF = null;

        for (CSVRecord currentRow : parser) {

            tempF = currentRow.get("TemperatureF");
            currentTemperature = Double.parseDouble(tempF);
            sumTemperature = sumTemperature + currentTemperature;
            i++;

        }
        return  sumTemperature / i;
    }

    public double averageTemperatureInManyFiles() {

        double averageTemperatureInManyFiles = 0;
        DirectoryResource dr = new DirectoryResource();
        double sumTemp = 0;
        int i = 0;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            double currenRow = averageTemperatureInFile(fr.getCSVParser());
            sumTemp = sumTemp + currenRow;
            i++;

        }
        averageTemperatureInManyFiles = sumTemp / i;

        return averageTemperatureInManyFiles;

    }

    public void testAverageTemperatureInFile() {

        FileResource fr = new FileResource("data/2014/weather-2014-01-20.csv");
        double averageTemperatureInFile = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("The average temperature in the selected file is : " + averageTemperatureInFile);

    }

    public void testAverageTemperatureInManyFiles() {

        double averageTemperatureInManyFiles = 0;

        averageTemperatureInManyFiles = averageTemperatureInManyFiles();

        System.out.println("The average temperature in the selected files are : " + averageTemperatureInManyFiles);

    }

    public double averageTemperatureHighHumidityInFile(CSVParser parser, int value) {

        double result = 0;
        double averageTemperatureWithHighHimidity = 0;
        String tempHumidity = null;
        int currentValueHumidity = 0;
        double sumTemp = 0;
        int i = 0;
        String tempTemperature = null;
        double currentTemp = 0;
        CSVRecord record = null;

        for (CSVRecord currentRow : parser) {

            if (checkValidHumidity(currentRow)) {

                tempHumidity = currentRow.get("Humidity");

                currentValueHumidity = Integer.parseInt(tempHumidity);

                if (currentValueHumidity >= value) {

                    try{    tempTemperature = currentRow.get("TemperatureF");
                    }catch(Exception e) {System.out.println("Not Valid Temperature!");}
                    currentTemp = Double.parseDouble(tempTemperature);
                    sumTemp = sumTemp + currentTemp;
                    i++;
                }

            }
            if (i != 0) {

                averageTemperatureWithHighHimidity = sumTemp / i;

            }
            result = averageTemperatureWithHighHimidity;

        }
        return result;
    }

    public void testAverageTemperatureWithHighHumidityInFile() {

        FileResource fr = new FileResource("data/2014/weather-2014-03-20.csv");
        double averageTemperatureInFile = averageTemperatureHighHumidityInFile(fr.getCSVParser(), 80);
        if (averageTemperatureInFile != 0) {
            System.out.println("The average temperature  with High Humidity is : " + averageTemperatureInFile);
        } else {
            System.out.println("No temperatures with that humidity.");
        }
    }

    public void testAverageTemperatureWithHighHumidityInFileNegative() {

        FileResource fr = new FileResource("data/2014/weather-2014-01-20.csv");
        double averageTemperatureInFile = averageTemperatureHighHumidityInFile(fr.getCSVParser(), 80);
        if (averageTemperatureInFile != 0) {
            System.out.println("The average temperature  with High Humidity is : " + averageTemperatureInFile);
        } else {
            System.out.println("No temperatures with that humidity.");
        }
    }


}
