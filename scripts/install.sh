su hdfs
hadoop fs -mkdir /udf

hadoop fs -put deprofaner-1.0-jar-with-dependencies.jar /udf/
hadoop fs -chown -R hdfs /udf
hadoop fs -chgrp -R hdfs /udf
hadoop fs -chmod -R 777 /udf
hadoop fs -ls /udf
