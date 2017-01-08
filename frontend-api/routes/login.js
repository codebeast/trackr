var express = require('express');
var http = require('http');
var router = express.Router();


router.get('/', function (req, res, next) {
    res.render('login', {title: 'Express'});
});


router.post('/', function (req, res, next) {
    var email = req.body.email;
    var password = req.body.password;

    login(email, password, function (success) {
        if (success) {
            res.redirect('maps')
        } else {
            res.render('login', {invalid: true});
        }
    });


});


function login(email, password, cb) {
    var postData = {
        email: email,
        passwordHash: password
    };
    var options = {
        host: "localhost",
        port: 7001,
        path: '/user',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    };

    var success = false;
    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            var object = JSON.parse(chunk);
            success = object && object.email == email;
        });

        res.on('end', function () {
            cb(success);
        });

    });

    req.on('error', function (chunk) {
        cb(false);
    });

    req.write(JSON.stringify(postData));
    req.end();
}

module.exports = router;
