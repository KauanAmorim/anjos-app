package com.nauakavlis.anjos_app.infrastructure.config.graphql;

import com.nauakavlis.anjos_app.domain.exception.DomainErrorCategory;
import com.nauakavlis.anjos_app.domain.exception.DomainException;
import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphQlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof DomainException domainEx) {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(toGraphQlErrorType(domainEx.getCategory()))
                    .message(ex.getMessage())
                    .build();
        }
        return null;
    }

    private ErrorType toGraphQlErrorType(DomainErrorCategory category) {
        return switch (category) {
            case NOT_FOUND -> ErrorType.NOT_FOUND;
            case VALIDATION, CONFLICT -> ErrorType.BAD_REQUEST;
            case UNAUTHORIZED -> ErrorType.UNAUTHORIZED;
            case FORBIDDEN -> ErrorType.FORBIDDEN;
        };
    }
}