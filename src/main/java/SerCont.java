import java.io.Serializable;

public class SerCont extends Continuation implements Serializable {

    public SerCont(ContinuationScope scope, Runnable target) {
        super(scope, target);
    }

    public SerCont(ContinuationScope scope, int stackSize, Runnable target) {
        super(scope, stackSize, target);
    }
}
