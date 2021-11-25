#Exchange Rate APIs


Build a Spring Boot Rest Exchange rate API  with Maven that use Spring Data JPA to interact with H2 database.



Functional Requirements:
1. Exchange Rate API

   - _input_: currency pair to retrieve the exchange rate

   - _output_: exchange rate
2. Exchange API:

   - _input_: source amount, source currency, target currency

   - _output_: amount in target currency, and transaction id.
3. Exchange List API

   -  _input_: transaction id or conversion date

      - i. only one of the inputs shall be provided for each call

   - _output_: list of conversions filtered by the inputs
4. The application shall use a service provider to retrieve exchange rates and optionally
   for calculating amounts.
5. In the case of an error, a specific code to the error and a meaningful message shall be
   provided as response.
   > [https://currencylayer.com/](https://currencylayer.com/)
   
   > [http://fixer.io/](http://fixer.io/)
   

## Run Spring Boot application
```
mvn spring-boot:run
```


