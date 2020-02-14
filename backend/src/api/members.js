const express = require('express');

const router = express.Router();

router.get('/', (req, res) => {
  res.json(['Avi', 'Kunal', 'Shabaz', 'Swetha', 'Tameem', 'Waleed']);
});

module.exports = router;
