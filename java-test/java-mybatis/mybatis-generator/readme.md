官网文档：http://www.mybatis.org/mybatis-3/zh/index.html

mybatis-generator，详细见：https://www.cnblogs.com/yjmyzz/p/4210554.html
执行：mvn mybatis-generator:generate


mysql5.7使用，详见：https://www.cnblogs.com/xujishou/p/6306765.htmls
1、安装MySQL5.7
2、
(1)
cd D:\software\mysql\mysql5.7\bin
mysqladmin -u root -p shutdown

(2)
cd D:\software\mysql\mysql5.7\bin

替换：D:\software\mysql\mysql5.7\my-default.ini 到 D:\software\mysql\mysql5.7\bin\my.ini
在my.ini中新增
[mysqld]
basedir=D:\software\mysql\mysql5.7
datadir=D:\software\mysql\data
port=3306

执行  mysqld install MYSQL --defaults-file="D:\software\mysql\mysql5.7\bin\my.ini"
执行 mysqld --initialize-insecture
执行 net start mysql57

3. 
CREATE USER 'test'@'%' IDENTIFIED BY 'test@123';

4.
GRANT SELECT, INSERT ON mq.* TO 'dog'@'localhost';
grant all privileges on mq.* to 'dog'@localhost;
flush privileges;

5 
SET PASSWORD FOR 'test'@'test' = PASSWORD("dog");

6 
REVOKE SELECT ON mq.* FROM 'dog2'@'localhost';
 
7
DROP USER 'username'@'host';

8
show grants for dog@localhost;


###############################################
1 
create database test;

2
show databases;





 
