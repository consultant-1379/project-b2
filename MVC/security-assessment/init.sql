create user 'securityuser'@'%' identified by 'password'; -- Creates the user
grant all on security_db.* to 'securityuser'@'%'; -- Gives all privileges to the new user on the newly created database