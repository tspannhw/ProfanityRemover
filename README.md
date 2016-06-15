# ProfanityRemover
Hive Java UDF for ProfanityRemover (Example Code)

Warning!  src/main/resources and src/test/resources contain a list of offensive words.

This is an example of a Hive UDF, it will filter many profanities, but will also block out a lot of false positives.


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
	./build.sh

To Build for Command-Line Usage (outside of Hive)
	./buildfirst.sh
(or)
	mvn clean compile assembly:single

generates
	target/deprofaner-1.0-jar-with-dependencies.jar

Copy deprofaner*jar to directory to run from or /usr/hdp/current/hive-client/lib/

mkdir -p /opt/demo/udf
Copy src/main/resources/terms.txt to /opt/demo/udf/terms.txt

In Hive

hive> set hive.cli.print.header=true;
hive> add jar deprofaner-1.0-jar-with-dependencies.jar;
Added [deprofaner-1.0-jar-with-dependencies.jar] to class path
Added resources: [deprofaner-1.0-jar-with-dependencies.jar]
hive> CREATE TEMPORARY FUNCTION cleaner as 'com.dataflowdeveloper.deprofaner.ProfanityRemover';
OK
select cleaner('clean this <curseword> up now') from sample_07 limit 1;
OK
_c0
clean this **** up now
Time taken: 6.279 seconds, Fetched: 1 row(s)


Check logs
/var/log/hive/hiveserver2.log

set hive.cli.print.header=true;
add jar deprofaner-1.0-jar-with-dependencies.jar;
CREATE TEMPORARY FUNCTION cleaner as 'com.dataflowdeveloper.deprofaner.ProfanityRemover';
select cleaner(description) from sample_07 limit 100;

For Permanent UDF

-- For Permanent
-- https://cwiki.apache.org/confluence/display/Hive/HivePlugins

Run scripts/install.sh

set hive.cli.print.header=true;
CREATE FUNCTION cleaner as 'com.dataflowdeveloper.deprofaner.ProfanityRemover' USING JAR 'hdfs:///udf/deprofaner-1.0-jar-with-dependencies.jar';


