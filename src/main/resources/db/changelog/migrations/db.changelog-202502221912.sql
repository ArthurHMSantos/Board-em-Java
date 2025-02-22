--liquibase formatted sql
--changeset arthur:202502221912
--comment:  cards table create

-- Adicionar colunas entered_at e moved_at na tabela CARDS
ALTER TABLE CARDS ADD COLUMN entered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE CARDS ADD COLUMN moved_at TIMESTAMP NULL;


--rollback ALTER TABLE CARDS DROP COLUMN entered_at;
--rollback ALTER TABLE CARDS DROP COLUMN moved_at;