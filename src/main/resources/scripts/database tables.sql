CREATE TABLE `webshop`.`roles`
(
    `Id_role` BIGINT      NOT NULL AUTO_INCREMENT,
    `title`   VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Id_role`),
    UNIQUE INDEX `Id_role_UNIQUE` (`Id_role` ASC) VISIBLE,
    UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`users`
(
    `idUsers`              BIGINT       NOT NULL AUTO_INCREMENT,
    `firstName`            VARCHAR(45)  NOT NULL,
    `lastName`             VARCHAR(45)  NOT NULL,
    `login`                VARCHAR(45)  NOT NULL,
    `email`                VARCHAR(150) NOT NULL,
    `password`             VARCHAR(100) NOT NULL,
    `link_image`           VARCHAR(200) NOT NULL,
    `receiving_newsletter` TINYINT(1)   NOT NULL,
    `Id_role`              BIGINT       NOT NULL,
    PRIMARY KEY (`idUsers`),
    UNIQUE INDEX `idUsers_UNIQUE` (`idUsers` ASC) VISIBLE,
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `Id_users_roles_idx` (`Id_role` ASC) VISIBLE,
    CONSTRAINT `Id_users_roles`
        FOREIGN KEY (`Id_role`)
            REFERENCES `webshop`.`roles` (`Id_role`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`addresses`
(
    `Id_address`  BIGINT      NOT NULL AUTO_INCREMENT,
    `country`     VARCHAR(45) NOT NULL,
    `city`        VARCHAR(45) NOT NULL,
    `street`      VARCHAR(45) NOT NULL,
    `floor`       INT         NOT NULL,
    `post`        INT         NOT NULL,
    `houseNumber` INT         NOT NULL,
    PRIMARY KEY (`Id_address`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

ALTER TABLE `webshop`.`users`
    ADD COLUMN `Id_address` BIGINT NULL AFTER `Id_role`,
    ADD INDEX `Id_users_addresses_idx` (`Id_address` ASC) VISIBLE;
;
ALTER TABLE `webshop`.`users`
    ADD CONSTRAINT `Id_users_addresses`
        FOREIGN KEY (`Id_address`)
            REFERENCES `webshop`.`addresses` (`Id_address`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `webshop`.`categories`
(
    `idcategory`  BIGINT       NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(45)  NOT NULL,
    `description` VARCHAR(200) NULL,
    PRIMARY KEY (`idcategory`),
    UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`producers`
(
    `idproducers` BIGINT      NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(45) NOT NULL,
    `city`        VARCHAR(45) NOT NULL,
    `country`     VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idproducers`),
    UNIQUE INDEX `idproducers_UNIQUE` (`idproducers` ASC) VISIBLE,
    UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`electronics`
(
    `idElectronic`   BIGINT       NOT NULL AUTO_INCREMENT,
    `ElectronicName` VARCHAR(45)  NOT NULL,
    `Price`          DECIMAL      NOT NULL,
    `Image_link`     VARCHAR(100) NOT NULL,
    `Description`    VARCHAR(200) NULL,
    `ScreenDiagonal` DOUBLE       NOT NULL,
    `idproducer`     BIGINT       NOT NULL,
    `idcategory`     BIGINT       NOT NULL,
    PRIMARY KEY (`idElectronic`),
    UNIQUE INDEX `idElectronic_UNIQUE` (`idElectronic` ASC) VISIBLE,
    UNIQUE INDEX `ElectronicName_UNIQUE` (`ElectronicName` ASC) VISIBLE,
    INDEX `id_producer_electronic_idx` (`idproducer` ASC) VISIBLE,
    INDEX `id_category_electronic_idx` (`idcategory` ASC) VISIBLE,
    CONSTRAINT `id_producer_electronic`
        FOREIGN KEY (`idproducer`)
            REFERENCES `webshop`.`producers` (`idproducers`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `id_category_electronic`
        FOREIGN KEY (`idcategory`)
            REFERENCES `webshop`.`categories` (`idcategory`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`orders`
(
    `idOrder`  BIGINT      NOT NULL AUTO_INCREMENT,
    `idUser`   BIGINT      NOT NULL,
    `Status`   VARCHAR(45) NOT NULL,
    `State`    VARCHAR(45) NOT NULL,
    `DateTime` DATETIME    NOT NULL,
    PRIMARY KEY (`idOrder`),
    UNIQUE INDEX `idOrder_UNIQUE` (`idOrder` ASC) VISIBLE,
    INDEX `id_user_order_idx` (`idUser` ASC) VISIBLE,
    CONSTRAINT `id_user_order`
        FOREIGN KEY (`idUser`)
            REFERENCES `webshop`.`users` (`idUsers`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `webshop`.`orderdetails`
(
    `idorderdetails` BIGINT  NOT NULL AUTO_INCREMENT,
    `idElectronic`   BIGINT  NOT NULL,
    `Amount`         INT     NOT NULL,
    `Price`          DECIMAL NOT NULL,
    `idOrder`        BIGINT  NOT NULL,
    PRIMARY KEY (`idorderdetails`),
    UNIQUE INDEX `idorderdetails_UNIQUE` (`idorderdetails` ASC) VISIBLE,
    UNIQUE INDEX `idOrder_UNIQUE` (`idOrder` ASC) VISIBLE,
    CONSTRAINT `Id_order_order_details`
        FOREIGN KEY (`idOrder`)
            REFERENCES `webshop`.`orders` (`idOrder`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;