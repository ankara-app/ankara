ALTER TABLE `estimate` ADD COLUMN `issue_date` DATE NULL AFTER `customer_id`;
UPDATE estimate set issue_date=time_created;
ALTER TABLE `estimate` CHANGE COLUMN `issue_date` `issue_date` DATE NOT NULL;