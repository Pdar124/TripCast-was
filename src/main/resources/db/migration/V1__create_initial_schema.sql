CREATE TABLE tour_spot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tour_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_course_id VARCHAR(20) NOT NULL,
    CONSTRAINT uk_tour_course_source_id UNIQUE (source_course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tour_course_stop (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_spot_id VARCHAR(20) NOT NULL,
    course_id BIGINT NOT NULL,
    tour_spot_id BIGINT NOT NULL,
    region_id VARCHAR(20) NOT NULL,
    course_order INT NOT NULL,
    travel_time INT NOT NULL,
    indoor_type VARCHAR(10) NOT NULL,
    theme_code VARCHAR(10) NOT NULL,
    theme_name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_course_stop_source_id UNIQUE (source_spot_id),
    CONSTRAINT uk_course_stop_order UNIQUE (course_id, course_order),
    CONSTRAINT fk_course_stop_course FOREIGN KEY (course_id) REFERENCES tour_course (id),
    CONSTRAINT fk_course_stop_spot FOREIGN KEY (tour_spot_id) REFERENCES tour_spot (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE region_climate_index (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    region_id VARCHAR(20) NOT NULL,
    base_date DATE NOT NULL,
    score INT NOT NULL,
    grade VARCHAR(20) NOT NULL,
    CONSTRAINT uk_region_climate_date UNIQUE (region_id, base_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE spot_weather_index (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_spot_id VARCHAR(20) NOT NULL,
    base_date DATE NOT NULL,
    feels_like_temp DOUBLE,
    uv_index INT,
    CONSTRAINT uk_spot_weather_date UNIQUE (source_spot_id, base_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
