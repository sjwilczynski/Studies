package list5.list5_3;

import java.time.LocalTime;
import java.util.function.Supplier;

//functional version - Local factory pattern
public class TimeLocalFactory {

    private static Supplier<LocalTime> provider;

    public LocalTime getTime() throws NoProviderException {
        if (provider != null)
            return provider.get();
        else
            throw new NoProviderException();
    }

    public static void setProvider(Supplier<LocalTime> provider) {
        TimeLocalFactory.provider = provider;
    }

}

//non functional version
class TimeLocalFactory2 {

    private static GetTime provider;

    public LocalTime getTime() throws NoProviderException {
        if (provider != null) {
            return provider.getTime();
        } else {
            throw new NoProviderException();
        }
    }

    public static void setProvider(GetTime provider) {
        TimeLocalFactory2.provider = provider;
    }

}

interface GetTime {
    LocalTime getTime();
}

class NoProviderException extends RuntimeException {
}