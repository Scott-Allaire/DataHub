const mqtt = require('mqtt');
const moment = require('moment');

const pool = require('./poolHelper');

require('dotenv').config();

const brokerUrl = 'mqtt://' + process.env.MQTT_HOST + ":" + process.env.MQTT_PORT;
const brokerOptions = {
    connectTimeout: 10
};
const INSERT_READING_SQL = 'INSERT INTO reading(code, source, value, epoch, received) ' +
    'VALUES (?, ?, ?, ?, ?)';

function insertReading(conn, code, source, value, epoch) {
    const received = moment().format();
    pool.getConnection((err, conn) => {
        conn.query(INSERT_READING_SQL, [code, source, value, epoch, received], (err, rows) => {
            if (err) throw err;
        });
    });
}

const init = (callback) => {
    console.log("Attempting to connect to MQTT server at " + brokerUrl);
    const client = mqtt.connect(brokerUrl, brokerOptions);

    client.on('error', (error) => {
        console.log("Error connecting to MQTT server at " + brokerUrl, error);
    });

    client.on('connect', () => {
        client.subscribe('topic/+');
        console.log("Listening for messages on " + 'topic/+');
    });

    client.on('message', (topic, message) => {
        console.log('Received: ' + message.toString() + ' from ' + topic);
        const json = JSON.parse(message);

        pool.getConnection((err, conn) => {
            if (json.tempf) {
                insertReading(conn, 'tempf', topic, json.tempf, json.epoch);
            }
            if (json.tempc) {
                insertReading(conn, 'tempc', topic, json.tempc, json.epoch);
            }
            if (json.hum) {
                insertReading(conn, 'hum', topic, json.hum, json.epoch);
            }
            if (json.feels_like) {
                insertReading(conn, 'feels_like', topic, json.feels_like, json.epoch);
            }
            if (json.pressure) {
                insertReading(conn, 'pressure', topic, json.tempc, json.epoch);
            }
        });
    })
};

module.exports = {
    init: init
};
