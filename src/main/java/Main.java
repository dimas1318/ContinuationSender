import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

import static java.lang.Continuation.getCurrentContinuation;
import static java.lang.Continuation.yield;

public class Main {

    public static void main(String[] args) {
        new Serializer().work();

        new Deserializer().work();
    }
//    public static void main(String[] args) {
//
//        ContinuationScope scope = new ContinuationScope("SERG");
////        Continuation c = new Continuation(scope, (Runnable & Serializable) () -> {
////            System.out.println("Hello koko");
////            int x = 2;
////            System.out.println(x);
////            yield(scope);
////            System.out.println("Continue");
////            x += 1;
////            System.out.println(x);
////        });
//        Continuation c = new Continuation(scope, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello koko");
//                int x = 2;
//                System.out.println(x);
//                yield(scope);
//                System.out.println("Continue");
//                x += 1;
//                System.out.println(x);
//            }
//        });
//
//        c.run();
////        c.run();
//
//        KryoContext kryoContext = DefaultKryoContext.newKryoContextFactory(kryo -> {
//            kryo.register(Object[].class);
//            kryo.register(Class.class);
//            kryo.register(SerializedLambda.class);
//            kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
////            kryo.register(Main.class);
//            kryo.register(Continuation.class);
//            kryo.register(ContinuationScope.class);
//            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//        });
//
//        byte[] bytes = kryoContext.serialze(c);
//
////        try (FileOutputStream fos = new FileOutputStream("/home/dmitry/Desktop/TEST_CONT_SERIALIZE")) {
////            fos.write(bytes);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
////        Continuation copy = (Continuation) kryoContext.deserialze(Continuation.class, bytes);
//        Continuation copy = (Continuation) kryoContext.deserialze(Continuation.class, null);
//        System.out.println(c.equals(copy));
//        copy.run();
//    }
}
