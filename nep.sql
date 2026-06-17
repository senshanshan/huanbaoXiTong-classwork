CREATE DATABASE IF NOT EXISTS nep DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nep;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS aqi_feedback;
DROP TABLE IF EXISTS grid_town;
DROP TABLE IF EXISTS grid_county;
DROP TABLE IF EXISTS grid_member;
DROP TABLE IF EXISTS grid_city;
DROP TABLE IF EXISTS grid_province;
DROP TABLE IF EXISTS supervisor;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS aqi;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE admins (
  admin_id INT PRIMARY KEY AUTO_INCREMENT,
  admin_code VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  remarks VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE supervisor (
  tel_id VARCHAR(20) PRIMARY KEY,
  password VARCHAR(100) NOT NULL,
  real_name VARCHAR(50) NOT NULL,
  birthday VARCHAR(20),
  sex INT,
  remarks VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE grid_province (
  province_id INT PRIMARY KEY AUTO_INCREMENT,
  province_name VARCHAR(50) NOT NULL,
  province_abbr VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE grid_city (
  city_id INT PRIMARY KEY AUTO_INCREMENT,
  city_name VARCHAR(50) NOT NULL,
  province_id INT NOT NULL,
  INDEX idx_grid_city_province_id (province_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE grid_member (
  gm_id INT PRIMARY KEY AUTO_INCREMENT,
  gm_name VARCHAR(50) NOT NULL,
  gm_code VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  province_id INT NOT NULL,
  city_id INT NOT NULL,
  tel VARCHAR(20),
  state INT DEFAULT 1,
  remarks VARCHAR(255),
  INDEX idx_grid_member_area_state (province_id, city_id, state)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE aqi (
  aqi_id INT PRIMARY KEY AUTO_INCREMENT,
  chinese_explain VARCHAR(50) NOT NULL,
  color VARCHAR(30),
  aqi_explain VARCHAR(100),
  so2_min INT,
  so2_max INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE aqi_feedback (
  af_id INT PRIMARY KEY AUTO_INCREMENT,
  tel_id VARCHAR(20) NOT NULL,
  province_id INT NOT NULL,
  city_id INT NOT NULL,
  address VARCHAR(200),
  information VARCHAR(500),
  estimated_grade INT,
  af_date VARCHAR(20),
  af_time VARCHAR(20),
  gm_id INT,
  assign_date VARCHAR(20),
  assign_time VARCHAR(20),
  state INT DEFAULT 0,
  remarks VARCHAR(255),
  INDEX idx_aqi_feedback_tel_id (tel_id),
  INDEX idx_aqi_feedback_gm_state (gm_id, state),
  INDEX idx_aqi_feedback_page (province_id, city_id, estimated_grade, af_date, state)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE statistics (
  id INT PRIMARY KEY AUTO_INCREMENT,
  province_id INT,
  city_id INT,
  address VARCHAR(200),
  so2_value INT,
  so2_level INT,
  co_value INT,
  co_level INT,
  spm_value INT,
  spm_level INT,
  aqi_id INT,
  confirm_date VARCHAR(20),
  confirm_time VARCHAR(20),
  gm_id INT,
  fd_id VARCHAR(20),
  information VARCHAR(500),
  remarks VARCHAR(255),
  INDEX idx_statistics_area_date (province_id, city_id, confirm_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE grid_county (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  city_id BIGINT,
  name VARCHAR(50),
  code VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE grid_town (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  county_id BIGINT,
  name VARCHAR(50),
  code VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO admins (admin_id, admin_code, password, remarks) VALUES
(1, 'admin', '123456', '默认管理员');

INSERT INTO supervisor (tel_id, password, real_name, birthday, sex, remarks) VALUES
('13800000000', '123456', '公众监督员', '2000-01-01', 1, '默认公众监督员');

INSERT INTO grid_province (province_id, province_name, province_abbr) VALUES
(1, '辽宁省', '辽'),
(2, '北京市', '京');

INSERT INTO grid_city (city_id, city_name, province_id) VALUES
(1, '沈阳市', 1),
(2, '大连市', 1),
(3, '北京市', 2);

INSERT INTO grid_member (gm_id, gm_name, gm_code, password, province_id, city_id, tel, state, remarks) VALUES
(1, '沈阳网格员', 'gm001', '123456', 1, 1, '13900000000', 1, '默认网格员'),
(2, '大连网格员', 'gm002', '123456', 1, 2, '13900000001', 1, '默认网格员');

INSERT INTO aqi (aqi_id, chinese_explain, color, aqi_explain, so2_min, so2_max) VALUES
(1, '优', '#00e400', '空气质量令人满意', 0, 50),
(2, '良', '#ffff00', '空气质量可接受', 51, 150),
(3, '轻度污染', '#ff7e00', '易感人群症状有轻度加剧', 151, 475),
(4, '中度污染', '#ff0000', '进一步加剧易感人群症状', 476, 800),
(5, '重度污染', '#99004c', '心脏病和肺病患者症状显著加剧', 801, 1600),
(6, '严重污染', '#7e0023', '健康人群运动耐受力降低', 1601, 9999);

INSERT INTO aqi_feedback
(af_id, tel_id, province_id, city_id, address, information, estimated_grade, af_date, af_time, gm_id, assign_date, assign_time, state, remarks)
VALUES
(1, '13800000000', 1, 1, '和平区示例街道', '发现空气异味明显', 3, '2026-06-15', '09:00:00', 1, '2026-06-15', '09:30:00', 1, '测试反馈');

INSERT INTO statistics
(id, province_id, city_id, address, so2_value, so2_level, co_value, co_level, spm_value, spm_level, aqi_id, confirm_date, confirm_time, gm_id, fd_id, information, remarks)
VALUES
(1, 1, 1, '和平区示例街道', 80, 2, 40, 1, 120, 3, 3, '2026-06-15', '10:00:00', 1, '13800000000', '发现空气异味明显', '测试统计');
