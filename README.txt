
Building a Hive UDF

Timothy Spann
dataflowdeveloper.com
Hortonworks.com
@PaasDev
https://community.hortonworks.com/users/9304/tspann.html
http://www.meetup.com/futureofdata-princeton/

Resources:

https://cwiki.apache.org/confluence/display/Hive/HivePlugins#HivePlugins-CreatingCustomUDFs

To Build an Eclipse Project
mvn eclipse:eclipse

To Build

mvn package

generates
target/deprofaner-1.0.jar

In Hive

hive> add jar deprofaner-1.0.jar;

create temporary function sanitize as 'com.dataflowdeveloper.deprofaner.ProfanityRemover';

In New Hive

create function default.sanitize as 'com.dataflowdeveloper.deprofaner.ProfanityRemover';



Test In Hive

select sanitize(title) from sample_07;

/usr/hdp/current/hive-client/lib/

