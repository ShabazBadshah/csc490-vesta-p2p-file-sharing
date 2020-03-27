const ecstatic = require('ecstatic');
const ss = require('socket.io-stream');
const fs = require('fs');

const httpBaseServer = require('http').createServer(
  ecstatic({ root: __dirname, handleError: false })
);
const socketIoServer = require('socket.io')(httpBaseServer);

httpBaseServer.listen(80, '0.0.0.0', () => console.log('Listening on 80'));

const p2pserver = require('socket.io-p2p-server').Server;

socketIoServer.use(p2pserver);

socketIoServer.on('connection', socket => {
  console.log(`User connected: ${socket.id}`);

  socket.on('go-private', data => {
    socket.broadcast.emit('go-private', data);
    // console.log(`Going private: ${socket.id}`);
  });

  // Recieve file from client
  socket.on('peer-file', data => {
    console.log('Message from peer-file: %s', data.textVal);
    socket.broadcast.emit('peer-file', data);
  });
  socket.on('peer-msg', function(data) {
    console.log('Message from peer-msg: %s', data.textVal);
    socket.broadcast.emit('peer-msg', data);
  });

  socket.on('disconnect', () => console.log('one user disconnected', socket.id));
});
