const mysql = require('mysql');

require('dotenv').config();

const poolHelper = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
});

const getConnection = (callback) => {
    poolHelper.getConnection(function (err, connection) {
        if (connection) {
            callback(err, connection);
            connection.release();
        } else {
            console.log(err);
        }
    });
};

module.exports = {
    getConnection: getConnection
};
