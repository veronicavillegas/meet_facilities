package meet.facilities.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import meet.facilities.exception.ApiException;
import meet.facilities.util.Constant;

@Component
public class Interceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) {
        String token = requestServlet.getHeader(Constant.AUTH_HEADER);
        checkIsUserOrAdmin(token);
        checkIsAdminToAskForBeers(requestServlet, (HandlerMethod)handler, token);
        
        return true;
    }

    private void checkIsAdminToAskForBeers(HttpServletRequest requestServlet, HandlerMethod handler, String token) {
        boolean isBeerUrl = Constant.MEET_BEER_URL.equals(requestServlet.getRequestURI());
        boolean isMethodCalculateBeer = Constant.CALCULATE_BEER_METHOD.equals(handler.getMethod().getName());
        
        if (isBeerUrl && isMethodCalculateBeer && !isAdmin(token)) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(), Constant.INVALID_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
        return;
    }

    private void checkIsUserOrAdmin(String token) {
        if (!isAdmin(token) && !isUser(token)) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(), Constant.INVALID_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    private boolean isAdmin(String token) {
        return Constant.ADMIN_TOKEN.equals(token);
    }

    private boolean isUser(String token) {
        return Constant.USER_TOKEN.equals(token);
    }
}
