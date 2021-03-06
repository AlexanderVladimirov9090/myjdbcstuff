package com.gmail.alexander.vladimirov1902.magazine;

import com.gmail.alexander.vladimirov1902.magazine.persistence_layer.DataStore;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Created by clouway on 24.11.16.
 *
 * @author Alexander Vladimirov
 *         <alexandervladimirov1902@gmail.com>
 */
public class DatabaseTableRule implements TestRule {
    private DataStore dataStore;
    private List<String> tablesName;

    public DatabaseTableRule(DataStore dataStore, List<String> tableSName) {
        this.dataStore = dataStore;
        this.tablesName = tableSName;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                for (String each : tablesName) {
                    dataStore.update("TRUNCATE TABLE " + each);
                }
            }
        };
    }
}
