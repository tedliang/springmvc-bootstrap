package org.springframework.samples.mvc.validation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class DefaultControllerAdvice implements ApplicationContextAware {

    private final Map<Class, Validator> validatorMap = new ConcurrentHashMap<>(64);

    private ApplicationContext applicationContext;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null) {
            Validator validator = findValidator(target.getClass());
            if (validator != null) {
                binder.addValidators(validator);
            }
        }
    }

    private Validator findValidator(Class<?> clazz) {
        Validator validatorToAdd = validatorMap.get(clazz);
        if (validatorToAdd == null) {
            for (Validator validator : applicationContext.getBeansOfType(Validator.class).values()) {
                if (validator.supports(clazz)) {
                    validatorMap.put(clazz, validator);
                    validatorToAdd = validator;
                    break;
                }
            }
        }
        return validatorToAdd;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
