CREATE TABLE `parser`.`entry` (
  `entry_id`      INT        NOT NULL AUTO_INCREMENT,
  `content`       TEXT(1024) NOT NULL,
  `creation_date` DATETIME   NOT NULL,
  PRIMARY KEY (`entry_id`)
);
