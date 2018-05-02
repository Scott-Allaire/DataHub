var express = require('express');
var router = express.Router();

const pool = require('../helpers/pool');

router.get('/', function(req, res, next) {
    pool.getConnection((err, conn) => {
        conn.query('select count(*) ct from reading', (err, rows) => {
            res.render('readings', {
                title: 'Readings',
                count: rows[0].ct
            });
        });
    });
});

module.exports = router;