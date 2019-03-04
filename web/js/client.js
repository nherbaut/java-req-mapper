const {Empty, Requirement} = require('./requirements_pb.js');
const {ReqCollectorClient} = require('./requirements_grpc_web_pb.js');

var reqCollector = new ReqCollectorClient('http://localhost:8081');

var request = new Empty();

reqCollector.pullRequirement(request, {}, function(err, response) {
  alert(response);
});
