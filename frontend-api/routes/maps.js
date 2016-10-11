var express = require('express');
var router = express.Router();


/* GET users listing. */
router.get('/', function (req, res, next) {
    res.send('respond with maps');
});

module.exports = router;
