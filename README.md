# build steps

1. Get an IDE (e.g. Visual Studio Code, https://code.visualstudio.com/)
2. Download and install a Java Development Kit (JDK) (e.g. https://bell-sw.com/pages/downloads/#/java-17-lts)
3. On Windows, download and install Docker Desktop (https://www.docker.com/products/docker-desktop/)
4. Initialize Docker Desktop to check that virtualization is enabled on Windows
5. Download and install Postman (https://www.postman.com/downloads/)
6. Open a terminal and clone the source code (git clone https://github.com/Bryanyoong/vanguard-work.git)
7. cd into vanguard-work/SalesApplication
8. Type ./gradlew bootrun

# run steps
1. Type ./gradlew bootrun

2. Open Postman and import this to create a CSV with 1 million records:  
    curl 'http://localhost:8080/createGameSales?size=1000000'

3. Import the CSV file:  
    curl 'http://localhost:8080/import' --form 'file=@"/C:/Users/bryan/Documents/vanguard-work/SalesApplication/game_sales_1000000.csv"'

4. List of game sales:  
    curl 'http://localhost:8080/getGameSales?pageSize=100&pageNumber=1'

5. List of game sales during a given period (15 April to 16 April):  
    curl 'http://localhost:8080/getGameSales?date_of_sale_from=1713182400&date_of_sale_to=1713268800&pageSize=100&pageNumber=1'

6. List of game sales where sale_price is less than $2.00:  
    curl 'http://localhost:8080/getGameSales?sale_price_less_than=2&pageSize=100&pageNumber=1'

7. List of game sales where sale_price is greater than $90:  
    curl 'http://localhost:8080/getGameSales?sale_price_greater_than=90&pageSize=100&pageNumber=1'

8. List of game sales during a given period (15 April to 16 April) and sale_price is less than $2.00:  
    curl 'http://localhost:8080/getGameSales?date_of_sale_from=1713182400&date_of_sale_to=1713268800&sale_price_less_than=2&pageSize=100&pageNumber=1'

9. Total number of games sold during a given period (daily counts) (15 April to 16 April):  
    curl 'http://localhost:8080/getTotalSales?date_of_sale_from=1713182400&date_of_sale_to=1713268800'

10. Total sales generated (total sale_price) during a given period (daily dales) (15 April to 16 April):  
    curl 'http://localhost:8080/getTotalSales?date_of_sale_from=1713182400&date_of_sale_to=1713268800'

11. Total sales generated (total sale_price) during a given period with a given game_no. (eg. daily sales of a particular game_no) (15 April to 16 April) and game_no 1:  
    curl 'http://localhost:8080/getTotalSales?date_of_sale_from=1713182400&date_of_sale_to=1713268800&game_no=1'