import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

//The cumulative vesting schedule

/**
 * Java command-line program that reads a file of vesting events and outputs a vesting schedule to stdout
 */

public class VestingSchedule {

    private final Map<String, VestingInfo> vestingMap = new LinkedHashMap<>(); //HashMap to store the information regarding vesting

    /**
     * This is the main method for the java program to create an Object for the VestingSchedule and execute the main
     * method createVestingSchedule with the arguments passed from the user input
     * The following is the format for input: <filename> <target_date> <precision> (optional)
     *
     * @param String [] args
     */
    public static void main(String[] args) {
        VestingSchedule vestingSchedule = new VestingSchedule();
        vestingSchedule.createVestingSchedule(args);
    }

    /**
     * This is the main method that accepts user input and calls the methods to perform logic to output the vesting schedule
     *
     * @param String [] args
     */
    private void createVestingSchedule(String[] args) {
        String methodName = "createVestingSchedule";
        if (args.length < 2) { //If there is insufficient input provided, the program exits with the following statement
            System.out.printf("%s: Please use the following format: <filename> <target_date>", methodName);
            exitProgram();
        }
        String filename = "";
        LocalDate targetDate = null;
        int precision = 0;
        try {
            filename = args[0];
            targetDate = LocalDate.parse(args[1], DateTimeFormatter.ISO_DATE);
            precision = 0;
            if (args.length == 3) { //This checks if the optional parameter for precision is passed
                precision = Integer.parseInt(args[2]);
            } else if (args.length > 3) { //Checks if too many arguments are provided
                throw new NullPointerException("Too many arguments provided");
            }
        } catch (Exception e) { //Checks if there is an error with parsing arguments
            System.out.printf("%s: There is an error with the parsing the arguments. Please investigate the following: " + e.getMessage(), methodName);
            exitProgram();
        }
        processVestingFile(filename, targetDate, precision);
        outputVestingInfo();
    }

    /**
     * This is the core method that performs logic to parse the file into the VestingInfo object, calculates the vesting shares
     * and populates the vestingMap
     *
     * @param String    fileName
     * @param LocalDate targetDate
     * @param int       precision
     */
    private void processVestingFile(String fileName, LocalDate targetDate, int precision) {
        String methodName = "processVestingFile";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { //Parses file with BufferedReader
            String line;
            while ((line = br.readLine()) != null) {
                String[] parsed = line.split(",");
                String eventType = parsed[0];
                String employeeId = parsed[1];
                String employeeName = parsed[2];
                String awardId = parsed[3];
                LocalDate eventDate = LocalDate.parse(parsed[4], DateTimeFormatter.ISO_DATE);
                BigDecimal quantity = new BigDecimal(parsed[5]).setScale(precision, RoundingMode.DOWN);
                if (eventDate.isAfter(targetDate)) { //This sets the quantity to 0 since the parsed eventDate is after the target date
                    quantity = new BigDecimal(0).setScale(precision);
                }
                String key = employeeId + "|" + employeeName + "|" + awardId; //Using delimiters to concat the key attributes
                VestingInfo vestingInfo = this.vestingMap.getOrDefault(key, new VestingInfo(employeeId, employeeName, awardId));
                processVestingShares(eventType, vestingInfo, quantity);
                vestingMap.put(key, vestingInfo);
            }
        } catch (Exception e) { //This is the error scenerio where an issue has occured from trying to parse the file
            System.out.printf("%s: There is an error with the program. Please investigate the following: " + e.getMessage(), methodName);
            exitProgram();
        }
    }

    /**
     * This is method to process the shares for the vesting information based on the given quantity parsed
     *
     * @param String      action
     * @param VestingInfo vestingInfo
     * @param BigDecimal  quantity
     */
    private void processVestingShares(String action, VestingInfo vestingInfo, BigDecimal quantity) {
        if (action.equalsIgnoreCase("VEST")) {
            vestingInfo.setVestedShares(vestingInfo.getVestedShares().add(quantity));
        } else if (action.equalsIgnoreCase("CANCEL")) {
            vestingInfo.setVestedShares(vestingInfo.getVestedShares().subtract(quantity));
        }
    }


    /**
     * This is method to output the vesting schedule in the corrosping format ordered by employeeId and awardId
     */
    private void outputVestingInfo() {
        vestingMap.values().stream()
                .sorted(Comparator.comparing(VestingInfo::getEmployeeId).thenComparing(VestingInfo::getAwardId))
                .forEach(element -> {
                    System.out.println(element.getEmployeeId() + "," + element.getEmployeeName() + "," + element.getAwardId() + "," + element.getVestedShares());
                });
    }

    /**
     * This is method to exit the program
     */
    private void exitProgram() {
        System.exit(1);
    }
}