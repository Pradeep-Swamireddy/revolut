REST Api to Transfer Money from one Account to another:

1) POST /customers
2) POST /customers/{customerId}/accounts
3) DELETE /customers/{customerId}
4) POST /transfers

Command to run main application:
java -cp money-transfer-api.jar com.revolut.banking.main.TransferApp

Command to run test cases:
java -jar money-transfer-api.jar

