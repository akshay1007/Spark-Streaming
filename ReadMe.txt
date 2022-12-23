For the Docker setup please follow the steps given into the below article.
https://developer.confluent.io/quickstart/kafka-docker/

Once the docker setup please write below sample data into topic

{"ts": 20221201000553,"eventName": "Charged","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Charged","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Add-TO-Bag","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Add-TO-Bag","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Charged","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "PDP-Click","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Charged","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "PDP-Click","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "PDP-Click","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}
{"ts": 20221201000553,"eventName": "Charged","profile": {"identity": "23042438","phone": 918851444897},"deviceInfo": {"make": "xiaomi"},"eventProps": {"offerIds": "Combo_ncr879,ncr10625"}}


# To Build the JAR
mvn install
// This will create a new jar file under the target folder

# For running the spark submit command
# Note please comment the --master "local[*]" from your code and then build the jar
# Spark submit command
/Users/akshayjain/Desktop/DealShare/installation/spark-3.2.3-bin-hadoop3.2/bin/spark-submit \
--master "local[*]" \
--class SparkAppMain \
target/spark-kafka_v2-1.0-SNAPSHOT.jar



