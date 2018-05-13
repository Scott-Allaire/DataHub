const mqtt = require('mqtt');

require('dotenv').config();

const client  = mqtt.connect('mqtt://' + process.env.MQTT_HOST + ":" + process.env.MQTT_PORT);

client.on('connect', function () {
    client.publish('topic/weather', '{"hum":36.0,"tempc":21.0,"tempf":69.8,"feels_like":68.2,"time":"20:26:52","epoch":1526070412}');
    client.end();
});