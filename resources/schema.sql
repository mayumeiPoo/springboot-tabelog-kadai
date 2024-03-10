CREATE TABLE IF NOT EXISTS role (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(50) NOT NULL
 );
 
CREATE TABLE IF NOT EXISTS user (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
furigana VARCHAR(50) NOT NULL,
postal_code VARCHAR(50) NOT NULL,
address VARCHAR(255) NOT NULL,
phone_number VARCHAR(50) NOT NULL,
birthmonth VARCHAR(50),
birthday VARCHAR(50),
gender VARCHAR(50),
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,  
role_id INT NOT NULL,
enabled BOOLEAN NOT NULL,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    
FOREIGN KEY (role_id) REFERENCES role (id)
);

 CREATE TABLE IF NOT EXISTS category (
 id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(50)NOT NULL,
 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shop (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50)NOT NULL,
category_id INT NOT NULL,
image_name VARCHAR(255),
description VARCHAR(255) NOT NULL,
price INT NOT NULL,
postal_code VARCHAR(50) NOT NULL,
address VARCHAR(255) NOT NULL,
phone_number VARCHAR(50) NOT NULL,
station  VARCHAR(50) NOT NULL,
capacity INT NOT NULL,
time_start VARCHAR(50) NOT NULL,
time_end VARCHAR(50) NOT NULL,
lat  DOUBLE,
lng  DOUBLE,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS favorite (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
shop_id INT NOT NULL,
user_id INT NOT NULL,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (shop_id) REFERENCES shop (id),
FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS review (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
shop_id INT NOT NULL,
user_id INT NOT NULL,
evaluation INT NOT NULL,
comments VARCHAR(255) NOT NULL,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (shop_id) REFERENCES shop (id),
FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS reservation (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
shop_id INT NOT NULL,
user_id INT NOT NULL,
number_of_people INT NOT NULL,
reservation_date DATE NOT NULL,
reservation_time TIME NOT NULL,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (shop_id) REFERENCES shop (id),
FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS verification_tokens (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL UNIQUE,
token VARCHAR(255) NOT NULL,        
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES user (id) 
 );