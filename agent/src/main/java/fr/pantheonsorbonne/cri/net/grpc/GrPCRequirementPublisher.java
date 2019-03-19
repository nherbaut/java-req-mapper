package fr.pantheonsorbonne.cri.net.grpc;

import com.google.inject.Inject;

import fr.pantheonsorbonne.cri.req.AbstractRequirementPublisher;
import fr.pantheonsorbonne.cri.requirements.Empty;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc.ReqCollectorStub;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import io.grpc.stub.StreamObserver;

public class GrPCRequirementPublisher extends AbstractRequirementPublisher {

	final io.grpc.stub.StreamObserver<fr.pantheonsorbonne.cri.requirements.Requirement> reqPublisher;

	@Inject
	public GrPCRequirementPublisher(ReqCollectorStub service, StreamObserver<Empty> obs) {
		reqPublisher = service.pushRequirement(obs);
	}

	@Override
	protected void publishLinkedRequirement(Requirement req) {
		reqPublisher.onNext(req);

	}

}
