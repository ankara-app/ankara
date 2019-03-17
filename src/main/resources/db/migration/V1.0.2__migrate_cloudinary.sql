ALTER TABLE company
DROP COLUMN `picture`;

ALTER TABLE company
ADD COLUMN `picture_url` LONGTEXT NULL AFTER `version`;
