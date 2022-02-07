package car.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataSourceContextHolder {
    // used to share data with current thread
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // used to keep all datasource lookup key
    private static List<String> dataSourceLookupKeys = new ArrayList<>();

    public static void switchDataSource(String ds) {
        threadLocal.set(ds);
    }

    // used to get the current thread datasource's lookup key
    public static String getDataSource() {
        return threadLocal.get();
    }

    // used to clear current thread's dataSource lookup key after routing
    public static void clearDataSource() {
        threadLocal.remove();
    }

    public static boolean containDataSource(String ds) {
        return dataSourceLookupKeys.contains(ds);
    }

    // used to register all dataSources lookup key
    public static void addDataSource(String ds) {
        if (!containDataSource(ds)) {
            dataSourceLookupKeys.add(ds);
        }
    }

    private static final String SLAVE = "slave";

    public static boolean switchSlaveDataSourceRandomly(String ds) {
        if (SLAVE.equalsIgnoreCase(ds)) {
            Random rd = new Random();
            while (true) {
                int index = rd.nextInt(dataSourceLookupKeys.size());
                String dsKey = dataSourceLookupKeys.get(index);
                if (dsKey.toLowerCase(Locale.ROOT).contains(SLAVE)) {
                    switchDataSource(dsKey);
                    break;
                }
            }
            return true;
        }
        return false;
    }
}