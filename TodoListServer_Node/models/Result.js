class Result {
  error;
  errMsg;
  result;
  constructor(error, errMsg, result) {
    this.error = error;
    this.errMsg = errMsg;
    this.result = result;
  }
}

module.exports = Result;
