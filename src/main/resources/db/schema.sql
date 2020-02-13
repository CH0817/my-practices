CREATE TABLE `user`
(
    id          VARCHAR(32) NOT NULL,
    email       VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(68) NOT NULL,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE `account_type`
(
    id          VARCHAR(32) NOT NULL,
    name        VARCHAR(10) NOT NULL,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE `account`
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

CREATE TABLE `item`
(
    id          VARCHAR(32) NOT NULL,
    name        VARCHAR(50) NOT NULL,
    removed     BIT         NOT NULL DEFAULT 0,
    create_date DATETIME    NOT NULL,
    update_date DATETIME,
    user_id     VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE `trade`
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

CREATE TABLE `functions`
(
    id        VARCHAR(32) NOT NULL,
    name      VARCHAR(10) NOT NULL,
    url       VARCHAR(150) UNIQUE,
    icon      VARCHAR(30),
    parent_id VARCHAR(32),
    sorted    SMALLINT,
    PRIMARY KEY (id)
);

CREATE TABLE `user_functions`
(
    user_id     VARCHAR(32) NOT NULL,
    functions_id VARCHAR(32) NOT NULL,
    PRIMARY KEY (user_id, functions_id)
);

CREATE TABLE `register_token`
(
    email       VARCHAR(50) NOT NULL UNIQUE,
    token       VARCHAR(32) NOT NULL,
    expire_date DATE        NOT NULL,
    PRIMARY KEY (email)
);