var express = require('express');
var http = require('http');
var router = express.Router();


router.get('/all', function (req, res, next) {
    getDevices(function (devices) {
        res.json(devices);
    });
});

function getDevices(cb) {
    var options = {
        host: "localhost",
        port: 7001,
        path: '/account/testaccount/devices',
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            var object = JSON.parse(chunk);
            cb(object);
        });

        res.on('end', function () {
        });

    });

    req.on('error', function (chunk) {
        cb(null);
    });
    req.end();
}

module.exports = router;
