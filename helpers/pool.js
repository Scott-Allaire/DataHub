const mysql = require('mysql');

require('dotenv').config();

const pool = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
});

const getConnection = (callback) => {
    pool.getConnection(function (err, connection) {
        callback(err, connection);
        connection.release();
    });
};

module.exports = {
    getConnection: getConnection
};
