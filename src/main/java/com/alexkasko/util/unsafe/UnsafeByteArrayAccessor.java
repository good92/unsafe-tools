package com.alexkasko.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * User: alexkasko
 * Date: 12/11/12
 */
class UnsafeByteArrayAccessor implements ByteArrayAccessor {

    private static final Unsafe UNSAFE;

    // borrowed from
    // https://github.com/dain/snappy/blob/602f4d7d71237f6a599389575edf5367da6cea37/src/main/java/org/iq80/snappy/UnsafeMemory.java#L28
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            // It seems not all Unsafe implementations implement the following method.
            new UnsafeByteArrayAccessor().copy(new byte[1], 0, new byte[1], 0, 1);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final long BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);

    @Override
    public boolean isUnsafe() {
        return true;
    }

    @Override
    public byte readByte(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 1 <= data.length;
        return UNSAFE.getByte(data, BYTE_ARRAY_OFFSET + offset);
    }

    @Override
    public void writeByte(byte[] data, int offset, byte value) {
        assert offset >= 0;
        assert offset + 1 <= data.length;
        UNSAFE.putByte(data, BYTE_ARRAY_OFFSET + offset, value);
    }

    @Override
    public short readUnsignedByte(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 1 <= data.length;
        return (short) (UNSAFE.getByte(data, BYTE_ARRAY_OFFSET + offset) & 0xff);
    }

    @Override
    public void writeUnsignedByte(byte[] data, int offset, short value) {
        assert offset >= 0;
        assert offset + 1 <= data.length;
        UNSAFE.putByte(data, BYTE_ARRAY_OFFSET + offset, (byte) value);
    }

    @Override
    public short readShort(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 2 <= data.length;
        return UNSAFE.getShort(data, BYTE_ARRAY_OFFSET + offset);
    }

    @Override
    public void writeShort(byte[] data, int offset, short value) {
        assert offset >= 0;
        assert offset + 2 <= data.length;
        UNSAFE.putShort(data, BYTE_ARRAY_OFFSET + offset, value);
    }

    @Override
    public int readUnsignedShort(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 2 <= data.length;
        return UNSAFE.getShort(data, BYTE_ARRAY_OFFSET + offset) & 0xffff;

    }

    @Override
    public void writeUnsignedShort(byte[] data, int offset, int value) {
        assert offset >= 0;
        assert offset + 2 <= data.length;
        UNSAFE.putShort(data, BYTE_ARRAY_OFFSET + offset, (short) value);
    }

    @Override
    public int readInt(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 4 <= data.length;
        return UNSAFE.getInt(data, BYTE_ARRAY_OFFSET + offset);
    }

    @Override
    public void writeInt(byte[] data, int offset, int value) {
        assert offset >= 0;
        assert offset + 4 <= data.length;
        UNSAFE.putInt(data, BYTE_ARRAY_OFFSET + offset, value);
    }

    @Override
    public long readUnsignedInt(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 4 <= data.length;
        return UNSAFE.getInt(data, BYTE_ARRAY_OFFSET + offset) & 0xffffffffL;
    }

    @Override
    public void writeUnsignedInt(byte[] data, int offset, long value) {
        assert offset >= 0;
        assert offset + 4 <= data.length;
        UNSAFE.putInt(data, BYTE_ARRAY_OFFSET + offset, (int) value);
    }

    @Override
    public long readLong(byte[] data, int offset) {
        assert offset >= 0;
        assert offset + 8 <= data.length;
        return UNSAFE.getLong(data, BYTE_ARRAY_OFFSET + offset);
    }

    @Override
    public void writeLong(byte[] data, int offset, long value) {
        assert offset >= 0;
        assert offset + 8 <= data.length;
        UNSAFE.putLong(data, BYTE_ARRAY_OFFSET + offset, value);
    }

    @Override
    public void copy(byte[] input, int inputIndex, byte[] output, int outputIndex, int length) {
        assert inputIndex >= 0;
        assert inputIndex + length <= input.length;
        assert outputIndex >= 0;
        assert outputIndex + length <= output.length;
        UNSAFE.copyMemory(input, BYTE_ARRAY_OFFSET + inputIndex, output, BYTE_ARRAY_OFFSET + outputIndex, length);
    }
}