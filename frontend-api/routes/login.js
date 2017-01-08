var express = require('express');
var router = express.Router();


router.get('/', function (req, res, next) {
    res.render('login', {title: 'Express'});
});


router.post('/', function (req, res, next) {
    var email = req.body.email;
    var password = req.body.password;

    if (password == "password") {
        res.redirect('/maps');
    } else {
        res.render('login', {invalid: true})
    }

});

module.exports = router;
