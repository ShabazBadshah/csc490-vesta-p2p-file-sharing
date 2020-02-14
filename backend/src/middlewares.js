/**
 * Sets a 404 error in the response with the requested resource that could not be found
 * @param {*} req The client request object
 * @param {*} res The server response object
 * @param {*} next The next function in the middleware stack to be called
 */
function notFound(req, res, next) {
  res.status(404);
  const error = new Error(`Not Found - ${req.originalUrl}`);
  next(error);
}

/**
 *
 * @param {*} err The error that was raised
 * @param {*} req The client request object
 * @param {*} res The server response object
 * @param {*} next The next function in the middleware stack to be called
 */
function errorHandler(err, req, res, next) {
  const statusCode = res.statusCode !== 200 ? res.statusCode : 500;
  res.status(statusCode);
  res.json({
    message: err.message,
    stack: process.env.NODE_ENV === 'production' ? 'PROD ERROR :D' : err.stack
  });
}

module.exports = {
  notFound,
  errorHandler
};
