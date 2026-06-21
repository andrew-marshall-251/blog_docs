-- MySQL create-and-populate script for the blog Spring Boot app.
-- Matches the JPA entities in blog/src/main/java/com/andrew/blog/entities.

DROP DATABASE IF EXISTS blog;
CREATE DATABASE blog
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE blog;

CREATE TABLE mascots (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    img_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mascot_id BIGINT NULL,
    bio LONGTEXT NULL,
    password_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_users_username (username),
    KEY idx_users_email (email),
    KEY idx_users_mascot_id (mascot_id),
    CONSTRAINT fk_users_mascots
        FOREIGN KEY (mascot_id) REFERENCES mascots (id)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_users
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE refresh_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    refresh_token_hash VARCHAR(255) NOT NULL,
    expires DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_refresh_tokens_user_id (user_id),
    UNIQUE KEY uk_refresh_tokens_refresh_token_hash (refresh_token_hash),
    CONSTRAINT fk_refresh_tokens_users
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE threads (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_threads_name (name)
) ENGINE=InnoDB;

CREATE TABLE posts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NULL,
    thread_id BIGINT NULL,
    title VARCHAR(255) NOT NULL,
    body LONGTEXT NULL,
    slug VARCHAR(255) NOT NULL,
    status ENUM('DRAFT', 'PUBLISHED', 'ARCHIVED') NOT NULL,
    created_at DATETIME(6) NULL,
    last_updated_at DATETIME(6) NULL,
    published_at DATETIME(6) NULL,
    PRIMARY KEY (id),
    KEY idx_posts_user_id (user_id),
    KEY idx_posts_thread_id (thread_id),
    KEY idx_posts_slug (slug),
    CONSTRAINT fk_posts_users
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_posts_threads
        FOREIGN KEY (thread_id) REFERENCES threads (id)
) ENGINE=InnoDB;

CREATE TABLE comments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NULL,
    post_id BIGINT NULL,
    parent_id BIGINT NULL,
    body VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NULL,
    last_updated_at DATETIME(6) NULL,
    is_deleted BIT(1) NOT NULL DEFAULT b'0',
    PRIMARY KEY (id),
    KEY idx_comments_user_id (user_id),
    KEY idx_comments_post_id (post_id),
    KEY idx_comments_parent_id (parent_id),
    CONSTRAINT fk_comments_users
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_comments_posts
        FOREIGN KEY (post_id) REFERENCES posts (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_comments_parent
        FOREIGN KEY (parent_id) REFERENCES comments (id)
        ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT INTO mascots (id, name, img_url) VALUES
    (1, 'Byte', 'https://example.com/mascots/byte.png'),
    (2, 'Pixel', 'https://example.com/mascots/pixel.png'),
    (3, 'Orbit', 'https://example.com/mascots/orbit.png');

-- Password for all seeded users is: password
INSERT INTO users (id, username, email, mascot_id, bio, password_hash) VALUES
    (1, 'admin', 'admin@example.com', 1, 'Admin account for managing the blog.', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiYx6tv2HKGE/oS0QlmRpxR7KvZkEVS'),
    (2, 'andrew', 'andrew@example.com', 2, 'Writes about backend APIs and project notes.', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiYx6tv2HKGE/oS0QlmRpxR7KvZkEVS'),
    (3, 'reader', 'reader@example.com', 3, 'Follows posts and leaves comments.', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiYx6tv2HKGE/oS0QlmRpxR7KvZkEVS');

INSERT INTO user_roles (user_id, role) VALUES
    (1, 'ROLE_USER'),
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_USER');

INSERT INTO threads (id, name) VALUES
    (1, 'Announcements'),
    (2, 'Backend'),
    (3, 'General');

INSERT INTO posts (
    id,
    user_id,
    thread_id,
    title,
    body,
    slug,
    status,
    created_at,
    last_updated_at,
    published_at
) VALUES
    (
        1,
        1,
        1,
        'Welcome to the Blog',
        'This first post seeds the application with a published announcement.',
        'welcome-to-the-blog',
        'PUBLISHED',
        '2026-06-12 09:00:00.000000',
        '2026-06-12 09:00:00.000000',
        '2026-06-12 09:00:00.000000'
    ),
    (
        2,
        2,
        2,
        'Building the Blog API',
        'This post describes the API entities, services, repositories, and controllers.',
        'building-the-blog-api',
        'PUBLISHED',
        '2026-06-12 09:30:00.000000',
        '2026-06-12 09:45:00.000000',
        '2026-06-12 09:30:00.000000'
    ),
    (
        3,
        2,
        3,
        'Draft Ideas',
        'This draft exists to test filtering and status handling later.',
        'draft-ideas',
        'DRAFT',
        '2026-06-12 10:00:00.000000',
        '2026-06-12 10:00:00.000000',
        NULL
    );

INSERT INTO comments (
    id,
    user_id,
    post_id,
    parent_id,
    body,
    created_at,
    last_updated_at,
    is_deleted
) VALUES
    (
        1,
        3,
        1,
        NULL,
        'This is a useful starter post.',
        '2026-06-12 10:15:00.000000',
        '2026-06-12 10:15:00.000000',
        b'0'
    ),
    (
        2,
        2,
        1,
        1,
        'Thanks for reading.',
        '2026-06-12 10:20:00.000000',
        '2026-06-12 10:20:00.000000',
        b'0'
    ),
    (
        3,
        1,
        2,
        NULL,
        'The API seed data is ready for local testing.',
        '2026-06-12 10:30:00.000000',
        '2026-06-12 10:30:00.000000',
        b'0'
    );

ALTER TABLE mascots AUTO_INCREMENT = 4;
ALTER TABLE users AUTO_INCREMENT = 4;
ALTER TABLE refresh_tokens AUTO_INCREMENT = 1;
ALTER TABLE threads AUTO_INCREMENT = 4;
ALTER TABLE posts AUTO_INCREMENT = 4;
ALTER TABLE comments AUTO_INCREMENT = 4;
