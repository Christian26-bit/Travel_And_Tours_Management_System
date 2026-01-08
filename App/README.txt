# Travel Management System - Portable App Folder

This folder contains everything needed to run the application standalone.

## Files:
- travelmanagement.jar: The main application.
- db-seeder.jar: Tool to populate the database with initial data.
- config.properties: Database credentials (edit this if your MySQL setup is different).
- schema.sql: Database structure (automatically loaded on first run).
- Run-App.bat: Double-click to run the main application.

## How to Run:
1. Ensure MySQL is running on your system.
2. Edit `config.properties` if you need to change the database user or password.
3. (Optional) Run the seeder to add sample packages:
   java -jar db-seeder.jar
4. Run the application:
   Double-click Run-App.bat OR run 'java -jar travelmanagement.jar'

## Default Admin Credentials:
- Email: admin@cht.com
- Password: password123
