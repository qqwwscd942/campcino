-- 데이터베이스 생성 및 사용
CREATE DATABASE IF NOT EXISTS ssafy_camp;
USE ssafy_camp;

-- tbl_sido 테이블 생성
DROP TABLE IF EXISTS tbl_sido;

CREATE TABLE tbl_sido (
    sido_id INT NOT NULL AUTO_INCREMENT,
    sido_name VARCHAR(50) NULL,
    PRIMARY KEY (sido_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_camp_category 테이블 생성
DROP TABLE IF EXISTS tbl_camp_category;

CREATE TABLE tbl_camp_category (
    category_id INT NOT NULL AUTO_INCREMENT,
    camp_category VARCHAR(30) NULL,
    PRIMARY KEY (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_camp_product 테이블 생성
DROP TABLE IF EXISTS tbl_camp_product;

CREATE TABLE tbl_camp_product (
    product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(20) NULL,
    product_category INT NULL,
    PRIMARY KEY (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_camp 테이블 생성
DROP TABLE IF EXISTS tbl_camp;

CREATE TABLE tbl_camp (
    camp_id INT NOT NULL AUTO_INCREMENT,
    sido_id INT NOT NULL,
    category_id INT NOT NULL,
    camp_name VARCHAR(30) NULL,
    camp_homepage VARCHAR(200) NULL,
    camp_explanation VARCHAR(1000) NULL,
    camp_phone VARCHAR(15) NULL,
    camp_character VARCHAR(1000) NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (sido_id) REFERENCES tbl_sido(sido_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES tbl_camp_category(category_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_address 테이블 생성
DROP TABLE IF EXISTS tbl_address;

CREATE TABLE tbl_address (
    camp_id INT NOT NULL,
    camp_road_address VARCHAR(50) NULL,
    camp_number_address VARCHAR(50) NULL,
    longitude DECIMAL(10,7) NULL,
    latitude DECIMAL(10,8) NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_user 테이블 생성
DROP TABLE IF EXISTS tbl_user;

CREATE TABLE tbl_user (
    user_id VARCHAR(30) NOT NULL,
    user_password VARCHAR(255) NULL DEFAULT NULL,
    user_phone VARCHAR(30) NULL DEFAULT NULL,
    user_age INT NULL DEFAULT NULL,
    user_email VARCHAR(30) NULL DEFAULT NULL,
    user_gender VARCHAR(10) NULL DEFAULT NULL,
    user_signup TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    user_delete TIMESTAMP NULL DEFAULT NULL,
    user_mileage BIGINT NULL DEFAULT NULL,
    user_url VARCHAR(255) NULL DEFAULT NULL,
    user_temp_auth VARCHAR(30) NULL DEFAULT NULL,
    user_refresh_token VARCHAR(255) NULL DEFAULT NULL, -- Refresh Token 저장 필드 추가
    user_grade VARCHAR(30) NULL DEFAULT NULL,         -- 회원 등급
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_review 테이블 생성
DROP TABLE IF EXISTS tbl_review;

CREATE TABLE tbl_review (
    review_id INT NOT NULL AUTO_INCREMENT,
    camp_rate INT NULL,
    comment TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id VARCHAR(30) NOT NULL,
    camp_id INT NOT NULL,
    PRIMARY KEY (review_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_boards 테이블 생성
DROP TABLE IF EXISTS `tbl_boards`;

CREATE TABLE `tbl_boards` (
    `board_id` INT NOT NULL AUTO_INCREMENT,
    `board_title` VARCHAR(100) NULL,
    `board_content` TEXT NULL,
     `category` VARCHAR(50) NOT NULL,
    `board_created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `board_view` INT NULL DEFAULT 0,
    `img_url` VARCHAR(1000) NULL,
    `user_id` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tbl_comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT NOT NULL,
    user_id VARCHAR(30) NOT NULL,
    comment_content TEXT NOT NULL,
    comment_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES tbl_boards(board_id) ON DELETE CASCADE
);

-- tbl_res 테이블 생성
DROP TABLE IF EXISTS tbl_res;

CREATE TABLE tbl_res (
    res_id INT NOT NULL AUTO_INCREMENT,
    res_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    res_status VARCHAR(30) NULL,
    user_id VARCHAR(30) NOT NULL,
    camp_id INT NOT NULL,
    PRIMARY KEY (res_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_op 테이블 생성
DROP TABLE IF EXISTS tbl_op;

CREATE TABLE tbl_op (
    camp_id INT NOT NULL,
    o_weekday BOOLEAN NULL,
    o_weekend BOOLEAN NULL,
    o_spring BOOLEAN NULL,
    o_summer BOOLEAN NULL,
    o_fall BOOLEAN NULL,
    o_winter BOOLEAN NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_facilities 테이블 생성
DROP TABLE IF EXISTS tbl_facilities;

CREATE TABLE tbl_facilities (
    camp_id INT NOT NULL,
    f_elec BOOLEAN NULL,
    f_hot_water BOOLEAN NULL,
    f_wifi BOOLEAN NULL,
    f_firewood BOOLEAN NULL,
    f_trails BOOLEAN NULL,
    f_swimming BOOLEAN NULL,
    f_playground BOOLEAN NULL,
    f_market BOOLEAN NULL,
    f_toilets INT NULL, -- 오타 수정: f_toilerts -> f_toilets
    f_showers INT NULL,
    f_sinks INT NULL,
    f_fire_extinguishers INT NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_near_facilities 테이블 생성
DROP TABLE IF EXISTS tbl_near_facilities;

CREATE TABLE tbl_near_facilities (
    camp_id INT NOT NULL,
    n_fishing BOOLEAN NULL,
    n_hiking BOOLEAN NULL,
    n_beach BOOLEAN NULL,
    n_water_sports BOOLEAN NULL,
    n_valley BOOLEAN NULL,
    n_river BOOLEAN NULL,
    n_pool BOOLEAN NULL,
    n_youth BOOLEAN NULL,
    n_farm BOOLEAN NULL,
    n_kids BOOLEAN NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_glamping 테이블 생성
DROP TABLE IF EXISTS tbl_glamping;

CREATE TABLE tbl_glamping (
    camp_id INT NOT NULL,
    g_bed BOOLEAN NULL,
    g_tv BOOLEAN NULL,
    g_fridge BOOLEAN NULL,
    g_internet BOOLEAN NULL,
    g_toilet BOOLEAN NULL,
    g_aircon BOOLEAN NULL,
    g_heater BOOLEAN NULL,
    g_cook_tool BOOLEAN NULL,
    PRIMARY KEY (camp_id),
    FOREIGN KEY (camp_id) REFERENCES tbl_camp(camp_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_camp_shop 테이블 생성 및 수정
DROP TABLE IF EXISTS tbl_camp_shop;

CREATE TABLE tbl_camp_shop (
    shop_id INT NOT NULL AUTO_INCREMENT,
    shop_title VARCHAR(30) NULL,
    shop_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    shop_view_count INT NULL DEFAULT 0,
    shop_status VARCHAR(30) NULL,
    shop_img VARCHAR(255) NULL,
    shop_price BIGINT NULL,
    shop_comment VARCHAR(1000) NULL, -- 오타 수정: shope_comment -> shop_comment
    shop_rating DECIMAL(3,2) NULL,    -- 평점 필드 추가
    Field VARCHAR(255) NULL,
    product_id INT NOT NULL,
    user_id VARCHAR(30) NOT NULL,
    PRIMARY KEY (shop_id),
    FOREIGN KEY (product_id) REFERENCES tbl_camp_product(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tbl_shop_review 테이블 생성
DROP TABLE IF EXISTS tbl_shop_review;

CREATE TABLE tbl_shop_review (
    review_id INT NOT NULL AUTO_INCREMENT,
    shop_id INT NOT NULL,
    user_id VARCHAR(30) NOT NULL,
    shop_rate INT NOT NULL, -- 평점 (1-5)
    comment TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (review_id),
    FOREIGN KEY (shop_id) REFERENCES tbl_camp_shop(shop_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;