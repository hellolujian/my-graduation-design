drop database if exists resource_management;
create database resource_management;
use resource_management;

create table user (
    id int not null auto_increment comment '用户id',
    username varchar(32) not null comment '用户名',
    password varchar(32) not null comment '用户密码,MD5加密',
    email varchar(32) default null comment '用户邮箱',
    phone varchar(32) default null comment '用户手机号码',
    avatar varchar(128) not null default '/image/...' comment '用户头像', 
    points int not null default 0 comment '积分',
    tags json comment '用户标签,json格式',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key user_name_unique(username) using btree
) comment '用户表' ;

insert into user(username,password,email,phone,avatar,tags) values('陆健','123456','1370256381@qq.com','18852852189','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('小二','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java","sd"]');
insert into user(username,password,avatar,tags) values('张三','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('李四','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('王五','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('陈六','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('孙七','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('陆八','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');
insert into user(username,password,avatar,tags) values('阿九','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java","sdf","sdf"]');
insert into user(username,password,avatar,tags) values('老十','123456','http://127.0.0.1:8082/image/user_avatar/girl.jpg','["mysql","java"]');

create table resource_type (
    id int not null auto_increment comment '资源类型id',
    type_name varchar(32) not null comment '资源类型名称',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key type_name_unique(type_name) using btree
) comment '资源类型表' ;

insert into resource_type(type_name) values('工具类');
insert into resource_type(type_name) values('文档类');
insert into resource_type(type_name) values('代码类');
insert into resource_type(type_name) values('其他');

create table parent_category (
    id int not null auto_increment comment '父类别id',
    parent_category_name varchar(32) not null comment '父类别名称',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key parent_category_name_unique(parent_category_name) using btree
) auto_increment=10 comment '父类别' ;

insert into parent_category(parent_category_name) values('前端开发');
insert into parent_category(parent_category_name) values('后端开发');
insert into parent_category(parent_category_name) values('移动开发');
insert into parent_category(parent_category_name) values('数据库');
insert into parent_category(parent_category_name) values('云计算');
insert into parent_category(parent_category_name) values('大数据');
insert into parent_category(parent_category_name) values('操作系统');
insert into parent_category(parent_category_name) values('人工智能');

create table child_category (
    id int not null auto_increment comment '子类别id',
    parent_category_id int not null comment '父类别id',
    child_category_name varchar(32) not null comment '子类别名称',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id)
) auto_increment=100 comment '子类别' ;

insert into child_category(parent_category_id,child_category_name) values(10,'HTML/CSS');
insert into child_category(parent_category_id,child_category_name) values(10,'JavaScript');
insert into child_category(parent_category_id,child_category_name) values(10,'jQuery');
insert into child_category(parent_category_id,child_category_name) values(10,'Node.js');
insert into child_category(parent_category_id,child_category_name) values(10,'Bootstrp');
insert into child_category(parent_category_id,child_category_name) values(10,'Angular');
insert into child_category(parent_category_id,child_category_name) values(10,'ReactJS');
insert into child_category(parent_category_id,child_category_name) values(10,'Vue.js');
insert into child_category(parent_category_id,child_category_name) values(10,'Sass/Less');
insert into child_category(parent_category_id,child_category_name) values(10,'WebApp');
insert into child_category(parent_category_id,child_category_name) values(10,'前端工具');
insert into child_category(parent_category_id,child_category_name) values(10,'其他');
insert into child_category(parent_category_id,child_category_name) values(11,'PHP');
insert into child_category(parent_category_id,child_category_name) values(11,'Java');
insert into child_category(parent_category_id,child_category_name) values(11,'SpringBoot');
insert into child_category(parent_category_id,child_category_name) values(11,'Python');
insert into child_category(parent_category_id,child_category_name) values(11,'C');
insert into child_category(parent_category_id,child_category_name) values(11,'C++');
insert into child_category(parent_category_id,child_category_name) values(11,'Go');
insert into child_category(parent_category_id,child_category_name) values(11,'C#');
insert into child_category(parent_category_id,child_category_name) values(11,'Ruby');
insert into child_category(parent_category_id,child_category_name) values(11,'其他');
insert into child_category(parent_category_id,child_category_name) values(12,'Android');
insert into child_category(parent_category_id,child_category_name) values(12,'iOS');
insert into child_category(parent_category_id,child_category_name) values(12,'Unity 3D');
insert into child_category(parent_category_id,child_category_name) values(12,'Cocos2d-x');
insert into child_category(parent_category_id,child_category_name) values(12,'其他');
insert into child_category(parent_category_id,child_category_name) values(13,'MySQL');
insert into child_category(parent_category_id,child_category_name) values(13,'MongoDB');
insert into child_category(parent_category_id,child_category_name) values(13,'Oracle');
insert into child_category(parent_category_id,child_category_name) values(13,'SQL Server');
insert into child_category(parent_category_id,child_category_name) values(13,'Redis');
insert into child_category(parent_category_id,child_category_name) values(13,'其他');
insert into child_category(parent_category_id,child_category_name) values(14,'kubernetes');
insert into child_category(parent_category_id,child_category_name) values(14,'mesos');
insert into child_category(parent_category_id,child_category_name) values(14,'Docker');
insert into child_category(parent_category_id,child_category_name) values(14,'微服务');
insert into child_category(parent_category_id,child_category_name) values(14,'OpenStack');
insert into child_category(parent_category_id,child_category_name) values(14,'其他');
insert into child_category(parent_category_id,child_category_name) values(15,'spark');
insert into child_category(parent_category_id,child_category_name) values(15,'Hadoop');
insert into child_category(parent_category_id,child_category_name) values(15,'Hbase');
insert into child_category(parent_category_id,child_category_name) values(15,'Hive');
insert into child_category(parent_category_id,child_category_name) values(15,'Netty');
insert into child_category(parent_category_id,child_category_name) values(15,'flink');
insert into child_category(parent_category_id,child_category_name) values(15,'算法与数据结构');
insert into child_category(parent_category_id,child_category_name) values(15,'kafka');
insert into child_category(parent_category_id,child_category_name) values(15,'Storm');
insert into child_category(parent_category_id,child_category_name) values(15,'其他');
insert into child_category(parent_category_id,child_category_name) values(16,'DOS');
insert into child_category(parent_category_id,child_category_name) values(16,'Linux');
insert into child_category(parent_category_id,child_category_name) values(16,'MacOS');
insert into child_category(parent_category_id,child_category_name) values(16,'Solaris');
insert into child_category(parent_category_id,child_category_name) values(16,'Ubuntu');
insert into child_category(parent_category_id,child_category_name) values(16,'Ubuntu');
insert into child_category(parent_category_id,child_category_name) values(16,'Ubuntu');
insert into child_category(parent_category_id,child_category_name) values(16,'Ubuntu');
insert into child_category(parent_category_id,child_category_name) values(16,'其他');
insert into child_category(parent_category_id,child_category_name) values(17,'机器学习');
insert into child_category(parent_category_id,child_category_name) values(17,'深度学习');
insert into child_category(parent_category_id,child_category_name) values(17,'搜索引擎');
insert into child_category(parent_category_id,child_category_name) values(17,'其他');

create table tag (
    id int not null auto_increment comment '标签id',
    tag_name varchar(32) not null comment '标签名称',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key tag_name_unique(tag_name) using btree
) comment '标签表' ;

insert into tag(tag_name) values('java');
insert into tag(tag_name) values('mysql');
insert into tag(tag_name) values('c');
insert into tag(tag_name) values('人工智能');
insert into tag(tag_name) values('大数据');
insert into tag(tag_name) values('云计算');
insert into tag(tag_name) values('javascript');
insert into tag(tag_name) values('android');
insert into tag(tag_name) values('springboot');
insert into tag(tag_name) values('springcloud');
insert into tag(tag_name) values('vue');
insert into tag(tag_name) values('docker');
insert into tag(tag_name) values('微服务');

create table resource_suffix (
    id int not null auto_increment comment '资源后缀id',
    suffix_name varchar(32) not null comment '资源后缀名',
    suffix_image varchar(128) not null default 'image' comment '后缀名图片',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key suffix_name_unique(suffix_name) using btree
) comment '资源后缀名表' ;

insert into resource_suffix(suffix_name,suffix_image) values('.doc','http://127.0.0.1:8082/image/suffix_icon/doc.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.docx','http://127.0.0.1:8082/image/suffix_icon/docx.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.ppt','http://127.0.0.1:8082/image/suffix_icon/ppt.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.pptx','http://127.0.0.1:8082/image/suffix_icon/pptx.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.pdf','http://127.0.0.1:8082/image/suffix_icon/pdf.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.txt','http://127.0.0.1:8082/image/suffix_icon/txt.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.zip','http://127.0.0.1:8082/image/suffix_icon/zip.svg');
insert into resource_suffix(suffix_name,suffix_image) values('.rar','http://127.0.0.1:8082/image/suffix_icon/rar.svg');

create table resource (
    id int not null auto_increment comment '资源id',
    resource_title varchar(32) not null comment '资源标题',
    resource_type_id int not null comment '资源类型id',
    resource_category_id int not null comment '资源类别id',
    resource_suffix_name varchar(32) comment '资源后缀名',
    user_id int not null comment '用户id',
    tag_list json default null comment '资源自定义标签,json格式',
    check_status tinyint not null default 0 comment '审核状态：0未审核,1审核通过',
    resource_position text not null comment '资源所在位置',
    resource_size varchar(32) comment '资源大小',
    points int not null default 0 comment '下载资源所需积分',
    description varchar(255) not null comment '资源描述',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    key user_id_index (user_id) using btree comment '便于找出某个用户上传的资源'
) comment '资源表' ;


insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("a",1,101,'.doc',1,'["mysql","java"]',0,"a","0KB",0,"a");
insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("b",1,102,'.doc',2,'["mysql","java"]',0,"a","0KB",0,"a");
insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("c",1,103,'.doc',3,'["mysql","java"]',0,"a","0KB",0,"a");
insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("d",1,104,'.doc',4,'["mysql","java"]',0,"a","0KB",0,"a");
insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("e",1,105,'.doc',5,'["mysql","java"]',0,"a","0KB",0,"a");
insert into resource(resource_title,resource_type_id,resource_category_id,resource_suffix_name,user_id,tag_list,check_status,resource_position,resource_size,points,description) values("f",1,155,'.doc',5,'["mysql","java"]',0,"a","0KB",0,"a");

create table collection (
    id int not null auto_increment comment '收藏id',
    user_id int not null comment '用户id',
    resource_id int not null comment '资源id',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    key user_id_index (user_id) using btree comment '便于找出某个用户收藏的资源'
) comment '用户收藏表' ;

insert into collection(user_id,resource_id) values(1,2);
insert into collection(user_id,resource_id) values(1,3);
insert into collection(user_id,resource_id) values(1,4);
insert into collection(user_id,resource_id) values(1,5);
insert into collection(user_id,resource_id) values(2,1);
insert into collection(user_id,resource_id) values(2,3);
insert into collection(user_id,resource_id) values(2,4);
insert into collection(user_id,resource_id) values(2,5);
insert into collection(user_id,resource_id) values(3,1);
insert into collection(user_id,resource_id) values(3,2);
insert into collection(user_id,resource_id) values(3,4);
insert into collection(user_id,resource_id) values(3,5);
insert into collection(user_id,resource_id) values(4,1);
insert into collection(user_id,resource_id) values(4,2);
insert into collection(user_id,resource_id) values(4,3);
insert into collection(user_id,resource_id) values(4,5);
insert into collection(user_id,resource_id) values(5,1);
insert into collection(user_id,resource_id) values(5,2);
insert into collection(user_id,resource_id) values(5,3);
insert into collection(user_id,resource_id) values(5,4);

create table download (
    id int not null auto_increment comment '下载id',
    user_id int not null comment '用户id',
    resource_id int not null comment '资源id',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    key user_id_index (user_id) using btree comment '便于找出某个用户下载的资源'
) comment '用户资源下载表' ;

insert into download(user_id,resource_id) values(1,2);
insert into download(user_id,resource_id) values(1,3);
insert into download(user_id,resource_id) values(2,3);
insert into download(user_id,resource_id) values(2,4);
insert into download(user_id,resource_id) values(3,4);
insert into download(user_id,resource_id) values(3,5);
insert into download(user_id,resource_id) values(4,5);
insert into download(user_id,resource_id) values(4,1);
insert into download(user_id,resource_id) values(5,1);
insert into download(user_id,resource_id) values(5,2);

create table remark (
    id int not null auto_increment comment '评论id',
    user_id int not null comment '用户id',
    resource_id int not null comment '资源id',
    content text comment '评论内容',
    score tinyint not null default 0 comment '评分',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '最后一次更新时间',
    primary key(id),
    unique key user_id_resource_id_unique(user_id,resource_id) using btree,
    key user_id_resource_id_index (user_id,resource_id) using btree comment '便于找出某个用户对某个资源的评语'
) comment '评论表' ;

insert into remark(user_id,resource_id,content,score) values(1,2,"我是1,2还不错",2);
insert into remark(user_id,resource_id,content,score) values(1,3,"我是1,3还不错",3);
insert into remark(user_id,resource_id,content,score) values(2,3,"我是2,3还不错",3);
insert into remark(user_id,resource_id,content,score) values(2,4,"我是2,4还不错",4);
insert into remark(user_id,resource_id,content,score) values(3,4,"我是3,4还不错",4);
insert into remark(user_id,resource_id,content,score) values(3,5,"我是3,5还不错",5);

create table points (
    id int not null auto_increment comment '资源分id',
    resource_point int not null comment '资源分',
    primary key(id),
    unique key resource_point_unique(resource_point) using btree
) comment '资源分表' ;

insert into points(resource_point) values(2);
insert into points(resource_point) values(5);
insert into points(resource_point) values(8);
insert into points(resource_point) values(10);