# Read Me First

Requirements

For building and running the application you need:
============================================================================
JDK 1.8
Maven 3
Running the application locally

There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the main method in the com.rbs.service.customer.accounts.CustomerAccountsServiceApplication 
class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run

Solution design
============================================================================

This is a Spring Boot micro service which will allow a user to administer a customer’s bank account.

This uses Java8, RestApi, spring Data, In memory H2 database to store data, Junit5.

This creates database table with every server start and drops them with server stop.

After the tables are created, it uses import.sql in resources to load data.

can access H2 user interface using
http://localhost:8080/customer-accounts-service/h2

This is a rest service(CustomerAccountController) which calls request delegator (CustomerAccountDelegator)
 which in turn calls Service(CustomerAccountService) 
 which talks to CRUD repositories for database operations.
 
 This also uses Swagger for Api documentation.
 http://localhost:8080/customer-accounts-service/v2/api-docs
 Handles Exceptions using ControllerAdvice.
 
 ============================================================================
 Service catalog
  ============================================================================

+request the current balance-


HTTP GET
localhost:8080/customer-accounts-service/accounts/10002/balance?customerId="600"

+capture a withdrawal

HTTP PATCH
localhost:8080/customer-accounts-service/accounts/withDrawl
Request json

{
"accountNumber" : "10001",
"amount" : "1",
"customerId" : "600"
}


+capture a deposit

HTTP PATCH
localhost:8080/customer-accounts-service/accounts/deposit
Request json

{
"accountNumber" : "10001",
"amount" : "10",
"customerId" : "600"
}


+debit/credit interest amount

HTTP GET
localhost:8080/customer-accounts-service/accounts/10001/credit/interestRate?customerId="600"


+List last 20  transactions

HTTP GET
localhost:8080/customer-accounts-service/accounts/10002/transactions/20?customerId="600"

 ============================================================================
 Improvements that can be done to this application
  ============================================================================
  
  I tried to write junit test cases for most the code.
  But due to time constraint, I couldn't manage to add JUNIT tests for custom methods that i have added for CRUD repositories.
  
  Proper code formatter is not setup in my IntelliJ in my personal laptop. So could have configured it for better 
  code format.
  
  More scope to improve data model in the database if time permits.
  
  