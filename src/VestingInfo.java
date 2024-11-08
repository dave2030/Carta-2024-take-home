import java.math.BigDecimal;

/**
 * Java object for storing the VestingInfo
 */

public class VestingInfo {
    private final String employeeId;
    private final String employeeName;
    private final String awardId;
    private BigDecimal vestedShares; //Using BigDecimal is an efficient way to store and handle large numbers with precision

    public VestingInfo(String employeeId, String employeeName, String awardId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.awardId = awardId;
        this.vestedShares = new BigDecimal(0);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getAwardId() {
        return awardId;
    }

    public BigDecimal getVestedShares() {
        return vestedShares;
    }

    public void setVestedShares(BigDecimal vestedShares) {
        this.vestedShares = vestedShares;
    }

}