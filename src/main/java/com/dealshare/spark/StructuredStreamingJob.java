package com.dealshare.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.functions;

public class StructuredStreamingJob {

    public static void main(String[] args) throws StreamingQueryException {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);

        SparkSession spark = SparkSession.builder()
                .appName("StructuredStreamingJob")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "127.0.0.1:9092")
                .option("subscribe", "quickstart2")
                .option("includeHeaders", "true")
                .option("startingOffsets", "latest")
                .load();

        df = df.selectExpr("CAST(topic AS STRING)",
                "CAST(partition AS STRING)",
                "timestamp",
                "CAST(offset AS STRING)",
                "CAST(value AS STRING)");

        StructType data_schema = new StructType()
                .add("ts", DataTypes.StringType)
                .add("eventName", DataTypes.StringType)
                .add("profile", DataTypes.StringType)
                .add("deviceInfo", DataTypes.StringType)
                .add("eventProps", DataTypes.StringType)
                ;
        df = df.select(
                functions.col("timestamp"),
                functions.col("topic"),
                functions.col("partition"),
                functions.col("offset"),
                functions.from_json(functions.col("value"),
                        data_schema).alias("data"));

        df.printSchema();

        df = df.select("topic", "partition","timestamp", "offset", "data.*");
        Dataset<Row>  write_df = df;

        //aggregating the count of event
        df = df.groupBy(functions.window(df.col("timestamp"), "10 seconds", "10 seconds"),
                df.col("eventName")).count();

        StreamingQuery query = df.writeStream()
                .format("console")
                .option("truncate", "False")
                .outputMode(OutputMode.Complete())
                .option("compression","none")
                .start();
        //Writing the recived events
        StreamingQuery write_result = write_df.writeStream()
                .format("JSON").option("format", "append")
                .option("truncate", "False")
                .option("checkpointLocation", "/Users/akshayjain/Desktop/DealShare/codebase/Spark-Streaming/src/main/resources/checkpoint")
                .option("path", "/Users/akshayjain/Desktop/DealShare/codebase/Spark-Streaming/src/main/resources/data.json")
                .trigger(Trigger.ProcessingTime(10))
                .outputMode("append")
                .start();

        query.awaitTermination();
        write_result.awaitTermination();

    }

}