java-mybatis �����Զ����ɹ��ߣ�maven��ʽ��

maven ִ������
mybatis-generator:generate


https://www.cnblogs.com/xujishou/p/6306765.htmls
1. mysql5.7 ��װ
2. ����
(1)�ر�server����
cd D:\software\mysql\mysql5.7\bin
mysqladmin -u root -p shutdown

(2)����server����
cd D:\software\mysql\mysql5.7\bin

���ƣ�D:\software\mysql\mysql5.7\my-default.ini ��D:\software\mysql\mysql5.7\bin\my.ini
�޸ģ�my.ini����������:
[mysqld]
basedir=D:\software\mysql\mysql5.7
datadir=D:\software\mysql\data
port=3306

ִ�У�mysqld install MYSQL --defaults-file="D:\software\mysql\mysql5.7\bin\my.ini"
ִ�У�net start mysql57 (�ӣ�������mysql��Ϊ��MySQL57)
ִ�У�mysqld --initialize-insecture
ִ�У�net start mysql57

3. �����û�
CREATE USER 'test'@'%' IDENTIFIED BY 'test@123';

4. ��Ȩ
GRANT SELECT, INSERT ON mq.* TO 'dog'@'localhost';
grant all privileges on mq.* to 'dog'@localhost;
flush privileges;

5 ����������û�����
SET PASSWORD FOR 'test'@'test' = PASSWORD("dog");

6 �����û�Ȩ��
REVOKE SELECT ON mq.* FROM 'dog2'@'localhost';
 
7 ɾ���û�
DROP USER 'username'@'host';

8 �鿴�û�����Ȩ
show grants for dog@localhost;


###############################################
1 �������ݿ�
create database test;

2 �鿴���ݿ�
show databases;





 
