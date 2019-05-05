import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.invoke.SerializedLambda;

public class Deserializer {

    public void work() {
        KryoContext kryoContext = DefaultKryoContext.newKryoContextFactory(kryo -> {
            kryo.register(Continuation.class);
            kryo.register(ContinuationScope.class);
            kryo.register(Object[].class);
            kryo.register(Class.class);
            kryo.register(SerializedLambda.class);
            kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
//            kryo.register(Runnable.class);
//            kryo.register(MethodHandle.class);
//            kryo.register(CallSite.class);
//            kryo.register(MethodType.class);
//            kryo.register(Serializer.class);
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        });

//        Continuation recoveredCont = (Continuation) kryoContext.deserialze(Continuation.class, null);
        Runnable r = (Runnable) kryoContext.deserialze(Runnable.class, null);
        new Thread(r).start();
//        final boolean isContinuationDone = recoveredCont.isDone();
//        System.out.println("is Done: " + isContinuationDone);
//        if (!isContinuationDone) {
//            new Thread(recoveredCont::run).start();
//        }
    }
}
