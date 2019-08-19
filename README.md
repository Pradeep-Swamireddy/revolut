REST Api to Transfer Money from one Account to another:

1) POST /customers
   Sample JSON:
   {
      "customerId":"pradeepntg",
      "fullName":"Pradeep Swamireddy",
      "address":"London"
   }
2) GET /customers/{customerId}
3) DELETE /customers/{customerId}
4) GET /customers/{customerId}/accounts/{accountId}
5) POST /customers/{customerId}/accounts
   Sample JSON:
   {
      "balance":"1000"
   }
6) POST /transfers
   Sample JSON:
   {
      "sender":{
      "accountNo":"1"	
      },
      "receiver":{
      "accountNo":"2"	
      },
      "transferAmount":"200.525"
    }

Build Command:
mvn clean package

Command to run test cases:
java -jar money-transfer-api.jar

Command to run main application:
java -cp money-transfer-api.jar com.revolut.banking.main.TransferApp


