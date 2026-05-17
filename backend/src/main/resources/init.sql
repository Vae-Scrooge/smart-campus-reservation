-- 初始化数据库
CREATE DATABASE IF NOT EXISTS smart_campus DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_campus;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    real_name VARCHAR(50),
    student_id VARCHAR(20),
    phone VARCHAR(20),
    credit_score INT DEFAULT 100,
    role VARCHAR(20) DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 资源表（自习室/座位/设备等）
CREATE TABLE IF NOT EXISTS resources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL COMMENT '自习室/实验室/设备/会议室',
    location VARCHAR(200),
    capacity INT DEFAULT 1,
    description TEXT,
    status VARCHAR(20) DEFAULT 'AVAILABLE' COMMENT 'AVAILABLE/MAINTENANCE/DISABLED',
    open_time TIME DEFAULT '08:00',
    close_time TIME DEFAULT '22:00',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 预约记录表
CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    resource_id BIGINT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING/CHECKED_IN/COMPLETED/NO_SHOW/CANCELLED',
    checkin_time DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (resource_id) REFERENCES resources(id)
);

-- 插入测试数据
INSERT INTO users (username, password, email, real_name, student_id, role) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@campus.edu', '管理员', '000000', 'ADMIN'),
('zhangsan', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'zhangsan@campus.edu', '张三', '2024001', 'USER'),
('lisi', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'lisi@campus.edu', '李四', '2024002', 'USER');

INSERT INTO resources (name, type, location, capacity, description) VALUES
('图书馆自习室A区-01', '自习室', '图书馆3楼A区', 1, '靠窗安静座位，带电源插座'),
('图书馆自习室A区-02', '自习室', '图书馆3楼A区', 1, '标准自习座位'),
('图书馆自习室B区-01', '自习室', '图书馆3楼B区', 1, '带台灯座位'),
('计算机实验室-01', '实验室', '实验楼4楼401', 30, '配备40台工作站，预装开发环境'),
('小型会议室A', '会议室', '行政楼2楼', 10, '配备投影仪和白板');
