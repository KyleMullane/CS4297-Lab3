1. Start MySQL with Docker

Run Command:
docker pull mysql:8.0
Docker cant run a MySQL container unless it first has the MySQL image

Run this command once to start a MySQL container:
docker run --name lab-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=example -e MYSQL_DATABASE=demo -e MYSQL_USER=appuser -e MYSQL_PASSWORD=apppass -d mysql:8.0

If the container is stopped later, You can start it again with:
docker start lab-mysql

2. Run the Spring Boot App

Run the Command:
.\mvnw.cmd spring-boot:run
Make sure you "cd" into the correct directory

The app will start on http://localhost:8080

3. Testing with Postman

Create a new collection called Lab03 API.

Add requests for each endpoint below.
| Method | Endpoint      | Description          |
| ------ | ------------- | -------------------- |
| GET    | `/items`      | Get all items        |
| GET    | `/items/{id}` | Get item by ID       |
| POST   | `/items`      | Create new item      | {"name": "Marker","quantity": 15 } body raw json (You can put whatever item you want here just and example)
| PUT    | `/items/{id}` | Update existing item | {"name": "Blue Pencil","quantity": 12 } body raw json (You can put whatever item you want here just and example)
| DELETE | `/items/{id}` | Delete item by ID    |

4. MySQL Database

Run Command:
docker exec -it lab-mysql mysql -u appuser -papppass -D demo

Show all items:
SELECT * FROM items;

Step 2 instructions 
[CS4297.lab2.step2.docx](https://github.com/user-attachments/files/22583515/CS4297.lab2.step2.docx)

Step 1 and 3 Instructions [Lab03 Step 1 and 3 `.docx](https://github.com/user-attachments/files/22584129/Lab03.Step.1.and.3.docx)

Step 4 Instructions - [Lab03-Step4.docx](https://github.com/user-attachments/files/22584887/Lab03-Step4.docx)



