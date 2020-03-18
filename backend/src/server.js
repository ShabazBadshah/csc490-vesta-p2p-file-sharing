var express = require('express');
var server = express();
var ecstatic = require('ecstatic')
server = require('http').createServer(
  ecstatic({ root: __dirname, handleError: false })
)
var io = require('socket.io')(server)

server.listen(80, '0.0.0.0',function () {
  console.log('Listening on 80')
})
var p2pserver = require('socket.io-p2p-server').Server
/*var express = require('express');
var app = express();
// static_files has all of statically returned content
// https://expressjs.com/en/starter/static-files.html
var server = app.listen(80, function () {
    console.log('Example app listening on port 80!');
});*/

//var io = require('socket.io').listen(server);;
//io.origins(':');

io.use(p2pserver)

io.on('connection', function (socket) {
  console.log("PRINT SOMETHING PLZ")
  console.log("one user connected " + socket.id)
  socket.on('peer-msg', function (data) {
    console.log('Message jbjkbfrom peer: %s', data.textVal)
    socket.broadcast.emit('peer-msg', data)
  })

  socket.on('disconnect', function () {
    console.log('one user disconnected', socket.id)
  })
})
