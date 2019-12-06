SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `account_type`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `item`;
DROP TABLE IF EXISTS `trade`;

CREATE TABLE IF NOT EXISTS `user`
(
    id          VARCHAR(32) NOT NULL,
    email       VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(50) NOT NULL,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS `account_type`
(
    id          VARCHAR(32) NOT NULL,
    name        VARCHAR(10) NOT NULL UNIQUE,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE IF NOT EXISTS `account`
(
    id              VARCHAR(32)    NOT NULL,
    name            VARCHAR(50)    NOT NULL,
    money           DECIMAL(10, 2) NOT NULL,
    removed         BIT            NOT NULL DEFAULT 0,
    create_date     DATETIME       NOT NULL,
    update_date     DATETIME,
    user_id         VARCHAR(32)    NOT NULL,
    account_type_id VARCHAR(32)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (account_type_id) REFERENCES `account_type` (id)
);

CREATE TABLE IF NOT EXISTS `item`
(
    id          VARCHAR(32) NOT NULL,
    name        VARCHAR(50) NOT NULL UNIQUE,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE IF NOT EXISTS `trade`
(
    id          VARCHAR(32)    NOT NULL,
    money       DECIMAL(10, 2) NOT NULL,
    trade_type  VARCHAR(1)     NOT NULL,
    trade_date  DATE           NOT NULL,
    removed     BIT            NOT NULL DEFAULT 0,
    create_date DATETIME       NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(32)    NOT NULL,
    account_id  VARCHAR(32)    NOT NULL,
    item_id     VARCHAR(32)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (account_id) REFERENCES `account` (id),
    FOREIGN KEY (item_id) REFERENCES `item` (id)
);

CREATE UNIQUE INDEX name_account_type_index ON `account` (name, account_type_id);

SET foreign_key_checks = 1;