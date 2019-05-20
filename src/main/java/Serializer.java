import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.Serializable;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;

import static java.lang.Continuation.yield;

public class Serializer {

    public static final ContinuationScope CONTINUATION_SCOPE = new ContinuationScope("SERG");

    public void work() {

//        Runnable r = null;
//
//        try {
//
//            MethodHandles.Lookup me = MethodHandles.lookup();
//            MethodType t = MethodType.methodType(void.class);
//            MethodType rt = MethodType.methodType(Runnable.class);
//            CallSite site = LambdaMetafactory.altMetafactory(
//                    me, "run", rt, t, me.findStatic(Serializer.class, "doTask", t), t, LambdaMetafactory.FLAG_SERIALIZABLE);
//            MethodHandle factory = site.getTarget();
//            r = (Runnable) factory.invoke();
//
//            System.out.println("created lambda: " + r);
////            r.run();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

//        Runnable r = (Runnable & Serializable) Serializer::doTask;
//        Class c = r.getClass();
//        try {
//            Method writeReplace = c.getDeclaredMethod("writeReplace");
//            Method readResolve = SerializedLambda.class.getDeclaredMethod("readResolve");
//            writeReplace.setAccessible(true);
//            readResolve.setAccessible(true);
//            // Generate a serializable representation of this lambda
//            Object replacement = null;
//            replacement = writeReplace.invoke(r);
//
//            if (replacement instanceof SerializedLambda) {
//                SerializedLambda l = (SerializedLambda) replacement;
//                // Serialize and deserialize the representation of this lambda
//                // Use readResolve to create a real lambda object from this representation
//                Continuation continuation = new Continuation(CONTINUATION_SCOPE, ((Runnable) readResolve.invoke(l)));
//                new Thread(continuation::run).start();
//
//                KryoContext kryoContext = DefaultKryoContext.newKryoContextFactory(kryo -> {
//                    kryo.register(Continuation.class);
//                    kryo.register(ContinuationScope.class);
//                    kryo.register(Object[].class);
//                    kryo.register(Class.class);
//                    kryo.register(SerializedLambda.class);
//                    kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
//                });
//
//                kryoContext.serialize(continuation);
//            }
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }

//        Continuation c = new Continuation(CONTINUATION_SCOPE, r);
        Continuation c = new SerCont(CONTINUATION_SCOPE, (Runnable & Serializable) Serializer::doTask);
//        Continuation c = new Continuation(CONTINUATION_SCOPE, (Runnable & Serializable) Serializer::doTask);
//        Continuation c = new Continuation(CONTINUATION_SCOPE, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello koko");
//                int x = 2;
//                System.out.println(x);
//                yield(CONTINUATION_SCOPE);
//                System.out.println("Continue");
//                x += 1;
//                System.out.println(x);
//            }
//        });

        new Thread(c::run).start();
////        c.run();
//
//
        KryoContext kryoContext = DefaultKryoContext.newKryoContextFactory(kryo -> {
            kryo.register(Continuation.class);
            kryo.register(SerCont.class);
            kryo.register(ContinuationScope.class);
            kryo.register(Object[].class);
            kryo.register(Class.class);
            kryo.register(SerializedLambda.class);
            kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
            kryo.register(Runnable.class);
            kryo.register(MethodHandle.class);
            kryo.register(CallSite.class);
            kryo.register(MethodType.class);
            kryo.register(Serializer.class);
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        });

        kryoContext.serialize(c);
    }

    private static void doTask() {
        System.out.println("Hello koko");
        int x = 2;
        System.out.println(x);
        yield(CONTINUATION_SCOPE);
        System.out.println("Continue");
        x += 1;
        System.out.println(x);
    }
}
