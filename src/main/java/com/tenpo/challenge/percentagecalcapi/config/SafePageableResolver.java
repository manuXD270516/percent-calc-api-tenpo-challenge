package com.tenpo.challenge.percentagecalcapi.config;

import com.tenpo.challenge.percentagecalcapi.model.RequestLog;
import com.tenpo.challenge.percentagecalcapi.util.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class SafePageableResolver implements HandlerMethodArgumentResolver {

    private final PageableHandlerMethodArgumentResolver delegate = new PageableHandlerMethodArgumentResolver();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return delegate.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {

        Pageable original = delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        Sort safeSort = SortUtils.filterValidSort(
                original.getSort(),
                RequestLog.class,
                Sort.by(Sort.Direction.DESC, "timestamp")
        );

        return PageRequest.of(original.getPageNumber(), original.getPageSize(), safeSort);
    }
}
