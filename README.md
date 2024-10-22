# daps-enterprise-mgmt
Official enterprise application for Dental Auxiliary Placement Services

1. `git clone https://github.com/michaelcordero/daps-enterprise-mgmt`
2. `cd [ root project directory]`
3. To run with gradle... `./gradlew run -Phost=127.0.0.1 -Pport=80`
    >or
3. To run with java jar... `./gradlew build && java -Dhost=127.0.0.1 -Dport=80 -jar build/libs/daps-enterprise-mgmt-0.0.1.jar`
4. The data must first be initialized in the database with a .csv file. The program that will scan your OS for the file and insert the data is in Utilities -> H2exCSV.kt. This can be run from the command line or within IntelliJ. Please email me at corderosoft@gmail.com for .csv file as this is confidential data.

