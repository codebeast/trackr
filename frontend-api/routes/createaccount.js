var express = require('express');
var querystring = require('querystring');
var http = require('http');
var router = express.Router();


router.get('/', function (req, res, next) {
    displayCreateAccountPage(res, false);
});

router.post('/', function (req, res, next) {
    var companyName = req.body.companyName;
    var email = req.body.email;
    var password = req.body.password;

    if (companyName && email && password) {


        var postData = {
            account: {
                name: companyName
            },
            user: {
                email: email,
                passwordHash: password
            }
        };

        var options = {
                host: "localhost",
                port: 7001,
                path: '/account',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(postData)
            };

        var post = http.request(options, function (res) {
            console.log('STATUS: ' + res.statusCode);
            console.log('HEADERS: ' + JSON.stringify(res.headers));
            res.setEncoding('utf8');
            res.on('data', function (chunk) {
                console.log('BODY: ' + chunk);
            });
        });
        post.write(JSON.stringify(postData));
        post.end();


        if (companyName == "groo") {
            displayCreateAccountPage(res, true);
        } else {
            res.redirect("/login");
        }

    } else {
        displayCreateAccountPage(res, false);
    }
});


function displayCreateAccountPage(res, accountExists) {
    res.render("createAccount", {accountExists: accountExists})
}

module.exports = router;
