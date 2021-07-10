package per.jianfei.myjniapplication;

public class MyJNI {
    static {
        System.loadLibrary("JniTest");
    }

    public static native String sayHello();
}
