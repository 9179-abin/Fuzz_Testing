public class NativLib {
    // Load the native library
    static {
        System.loadLibrary("nativeLib"); // On Linux: libnative.so, on Windows: native.dll
    }

    // Declare the native method
    public native int add(int a, int b);

    // Test Method
    public static void main(String[] args) {
        NativLib lib = new NativLib();
        int result = lib.add(5, 7);
        System.out.println("Result: " + result);
    }
}
