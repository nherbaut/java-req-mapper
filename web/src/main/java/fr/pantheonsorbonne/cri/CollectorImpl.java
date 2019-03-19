package fr.pantheonsorbonne.cri;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;

import java.util.List;

import fr.pantheonsorbonne.cri.requirements.Empty;
import fr.pantheonsorbonne.cri.requirements.ReqCollectorGrpc.ReqCollectorImplBase;
import fr.pantheonsorbonne.cri.requirements.Requirement;
import io.grpc.stub.StreamObserver;

public class CollectorImpl extends ReqCollectorImplBase {

	public CollectorImpl() {

	}

	@Override
	public StreamObserver<Requirement> pushRequirement(StreamObserver<Empty> responseObserver) {
		return new StreamObserver<Requirement>() {

			@Override
			public void onNext(Requirement value) {
				if (SpringBootWebApplication.req.size() > 10) {
					SpringBootWebApplication.req.pop();
				}
				SpringBootWebApplication.req.addLast(value);

			}

			@Override
			public void onError(Throwable t) {
				System.out.println("error receiving req");

			}

			@Override
			public void onCompleted() {
				System.out.println("done receiving req");

			}
		};
	}

}
