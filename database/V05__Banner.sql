USE `shop`;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `banner`;

CREATE TABLE IF NOT EXISTS `shop`.`banner` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Categories
-- -----------------------------------------------------
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/e0/98/77/74e134ee6b9776514aa85b72bd54985e.jpg');
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/0c/9d/b5/74675cc9e6c6cceb987dcf8b94d5a76e.jpg');
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/0c/9d/b5/74675cc9e6c6cceb987dcf8b94d5a76e.jpg');
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/fb/67/08/c4ffbdc23087c7c62f0ee4ab335febcd.png');
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/f6/35/7d/b7de22d7bcdad565c68042744aaeb0e1.png');
INSERT INTO banner(image_url) VALUES ('https://salt.tikicdn.com/cache/w750/ts/tikimsp/56/aa/10/e0cee75883d82f78713ad8c26fdb0927.png');
