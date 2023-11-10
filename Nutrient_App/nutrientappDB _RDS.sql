DROP DATABASE IF EXISTS NUTRIENTAPP;
CREATE DATABASE NUTRIENTAPP; 
USE NUTRIENTAPP;


CREATE TABLE USER (
	userID				INT 				NOT NULL	AUTO_INCREMENT,
    username 			VARCHAR(50) 		NOT NULL, 
    user_password 		VARCHAR(255) 		NOT NULL, 
    fname				VARCHAR(255) 		NOT NULL,
    lname				VARCHAR(255) 		NOT NULL,
    sex					CHAR(2)				NOT NULL,
    dob 				VARCHAR(50), 
    
    weight 				INT					NOT NULL, 
    height 				INT     			NOT NULL, 
    
    
    PRIMARY KEY (userID),
    UNIQUE (username)
); 

    
CREATE TABLE DIET_LOG (
	username 			VARCHAR(50) 		NOT NULL, 
    diet_log_id 		INT					NOT NULL	AUTO_INCREMENT, 
	date_log 			DATE				NOT NULL,
    meal_type			VARCHAR(50)			NOT NULL,
    
    
    PRIMARY KEY (diet_log_id),
    FOREIGN KEY (`username`) REFERENCES USER(`username`),
    
    -- meal_type ENUM('breakfast', 'lunch', 'dinner', 'snack') NOT NULL
    -- CONSTRAINT int_num CHECK(meal_type='snacks' or meal_type='breakfast' or meal_type='lunch' or meal_type='dinner')
);

CREATE TABLE USER_INGREDIENTS (
	diet_log_id 		INT					NOT NULL, 
    ingredients_id		INT					NOT NULL 	AUTO_INCREMENT,
    ingredients			VARCHAR(255)		NOT NULL,
    quantities			VARCHAR(255)		NOT NULL,
    
    PRIMARY KEY (ingredients_id),
    FOREIGN KEY (`diet_log_id`) REFERENCES DIET_LOG(`diet_log_id`)
    );
    

CREATE TABLE EXERCISE_LOG (
	username 			VARCHAR(50) 		NOT NULL, 
    exercise_log_id		INT 				NOT NULL	AUTO_INCREMENT,
	date_log 			DATE				NOT NULL,
    exercise_type		VARCHAR(50)			NOT NULL,
    duration			INT					NOT NULL,
    intensity			CHAR(10)			NOT NULL,
	
    PRIMARY KEY (exercise_log_id),
    FOREIGN KEY (`username`) REFERENCES USER(`username`)
);




    
    
    




