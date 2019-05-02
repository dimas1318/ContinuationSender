import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.*;
import java.util.zip.DeflaterOutputStream;

public class DefaultKryoContext implements KryoContext {

    private static final int DEFAULT_BUFFER = 1024 * 100;

    private KryoPool pool;

    public static KryoContext newKryoContextFactory(KryoClassRegistrator registrator) {
        return new DefaultKryoContext(registrator);
    }

    private DefaultKryoContext(KryoClassRegistrator registrator) {
        KryoFactory factory = new KryoFactoryImpl(registrator);

        pool = new KryoPool.Builder(factory).softReferences().build();
    }

    private static class KryoFactoryImpl implements KryoFactory {
        private KryoClassRegistrator registrator;

        public KryoFactoryImpl(KryoClassRegistrator registrator) {
            this.registrator = registrator;
        }

        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            registrator.register(kryo);

            return kryo;
        }
    }


    @Override
    public byte[] serialze(Object obj) {
        return serialze(obj, DEFAULT_BUFFER);
    }

    @Override
    public byte[] serialze(Object obj, int bufferSize) {

//        Output output = new Output(new ByteArrayOutputStream(), bufferSize);

        Kryo kryo = pool.borrow();

//        kryo.writeClassAndObject(output, obj);
//        byte[] serialized = output.toBytes();

        Output output = null;
        try {
            output = new Output(new FileOutputStream("/home/dmitry/Desktop/TEST_CONT_SERIALIZE.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        kryo.writeObject(output, obj);
        output.close();

        pool.release(kryo);

//        return serialized;
        return null;
    }

    @Override
    public Object deserialze(Class clazz, byte[] serialized) {

        Kryo kryo = pool.borrow();

//        Input input = new Input(serialized);
//        Object obj = kryo.readClassAndObject(input);

        Input input = null;
        try {
            input = new Input(new FileInputStream("/home/dmitry/Desktop/TEST_CONT_SERIALIZE.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Object obj = kryo.readObject(input, Continuation.class);
        input.close();

        pool.release(kryo);

        return obj;
    }

}