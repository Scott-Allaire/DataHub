const express = require('express');
const router = express.Router();

const pool = require('../helpers/poolHelper');

router.get('/', function(req, res, next) {
    pool.getConnection((err, conn) => {

        conn.query('select * from reading order by received desc', (err, rows) => {
            res.json(rows);
        });
    });
});

module.exports = router;