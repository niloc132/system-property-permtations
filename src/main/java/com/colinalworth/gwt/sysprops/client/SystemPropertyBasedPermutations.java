package com.colinalworth.gwt.sysprops.client;

import com.google.gwt.core.client.EntryPoint;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Created by colin on 1/29/17.
 */
public class SystemPropertyBasedPermutations implements EntryPoint {
    public void onModuleLoad() {
        Constants constants = ConstantsFactory.get();


        Window.getSelf().alert("Locale is " + System.getProperty("locale"));

        Window.getSelf().alert("Greeting is " + constants.hello());
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "window")
    public static class Window {
        @JsProperty
        public native static Window getSelf();
        public native void alert(String message);
    }


    public interface Constants {
        String hello();
    }
    public static class Constants_en implements Constants {

        public String hello() {
            return "hello";
        }
    }
    public static class Constants_fr implements Constants {
        public String hello() {
            return "bonjour";
        }
    }
    public static class Constants_es implements Constants {
        public String hello() {
            return "hola";
        }
    }
    public static class ConstantsFactory {
        public static Constants get() {
            String locale = System.getProperty("locale");

            switch (locale) {
                case "en":
                    return new Constants_en();
                case "fr":
                    return new Constants_fr();
                case "es":
                    return new Constants_es();
            }
            throw new IllegalStateException("Unsupported locale " + locale);
        }
    }
}
