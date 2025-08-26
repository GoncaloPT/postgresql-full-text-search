package pt.goncalo.poc.postgresfulltext;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;

public class FullTextSearchAwarePostgreSQLDialect extends PostgreSQLDialect {
    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);
        var functionRegistry = functionContributions.getFunctionRegistry();
        functionRegistry.registerPattern("tsvector_query",
                "(?1 @@ ?2)");

    }
}
