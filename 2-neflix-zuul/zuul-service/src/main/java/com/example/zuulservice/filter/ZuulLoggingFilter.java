package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

//  @Slf4j로 대체
//  Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);

  @Override
  public Object run() throws ZuulException {
    log.info("*************** printing logs: ");

    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    log.info("*************** " + request.getRequestURI());
    return null;
  }
  @Override
  public String filterType() {
    // 사전 필터인지 사후 필터인지 결정하는 부분
    return "pre";
  }

  @Override
  public int filterOrder() {
    // 필터가 여러 개 있을 경우 순서를 결정하는 부분, 해당 프로젝트에서는 필터가 하나이므로 1로 지정
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    // 필터를 사용할 것인지 결정
    return true;
  }

}
