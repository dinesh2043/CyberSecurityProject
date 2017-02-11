CREATE DATABASE my_db;

CREATE TABLE Persons
(
PersonID int,
LastName varchar(255),
FirstName varchar(255),
Address varchar(255),
City varchar(255)
);

SELECT * FROM Customers;

SELECT * FROM Customers
WHERE CustomerID=1;

INSERT INTO table_name (column1,column2,column3,...)
VALUES (value1,value2,value3,...);

UPDATE Customers
SET ContactName='Alfred Schmidt', City='Frankfurt'
WHERE CustomerID=1;

DELETE FROM Customers
WHERE CustomerName='Alfreds Futterkiste' AND ContactName='Maria Anders';