-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`LEAGUE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`LEAGUE` (
  `ID_LEAGUE` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  `YEAR` INT NULL,
  PRIMARY KEY (`ID_LEAGUE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TOURNAMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TOURNAMENT` (
  `ID_TOURNAMENT` INT NOT NULL AUTO_INCREMENT,
  `DIVISION` VARCHAR(45) NULL,
  `IS_PROFESSIONAL` TINYINT(1) NULL,
  PRIMARY KEY (`ID_TOURNAMENT`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TEAM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TEAM` (
  `ID_TEAM` INT NOT NULL AUTO_INCREMENT,
  `ID_LEAGUE` INT NOT NULL,
  `ID_TOURNAMENT` INT NOT NULL,
  `NAME` VARCHAR(45) NULL,
  `FUNDATION` INT NULL,
  `LOCATION` VARCHAR(45) NULL,
  `STATUS` TINYINT(1) NULL,
  PRIMARY KEY (`ID_TEAM`),
  INDEX `fk_TEAM_LEAGUE1_idx` (`ID_LEAGUE` ASC),
  INDEX `fk_TEAM_TOURNAMENT1_idx` (`ID_TOURNAMENT` ASC),
  CONSTRAINT `fk_TEAM_LEAGUE1`
    FOREIGN KEY (`ID_LEAGUE`)
    REFERENCES `mydb`.`LEAGUE` (`ID_LEAGUE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TEAM_TOURNAMENT1`
    FOREIGN KEY (`ID_TOURNAMENT`)
    REFERENCES `mydb`.`TOURNAMENT` (`ID_TOURNAMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PLAYER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PLAYER` (
  `ID_PLAYER` INT NOT NULL AUTO_INCREMENT,
  `TEAM_ID_TEAM` INT NOT NULL,
  `NAME` VARCHAR(45) NULL,
  `EDAD` INT NULL,
  `NUMBER` INT NULL,
  `POSITION` VARCHAR(45) NULL,
  `STATUS` TINYINT(1) NULL,
  PRIMARY KEY (`ID_PLAYER`),
  INDEX `fk_PLAYER_TEAM_idx` (`TEAM_ID_TEAM` ASC),
  CONSTRAINT `fk_PLAYER_TEAM`
    FOREIGN KEY (`TEAM_ID_TEAM`)
    REFERENCES `mydb`.`TEAM` (`ID_TEAM`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
