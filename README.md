# Poseidon

Poseidon is an application for institutional investors who buy and sell fixed income securities. This application brings together information from many sources on fixed income securities

## Prerequisites:

1. Framework: Spring Boot v2.0.4
2. Java 8
3. Thymeleaf
4. Bootstrap v.4.3.1
5. Mysql 8.0.17


## Create Database
1. Download Poseidon project on this repository
2. Open the application.properties
3. In the "Datasource configuration" section, enter your Mysql configuration 
        spring.datasource.username={your username}
        spring.datasource.password={your passeword}
4. Run sql script to create table doc/DATA.sql

## Running App

Post installation of Java, Maven and MySQL, and after creating the database, you will be ready to lauch the application. By default, Poseidon will be stated on port 8080

## Use Poseidon
As we said, the application started on port 8080, so open your favorite web browser, and type "http://localhost:8080/" in the address bar. The anthentification page should show up. To connect to the application, you have to enter an email address, and a password.

You can to Poseidon with your Github account or with a username and a password. By default, all the user password are encoded in the database, here are all the accounts you can use with their password not encrypted :

- Username: user - Password: Passw0rd!
- Username: admin - Password: Passw0rd!

If you choose to connect with your Github account or with a user account, you will only be able to view and add transaction entities.
If you choose to connect with an admin account, you will be able to access all the features
