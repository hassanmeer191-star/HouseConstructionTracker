
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectExpense {

private int expenseId;           // unique id of the expense
private int projectId;           // project this expense belongs to
private String expenseName;      // cement, bricks, steel etc
private String description;      // details about the expense
private BigDecimal amount;       // money spent
private LocalDate expenseDate;   // date of the expense
private int supervisorId;        // supervisor who recorded it

public ProjectExpense(int expenseId, int projectId,String expenseName, String description,BigDecimal amount,
LocalDate expenseDate,int supervisorId)
{

this.expenseId = expenseId;
this.projectId = projectId;
this.expenseName = expenseName;
this.description = description;
this.expenseDate = expenseDate;
this.supervisorId = supervisorId;

//setAmount(amount);
}

}
