mysql -u root -e "CREATE DATABASE hhspring DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;"
mysql -u root -e "CREATE USER 'hhuser' IDENTIFIED BY 'boem'; grant all on hhspring.* to hhuser;"
mysql -u root -e "CREATE USER 'hhuser'@'localhost' IDENTIFIED BY 'boem'; grant all on hhspring.* to hhuser@localhost;"