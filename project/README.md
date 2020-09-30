# ΔΙΑΔΙΚΤΥΟ ΚΑΙ ΕΦΑΡΜΟΓΕΣ / ΔΙΚΤΥΑΚΟΣ ΠΡΟΓΡΑΜΜΑΤΙΣΜΟΣ

## Running

1. Run following commands:
    ```sh
    # Inside project directory run the bellow command to run Docker and spin the tomcat & mysql containers
    docker-compose up -d --build

    # Compile Java files
    javac -cp "./webapps/mysite/src/jar/*" -d ./webapps/mysite/WEB-INF/classes ./webapps/mysite/src/*.java

    # Restore an sql backup
    cat backup.sql | docker exec -i project_mysql /usr/bin/mysql -u root --password=root db
    ```
2. Open the page in web browser:

    http://localhost:8888/mysite/

3. Login with demo credentials:

    admin/admin
    
## Author
 Nikos Sarkiris