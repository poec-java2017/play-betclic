package models.api;

import models.OperationType;

import java.math.BigDecimal;
import java.util.Date;

public class OperationResult {
    public String uniq;
    public Date date;
    public BigDecimal amount;
    public OperationType type;
    public String user;
}
