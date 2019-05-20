public interface KryoContext {

    byte[] serialize(Object obj);

    byte[] serialize(Object obj, int bufferSize);

    Object deserialize(Class clazz, byte[] serialized);
}