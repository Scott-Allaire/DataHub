const express = require('express');
const _ = require('lodash');
const moment = require('moment');
const router = express.Router();

const pool = require('../helpers/poolHelper');

// list of available sources and reading types
router.get('/', function (req, res) {
    pool.getConnection((err, conn) => {
        const sql = 'select distinct source, code from reading order by source, code';
        conn.query(sql, (err, rows) => {
            res.json(_.groupBy(rows, (row) => row['source']));
        });
    });
});

// most recent readings from a given source
router.get('/:source', function (req, res) {
    const source = req.params.source;

    pool.getConnection((err, conn) => {
        const sql = 'select code, value, received from reading r1 ' +
            'where source = ? and received = ( ' +
            'select max(received) from reading r2 ' +
            'where r2.source = r1.source ' +
            'and r2.code = r1.code)';
        conn.query(sql, [source], (err, rows) => {
            res.json(rows);
        });
    });
});

// readings of given type from a source for a date range (defaults to last 30 days)
router.get('/:source/:code', function (req, res) {
    const source = req.params.source;
    const code = req.params.code;
    const start = req.query.start ?
        moment(req.query.start).format() :
        moment().startOf('day').add(-30, 'days').format();
    const end = req.query.end ?
        moment(req.query.end).format() :
        moment().endOf('day').format();

    pool.getConnection((err, conn) => {
        const sql = 'select value, epoch, received from reading r1 ' +
            'where source = ? ' +
            '  and code = ? ' +
            '  and received >= ? ' +
            '  and received <= ?';
        conn.query(sql, [source, code, start, end], (err, rows) => {
            res.json(rows);
        });
    });
});

module.exports = router;