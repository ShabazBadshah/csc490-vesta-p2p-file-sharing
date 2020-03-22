var express = require('express');
var server = express();
var ecstatic = require('ecstatic');
var ss = require('socket.io-stream');
var path = require('path');
var fs = require('fs');
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
    console.log('Message jbjkbfrom peer: %s', data)
    // ss(socket).on('file', function(stream) {
    //   fs.createReadStream('./testFile').pipe(stream);
    // });
    //broadcast will send msg to everyone exept the sender
    //use only emit to send to everyone
    // socket.broadcast.emit('peer-msg', data);
    // ss(socket).on('test-stream', function(stream, data2) {
    //   console.log("File is being written");
    //   console.log("About to write: %s", data2.name);
    //   var filename = path.basename(data2.name);
    //   stream.pipe(fs.createWriteStream(filename));
    // });
    
    // ss(socket).emit('test-', stream, {name: filename});
    // fs.createReadStream(filename).pipe(stream);
  })


  ss(socket).on('test-stream', function(stream, data2) {
    console.log("File is being written");
    console.log("About to write: %s", data2.name);
    var filename = path.basename(data2.name);
    stream.pipe(fs.createWriteStream(filename));
  });

  socket.on('go-private', function(data) {
    socket.broadcast.emit('go-private', data);
  });
  
  socket.on('disconnect', function () {
    console.log('one user disconnected', socket.id)
  })
})
