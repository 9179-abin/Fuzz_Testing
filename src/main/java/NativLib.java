public class NativLib {
    // Load the native library
    static {
        System.loadLibrary("nativeLib");
    }

    // Declare the native method
    public native int add(int a, int b);


    public static void main(String[] args) {
        NativLib lib = new NativLib();
        int result = lib.add(5, 7);
        System.out.println("Result: " + result);
    }
}
