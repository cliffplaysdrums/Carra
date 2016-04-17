-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema scheduler
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema scheduler
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduler` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `scheduler` ;

-- -----------------------------------------------------
-- Table `scheduler`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler`.`User` ;

CREATE TABLE IF NOT EXISTS `scheduler`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `username` VARCHAR(45) NULL COMMENT '',
  `password` VARCHAR(45) NULL COMMENT '',
  `email` VARCHAR(45) NULL COMMENT '',
  `isAdmin` TINYINT(1) NULL COMMENT '',
  PRIMARY KEY (`userId`)  COMMENT '',
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`Event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler`.`Event` ;

CREATE TABLE IF NOT EXISTS `scheduler`.`Event` (
  `eventId` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `eventName` VARCHAR(45) NULL COMMENT '',
  `eventDescription` VARCHAR(145) NULL COMMENT '',
  `eventDate` VARCHAR(45) NULL COMMENT '',
  `eventTime` VARCHAR(45) NULL COMMENT '',
  `eventPriority` VARCHAR(45) NULL COMMENT '',
  `rescheduled` TINYINT(1) NULL COMMENT '',
  `creator` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`eventId`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`UserEvent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler`.`UserEvent` ;

CREATE TABLE IF NOT EXISTS `scheduler`.`UserEvent` (
  `usereventId` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `userId` INT NULL COMMENT '',
  `eventId` INT NULL COMMENT '',
  PRIMARY KEY (`usereventId`)  COMMENT '',
  CONSTRAINT `fkUserEvent`
    FOREIGN KEY (`userId`)
    REFERENCES `scheduler`.`User` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fkEventUser`
    FOREIGN KEY (`eventId`)
    REFERENCES `scheduler`.`Event` (`eventId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`Department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler`.`Department` ;

CREATE TABLE IF NOT EXISTS `scheduler`.`Department` (
  `deptId` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `deptName` VARCHAR(45) NULL COMMENT '',
  `deptManager` INT NULL COMMENT '',
  PRIMARY KEY (`deptId`)  COMMENT '',
  UNIQUE INDEX `departmentName_UNIQUE` (`deptName` ASC)  COMMENT '',
  UNIQUE INDEX `deptManager_UNIQUE` (`deptManager` ASC)  COMMENT '',
  CONSTRAINT `fkDeptManager`
    FOREIGN KEY (`deptManager`)
    REFERENCES `scheduler`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`UserDept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduler`.`UserDept` ;

CREATE TABLE IF NOT EXISTS `scheduler`.`UserDept` (
  `userdeptId` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `userId` INT NULL COMMENT '',
  `deptId` INT NULL COMMENT '',
  PRIMARY KEY (`userdeptId`)  COMMENT '',
  CONSTRAINT `fkUserDeptfor`
    FOREIGN KEY (`userId`)
    REFERENCES `scheduler`.`User` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fkDeptUserfor`
    FOREIGN KEY (`deptId`)
    REFERENCES `scheduler`.`Department` (`deptId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
