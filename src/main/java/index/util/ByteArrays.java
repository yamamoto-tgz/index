package index.util;

public final class ByteArrays {
    public static int indexOf(byte[] bytes, String text, int fromIndex) {
        byte[] textBytes = text.getBytes();
        int counter = 0;
        for (int i = fromIndex; i < bytes.length; i++) {
            for (int j = 0; j < textBytes.length; j++) {
                if (bytes[i + j] == textBytes[j]) {
                    counter++;
                } else {
                    counter = 0;
                    break;
                }
            }
            if (counter == textBytes.length) return i;
        }
        return -1;
    }
}
