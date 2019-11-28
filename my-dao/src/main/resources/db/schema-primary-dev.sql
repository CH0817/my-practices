SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `account_type`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `item`;
DROP TABLE IF EXISTS `trade`;

CREATE TABLE IF NOT EXISTS `user`
(
    id          VARCHAR(36) NOT NULL,
    email       VARCHAR(50) NOT NULL,
    password    VARCHAR(50) NOT NULL,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS `account_type`
(
    id          VARCHAR(36) NOT NULL,
    name        VARCHAR(10) NOT NULL,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE IF NOT EXISTS `account`
(
    id              VARCHAR(36)    NOT NULL,
    name            VARCHAR(50)    NOT NULL,
    money           DECIMAL(10, 2) NOT NULL,
    create_date     DATETIME       NOT NULL,
    update_date     DATETIME,
    user_id         VARCHAR(36)    NOT NULL,
    account_type_id VARCHAR(36)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (account_type_id) REFERENCES `account_type` (id)
);

CREATE TABLE IF NOT EXISTS `item`
(
    id          VARCHAR(36) NOT NULL,
    name        VARCHAR(50) NOT NULL,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE IF NOT EXISTS `trade`
(
    id          VARCHAR(36)    NOT NULL,
    money       DECIMAL(10, 2) NOT NULL,
    trade_type  VARCHAR(1)     NOT NULL, # 1:收入，2:支出，3:轉帳
    trade_date  DATE           NOT NULL,
    create_date DATETIME       NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(36)    NOT NULL,
    account_id  VARCHAR(36)    NOT NULL,
    item_id     VARCHAR(36)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (account_id) REFERENCES `account` (id),
    FOREIGN KEY (item_id) REFERENCES `item` (id)
);

SET foreign_key_checks = 1;