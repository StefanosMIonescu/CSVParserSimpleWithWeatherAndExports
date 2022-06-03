import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ExportData {
    public void listExporters(CSVParser parser, String exportOfInterest) {

        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Export "Column
            String export = record.get("Exports");
            //Check if it contains exportOfIntrest
            if (export.contains(exportOfInterest)) {
                //If so, write down the "Contry" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public void whoExportsCoffee() {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");

    }

    public String countryInfo(CSVParser parser, String selectedCountry) {

        String info = "";
        String country = "";
        String exports = "";
        String valueInDollars = "";

        int i = 0;
        for (CSVRecord record : parser) {

            //print format "Country :" name , " "Country exports :" exports ," Export Value in Dollars $ :" value .
            country = record.get("Country");
            exports = record.get("Exports");
            valueInDollars = record.get("Value (dollars)");

            i++;

            if (selectedCountry.equals(record.get("Country"))) {

                info = "The Country's name is : " + country + " ,"
                        + "Exports :" + exports + " , " + " Value in Dollars :"
                        + valueInDollars;
            }
        }
        System.out.println(i);

        return info;
    }

    public void testCountryInfo(String countryName) {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String info = "";
        info = countryInfo(parser, countryName);
        System.out.println(info);
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {

        String info = "";
        String country = "";
        String exports = "";
        String valueInDollars = "";

        int i = 0;
        for (CSVRecord record : parser) {
            //print format "Country :" name , " "Country exoports :" exports ," Export Value in Dollars $ :" value .
            country = record.get("Country");
            exports = record.get("Exports");
            valueInDollars = record.get("Value (dollars)");
            i++;

            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                info = country;
                System.out.print(country + ",");
            }
        }
    }

    public void testListExportesTwoProducts(String exportItem1, String exportItem2) {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();

        System.out.print("The Countries with " + exportItem1 + " and "
                + exportItem2 + " exports are : ");
        listExportersTwoProducts(parser, exportItem1, exportItem2);

    }

    public int numberOfExporters(CSVParser parser, String exportItem) {

        int numberOfExporters = 0;

        String info = "";
        String country = "";
        String exports = "";
        String valueInDollars = "";

        for (CSVRecord record : parser) {
            country = record.get("Country");
            exports = record.get("Exports");
            valueInDollars = record.get("Value (dollars)");

            if (exports.contains(exportItem)) {
                numberOfExporters++;
                 System.out.print("The Number of Countries that are exporting "+exportItem +" are : "+ numberOfExporters);
            }

        }

        return numberOfExporters;
    }

    public void testNumberOfExports(String exportItem) {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();

        System.out.print("The number of the Countries that export  " + exportItem
                + " are : " + numberOfExporters(parser, exportItem));

    }

    public void bigExporters(CSVParser parser, String amount) {

        String country = "";
        String exports = "";
        String valueInDollars = "";

        for (CSVRecord record : parser) {
            //print format "Country :" name , " "Country exoports :" exports ," Export Value in Dollars $ :" value .
            country = record.get("Country");
            exports = record.get("Exports");
            valueInDollars = record.get("Value (dollars)");

            if (valueInDollars.length() > amount.length()) {

                System.out.println("The Countries that the Value amount of their total Exports is greater than : "
                        + amount + " are : " + country + " , with a value of : " + valueInDollars);
            }

        }

    }

    public void testBigExporters(String amount) {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        bigExporters(parser, amount);

    }

    public void numberOfCountriesWithTrillionExport() {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String info = null;
        String country = null;
        String exports = null;
        String valueInDollars = null;
        String trimmedValue = null;
        int sum = 0;
        int i = 0;
        long convertExportValue = 0;
        long trillionValue = 999999999999l;
        for (CSVRecord record : parser) {
            //print format "Country :" name , " "Country exports :" exports ," Export Value in Dollars $ :" value .
            country = record.get("Country");
            exports = record.get("Exports");
            valueInDollars = record.get("Value (dollars)");
            trimmedValue = valueInDollars.substring(1);
            convertExportValue = Long.parseLong(trimmedValue);
            i++;
            if (convertExportValue > trillionValue) {
                sum++;
            }
        }
        System.out.println("Number of Countries With Export greater Than 1 Trillion $ :" + sum);
    }
}
