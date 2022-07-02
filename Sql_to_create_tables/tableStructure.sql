drop schema if exists family_tree;

create schema family_tree;

use family_tree;

drop table if exists person;
CREATE TABLE `person` (
  `personId` int(11) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
	PRIMARY KEY (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists media;

CREATE TABLE `media` (
  `mediaId` int(11) NOT NULL auto_increment,
  `filename` varchar(100) NOT NULL,
     PRIMARY KEY (`mediaId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists notes;

CREATE TABLE `notes` (
  `noteId` int(11) NOT NULL auto_increment,
  `personId` int(11) NOT NULL,
  `note` varchar(500) NOT NULL,
  `date` DATETIME DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`noteId`),
  CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists people_in_media;

CREATE TABLE `people_in_media` (
  `id` int(11) NOT NULL auto_increment,
  `personId` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
   PRIMARY KEY (`id`),
  CONSTRAINT `people_in_media_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`),
  CONSTRAINT `people_in_media_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`mediaId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists `references`;

CREATE TABLE `references` (
  `referenceId` int(11) NOT NULL auto_increment,
  `personId` int(11) NOT NULL,
  `reference` varchar(500) NOT NULL,
    `date` DATETIME DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`referenceId`),
  CONSTRAINT `references_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists `tags`;

CREATE TABLE `tags` (
  `tagId` int(11) NOT NULL auto_increment,
  `mediaId` int(11) NOT NULL,
  `tag` varchar(200) NOT NULL,
   PRIMARY KEY (`tagId`),
  CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`mediaId`) REFERENCES `media` (`mediaId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists descendants;

CREATE TABLE `descendants` (
  `descendentId` int(11) NOT NULL auto_increment,
  `parentId` int(11) NOT NULL,
  `childId` int(11) NOT NULL,
   PRIMARY KEY (`descendentId`),
  CONSTRAINT `descendents_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `person` (`personId`),
  CONSTRAINT `descendents_ibfk_2` FOREIGN KEY (`childId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists partnering;
CREATE TABLE `partnering` (
  `id` int(11) NOT NULL auto_increment,
  `partner_1_id` int(11) NOT NULL,
  `partner_2_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
  CONSTRAINT `partnering_ibfk_1` FOREIGN KEY (`partner_1_id`) REFERENCES `person` (`personId`),
  CONSTRAINT `partnering_ibfk_2` FOREIGN KEY (`partner_2_id`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists dissolutions;

CREATE TABLE `dissolutions` (
  `id` int(11) NOT NULL auto_increment,
  `partner_1_id` int(11) NOT NULL,
  `partner_2_id` int(11) NOT NULL,
   PRIMARY KEY (`id`),
  CONSTRAINT `dissolutions_ibfk_1` FOREIGN KEY (`partner_1_id`) REFERENCES `person` (`personId`),
  CONSTRAINT `dissolutions_ibfk_2` FOREIGN KEY (`partner_2_id`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

