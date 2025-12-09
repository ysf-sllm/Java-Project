--TP5: Langage de contrôle de données 
--Question 1
create user user1 identified by analyst2024;
create user user2 identified by developer2024;
-- Question 2
grant create session to  user1;
grant create session to  user2;
--Qustion 3
--création du rôle
create role data_analyst_role;
--accorder le privilège de lecture  
 grant select any table to data_analyst_role;
 --accorder le privilège de mise à jour
 grant update any table to data_analyst_role;
 --Question 4
create role developper_role; 
grant create table  to developper_role; 
grant alter any table to developper_role ;
grant drop any table to developper_role ;
--Question 5
grant data_analyst_role to user1; 
grant data_analyst_role,developper_role to user2 ;
--Question 6
revoke update any table from user1; 
--Question 7
alter user user2 account lock;
--Question 8
create user user_test identified by test;
ALTER USER user_test ACCOUNT LOCK; 
ALTER USER user_test ACCOUNT UNLOCK; 
--Question 9
create role manager_role; 
grant all privileges on hr.employees to manager_role;
--Question 10
create user manager identified by manager123;
grant manager_role to manager;
--Question 11
select username from all_users;
--Question 12
select granted_role 
from dba_role_privs
where grantee = 'user2';
--Question 13
drop user user1 ;
drop user user2 ;
