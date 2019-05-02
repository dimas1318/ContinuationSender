import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.invoke.SerializedLambda;

public class Deserializer {

    public void work() {
        KryoContext kryoContext = DefaultKryoContext.newKryoContextFactory(kryo -> {
            kryo.register(Object[].class);
            kryo.register(Class.class);
            kryo.register(SerializedLambda.class);
            kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
            kryo.register(Continuation.class);
            kryo.register(ContinuationScope.class);
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        });

        Continuation copy = (Continuation) kryoContext.deserialze(Continuation.class, null);
        copy.run();
    }
}
