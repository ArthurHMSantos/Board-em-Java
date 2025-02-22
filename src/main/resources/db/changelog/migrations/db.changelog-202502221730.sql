--liquibase formatted sql
--changeset arthur:202502221730
--comment: boards table create

CREATE TABLE BOARDS(
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS