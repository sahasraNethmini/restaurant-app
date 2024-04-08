drop database restaurant;
CREATE DATABASE restaurant;
USE restaurant;

CREATE TABLE Customer(
	id VARCHAR(20) NOT NULL,
	name VARCHAR(50) NOT NULL,
	contact VARCHAR(50) NOT NULL,
	CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE Item(
	code VARCHAR(50) NOT NULL,
	description VARCHAR(50) NOT NULL,
	unitPrice decimal(10,2),
	qtyOnHand decimal(10,2),
	CONSTRAINT PRIMARY KEY (code)
);


	CREATE TABLE Orders(
		orderId VARCHAR(50) NOT NULL,
		orderDate DATE NOT NULL,
		customerID VARCHAR(20) NOT NULL,
		CONSTRAINT PRIMARY KEY (orderId),
		CONSTRAINT FOREIGN KEY (customerID) REFERENCES Customer(id)
	);


	CREATE TABLE OrderDetail(
		orderId VARCHAR(50) NOT NULL,
		code VARCHAR(50) NOT NULL,
		qty decimal(10,2),
		unitPrice decimal(10,2),
		CONSTRAINT PRIMARY KEY (orderId,code),
		CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId),
		CONSTRAINT FOREIGN KEY (code) REFERENCES Item(code)
	);