const express = require('express');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({ message: 'API - We got a hit!' });
});

router.use('/members', require('./members'));

module.exports = router;
